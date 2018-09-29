package com.jackson.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackson.config.AnnConfigManager;
import com.jackson.domain.Row;
import com.jackson.domain.TaskFolderState;
import com.jackson.task.GetTrademarkTask;
import com.jackson.utils.L;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by: Jackson
 */
public class MyGetTrademarkCallback implements GetTrademarkTask.GetTrademarkCallBack {

    TaskFolderState taskFolderState;
    private ObjectMapper mapper = new ObjectMapper();
    public MyGetTrademarkCallback(TaskFolderState taskFolderState) {
        this.taskFolderState = taskFolderState;
    }



    @Override
    public void onTaskSuccess(List<Row> rowList) {
        File file = new File(AnnConfigManager.instance.getTrademarkRowFilePath(taskFolderState));
        MyRetryTemplate.retryTemplate(new MCallback(file,rowList));
        taskFolderState.storeGetTrademarkRow(true);
    }

    private class MCallback implements MyRetryTemplate.RetryCallback<Void>{
        List<Row> rowList;
        File file;

        public MCallback(File file,List<Row> rowList) {
            this.rowList = rowList;
            this.file = file;
        }

        @Override
        public Void doFunction() throws Exception {
            ArrayList<String> arr = new ArrayList<>(rowList.size());
            for (Row row : rowList) {
                arr.add(mapper.writeValueAsString(row));
            }
            FileUtils.writeLines(file,arr,false);
            L.d("存入Trademark数据：",file.getParent(),rowList.size());
            return null;
        }
    }
}
