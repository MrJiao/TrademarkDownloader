package com.jackson.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackson.config.AnnConfigManager;
import com.jackson.domain.GetDocNumBean;
import com.jackson.domain.TaskFolderState;
import com.jackson.task.DownImageAndDocNumTask;
import com.jackson.utils.L;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by: Jackson
 */
public class MyDownloadAndImageDocNumCallback implements DownImageAndDocNumTask.DownImageAndDocCallBack {

    TaskFolderState taskFolderState;
    String docNumFilePath;
    public MyDownloadAndImageDocNumCallback(TaskFolderState taskFolderState) {
        this.taskFolderState = taskFolderState;
        docNumFilePath = AnnConfigManager.instance.getDocNumFilePath(taskFolderState);
    }

    @Override
    public void downloadSuccess(List<String> imageUrl, ArrayList<GetDocNumBean> docList) {
        taskFolderState.storeDownloadImage(true);

        Boolean isSuccess = MyRetryTemplate.retryTemplate(3, 1000, new StoreDocNumBeanCallback(docList));
        if(isSuccess)
            L.i("写入docNum成功",docNumFilePath,"数据量：",docList.size());

    }



    private class StoreDocNumBeanCallback implements MyRetryTemplate.RetryCallback<Boolean>{
        ArrayList<GetDocNumBean> docList;
        File docNumFile = new File(docNumFilePath);
        private ObjectMapper mapper = new ObjectMapper();

        public StoreDocNumBeanCallback(ArrayList<GetDocNumBean> docList) {
            this.docList = docList;

        }

        @Override
        public Boolean doFunction() throws Exception {

            ArrayList<String> arr = new ArrayList<>(docList.size());
            for (GetDocNumBean getDocNumBean : docList) {
                arr.add(mapper.writeValueAsString(getDocNumBean));
            }

            FileUtils.writeLines(docNumFile,arr,false);
            taskFolderState.storeGetImageDocNum(true);
            return true;
        }
    }
}
