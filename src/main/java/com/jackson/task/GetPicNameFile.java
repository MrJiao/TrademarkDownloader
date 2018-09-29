package com.jackson.task;

import com.jackson.config.AnnConfigManager;
import com.jackson.domain.TaskFolderState;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;



/**
 * Create by: Jackson
 */
public class GetPicNameFile implements Runnable {

    File picfolder;
    TaskFolderState taskFolderState;

    public GetPicNameFile(TaskFolderState taskFolderState) {
        this.picfolder = new File(AnnConfigManager.instance.getImageEnglishFolder(taskFolderState));
        this.taskFolderState = taskFolderState;
    }


    @Override
    public void run() {
        File[] picFiles = picfolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });

        ArrayList<String> arr = new ArrayList<>();
        for (File picFile : picFiles) {
            arr.add(getSimpleName(picFile));
        }
        try {
            FileUtils.writeLines(
                    new File( AnnConfigManager.instance.getPicNameFilePath(taskFolderState))
                    ,arr,false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getSimpleName(File file){
        String name = file.getName();
        String s = name.split("__")[1];
        String simpleName = s.split("\\.")[0];
        return simpleName;
    }
}
