package com.jackson.task;

import com.jackson.config.AnnConfigManager;
import com.jackson.config.EncodeConfig;
import com.jackson.domain.TaskFolderState;
import com.jackson.utils.ChineseUtil;
import com.jackson.utils.L;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by: Jackson
 */
public class ImageScreeningTask implements Runnable {
    TaskFolderState taskFolderState;

    public ImageScreeningTask(TaskFolderState taskFolderState) {
        this.taskFolderState = taskFolderState;
    }

    @Override
    public void run() {
        L.i("开始筛选图片");
        String imageScreeningFolderPath = AnnConfigManager.instance.getImageScreeningFolderPath(taskFolderState);
        String imageScreeningFilePath = AnnConfigManager.instance.getImageScreeningFilePath(taskFolderState);
        String imageEnglishFolderPath = AnnConfigManager.instance.getImageEnglishFolderPath(taskFolderState);

        File imageScreeningFolder = new File(imageScreeningFolderPath);
        File imageScreeningFile = new File(imageScreeningFilePath);
        File imageEnglishFolder = new File(imageEnglishFolderPath);

        imageScreeningFolder.mkdir();

        if(!imageScreeningFile.exists()){
            L.i("徐老师，请把筛选的商标名放到",imageScreeningFile.getAbsolutePath(),"如果没有，就新建一个",imageScreeningFile.getName(),"放进去");
            return;
        }
        List<String> imageNames = null;
        try {
            imageNames = FileUtils.readLines(imageScreeningFile, EncodeConfig.ENCODE);
        } catch (IOException e) {
            L.e(e);
        }

        File[] imageFiles = imageEnglishFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                return name.endsWith("jpg")&&!name.contains("被神秘力量隐藏了文件名");
            }
        });

        Map<String, List<File>> copyFile = getCopyFile(imageFiles, imageNames);

        for (Map.Entry<String, List<File>> entry : copyFile.entrySet()) {
            List<File> value = entry.getValue();
            for (int i=0;i<value.size();i++) {
                if(value.size()==1){
                    try {
                        FileUtils.copyFile(value.get(i),new File(imageScreeningFolder,entry.getKey()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        FileUtils.copyFile(value.get(i),new File(imageScreeningFolder,entry.getKey()+"("+i+1+").jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        taskFolderState.storeImageScreening(true);
        L.i("筛选图片完成");
    }


    private Map<String,List<File>> getCopyFile(File[] imageFiles,List<String> imageNames){
        HashMap<String, List<File>> map = new HashMap<>();
        for (File imageFile : imageFiles) {

            for (String imageName : imageNames) {
                if(StringUtils.equals(imageName,getSimpleName(imageFile))){
                    String name = getName(imageFile);
                    List<File> files = map.get(name);
                    if(files == null){
                        files = new ArrayList<>();
                        map.put(name,files);
                    }
                    files.add(imageFile);
                }
            }
        }
        return map;
    }

    private String getName(File imageFile){
        String name = imageFile.getName();
        String[] s = name.split("_");
        s[2]=s[2].split("\\.")[0];
        name = s[0]+"_"+s[2];
        return name;
    }


    public String getSimpleName(File file){
        String name = file.getName();
        String s = name.split("_")[2];
        String simpleName = s.split("\\.")[0];
        simpleName = ChineseUtil.removeChinese(simpleName);
        return simpleName.trim();
    }

}
