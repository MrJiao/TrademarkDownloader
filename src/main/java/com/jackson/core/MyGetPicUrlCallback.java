package com.jackson.core;

import com.jackson.config.AnnConfigManager;
import com.jackson.domain.TaskFolderState;
import com.jackson.task.GetImageUrlTask;
import com.jackson.utils.L;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Set;

/**
 * Create by: Jackson
 */
public class MyGetPicUrlCallback implements GetImageUrlTask.GetPicBeanCallBack {

    TaskFolderState taskFolderState;

    public MyGetPicUrlCallback(TaskFolderState taskFolderState) {
        this.taskFolderState = taskFolderState;
    }

    @Override
    public void imageUrl(Set<String> urlSet){
        File file = new File(AnnConfigManager.instance.getImageUrlFilePath(taskFolderState));
        MyRetryTemplate.retryTemplate(new MCallback(file,urlSet));

        taskFolderState.storeGetImageUrl(true);
    }

    private static class MCallback implements MyRetryTemplate.RetryCallback<Void>{
        Set<String> urlSet;
        File file;

        public MCallback(File file,Set<String> urlSet) {
            this.urlSet = urlSet;
            this.file = file;
        }

        @Override
        public Void doFunction() throws Exception {
            L.d("存入图片url数据：",file.getParent(),urlSet.size());
            FileUtils.writeLines(file,urlSet,false);
            return null;
        }
    }
}
