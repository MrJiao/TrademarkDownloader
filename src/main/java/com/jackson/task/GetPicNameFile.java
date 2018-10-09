package com.jackson.task;

import com.jackson.config.AnnConfigManager;
import com.jackson.domain.TaskFolderState;
import com.jackson.utils.ChineseUtil;
import com.jackson.utils.L;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

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
        this.picfolder = new File(AnnConfigManager.instance.getImageEnglishFolderPath(taskFolderState));
        this.taskFolderState = taskFolderState;
    }


    @Override
    public void run() {
        L.d("开始整理图片名");
        File[] picFiles = picfolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });

        ArrayList<String> arr = new ArrayList<>();
        for (File picFile : picFiles) {
            String simpleName = getSimpleName(picFile);
            if(StringUtils.isEmpty(simpleName))continue;
            arr.add(simpleName);
        }
        try {
            FileUtils.writeLines(
                    new File( AnnConfigManager.instance.getPicNameFilePath(taskFolderState))
                    ,arr,false);
            taskFolderState.storeGetPicNameFile(true);
            L.d("整理图片名完成");
        } catch (IOException e) {
            L.e(e);
            L.e("整理图片名失败");
        }
    }

    public String getSimpleName(File file){
        String name = file.getName();
        String s = name.split("__")[1];
        String simpleName = s.split("\\.")[0];
        simpleName = ChineseUtil.removeChinese(simpleName);

        return simpleName.trim();
    }
}
