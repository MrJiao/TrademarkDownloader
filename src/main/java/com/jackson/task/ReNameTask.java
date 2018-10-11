package com.jackson.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackson.config.AnnConfigManager;
import com.jackson.config.EncodeConfig;
import com.jackson.domain.MyImageFile;
import com.jackson.domain.Row;
import com.jackson.domain.TaskFolderState;
import com.jackson.utils.ChineseUtil;
import com.jackson.utils.L;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;

/**
 * Create by: Jackson
 */
public class ReNameTask implements Runnable {

    String imageFolderPath;

    String rowFilePath;

    TaskFolderState taskFolderState;
    String annNum = String.valueOf(AnnConfigManager.instance.getAnnTaskConfig().getAnnNum());
    private ObjectMapper mapper = new ObjectMapper();
    ArrayList<Row> rowList = new ArrayList<>();
    ArrayList<MyImageFile> imageFileList = new ArrayList<>();


    public ReNameTask(String imageFolderPath, String rowFilePath, TaskFolderState taskFolderState) {
        this.imageFolderPath = imageFolderPath;
        this.rowFilePath = rowFilePath;
        this.taskFolderState = taskFolderState;
    }

    private String getShortName(String name) {
        return name.split("\\.")[0];
    }

    @Override
    public void run() {
        L.i("开始重命名图片");
        initFolder();
        //读出当前的图片名称和对应的地址
        initRow();
        File imageFolder = new File(imageFolderPath);
        File[] imageFiles = imageFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                return name.endsWith("jpg");
            }
        });
        for (File imageFile : imageFiles) {
            String shortName = getShortName(imageFile.getName());
            int pageNumber = Integer.parseInt(shortName);
            imageFileList.add(new MyImageFile(pageNumber, imageFile, shortName));
        }

        Collections.sort(imageFileList, new Comparator<MyImageFile>() {
            @Override
            public int compare(MyImageFile o1, MyImageFile o2) {
                return o1.getPageNumber() - o2.getPageNumber();
            }
        });

        int rowIndex = 0;
        for (int i = 0; i < imageFileList.size(); i++) {
            MyImageFile imageFile = imageFileList.get(i);
            Row row = rowList.get(rowIndex);
            while (imageFile.getPageNumber() > row.getPage_no()) {
                rowIndex++;
                row = rowList.get(rowIndex);
                continue;
            }
            if (imageFile.getPageNumber() == row.getPage_no()) {
                String storePath;
                if (ChineseUtil.isChinese(row.getTm_name())){
                    storePath = AnnConfigManager.instance.getImageChineseFolder(taskFolderState);
                }else {
                    storePath = AnnConfigManager.instance.getImageEnglishFolderPath(taskFolderState);
                }

                try {
                    FileUtils.copyFile(imageFile.getImageFile(),new File(storePath, getImageName(row)));

                } catch (IOException e) {
                    try {
                        FileUtils.copyFile(imageFile.getImageFile(),new File(storePath, getErrorImageName(row)));
                    } catch (IOException e1) {
                        L.exception(e);
                    }
                    L.i("这个文件名不能被正确命名:",getImageName(row),"storePath",storePath);
                }
                rowIndex++;
            }
        }
        taskFolderState.storeRename(true);
        L.i("重命名图片完成");
    }

    private void initFolder() {
        String imageEnglishFolderPath = AnnConfigManager.instance.getImageEnglishFolderPath(taskFolderState);
        String chineseFolderPath = AnnConfigManager.instance.getImageChineseFolder(taskFolderState);

        File imageEnglishFolder = new File(imageEnglishFolderPath);
        File chineseFolder = new File(chineseFolderPath);

        if(!imageEnglishFolder.exists()){
            imageEnglishFolder.mkdir();
        }
        if(!chineseFolder.exists()){
            chineseFolder.mkdir();
        }
    }

    private String getImageName(Row row) {
        //return annNum + "_" + row.getPage_no() + "_" + row.getTm_name()+".jpg";
        return annNum + "_" + row.getTm_name()+".jpg";
    }

    //当名字不能使用时，使用这个来命名文件名
    private String getErrorImageName(Row row){
        return annNum + "_" + row.getPage_no() + "_" + "被神秘力量隐藏了文件名"+".jpg";
    }

    private void initRow() {
        try {
            List<String> rowStrList = FileUtils.readLines(new File(rowFilePath), EncodeConfig.ENCODE);
            for (String rowStr : rowStrList) {
                rowList.add(mapper.readValue(rowStr, Row.class));
            }
        } catch (IOException e) {
            L.e(e);
        }
    }
}
