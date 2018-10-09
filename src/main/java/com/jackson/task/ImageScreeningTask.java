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
import java.util.List;

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
                return name.endsWith("jpg");
            }
        });

        for (File imageFile : imageFiles) {
            for (String imageName : imageNames) {
                if(StringUtils.equals(imageName,getSimpleName(imageFile))){
                    try {
                        FileUtils.copyFile(imageFile,new File(imageScreeningFolder,imageFile.getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        taskFolderState.storeImageScreening(true);
        L.i("筛选图片完成");
    }


    public String getSimpleName(File file){
        String name = file.getName();
        String s = name.split("__")[1];
        String simpleName = s.split("\\.")[0];
        simpleName = ChineseUtil.removeChinese(simpleName);

        return simpleName.trim();
    }

}
