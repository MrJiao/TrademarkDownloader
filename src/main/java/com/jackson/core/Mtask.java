package com.jackson.core;

import com.jackson.config.AnnConfigManager;
import com.jackson.config.GlobalConfigManager;
import com.jackson.domain.PreAnnConfig;
import com.jackson.domain.TaskFolderState;
import com.jackson.task.GetPicNameFile;
import com.jackson.task.ImageScreeningTask;
import com.jackson.task.ReNameTask;
import com.jackson.utils.L;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Create by: Jackson
 */
public class Mtask implements Runnable {
    CloseableHttpClient client = HttpClients.createDefault();

    @Override
    public void run() {

        //获取当前运行路径
        List<TaskFolderState> taskFolderStateList = AnnConfigManager.instance.getTaskFolderStateList();
        for (TaskFolderState taskFolderState : taskFolderStateList) {
            if (!taskFolderState.isGetImageUrl()) {
                getUrl(taskFolderState);
                downloadImage(taskFolderState);
                getTrademarkRow(taskFolderState);
                rename(taskFolderState);
                getPicNameFile(taskFolderState);
            }
            if (taskFolderState.isGetImageUrl() && !taskFolderState.isDownloadImage()) {
                downloadImage(taskFolderState);
                getTrademarkRow(taskFolderState);
                rename(taskFolderState);
                getPicNameFile(taskFolderState);
            }
            if (taskFolderState.isGetImageUrl() && taskFolderState.isDownloadImage() && !taskFolderState.isGetTrademarkRow()) {
                getTrademarkRow(taskFolderState);
                rename(taskFolderState);
                getPicNameFile(taskFolderState);
            }
            if (taskFolderState.isGetImageUrl() && taskFolderState.isDownloadImage() && taskFolderState.isGetTrademarkRow() && !taskFolderState.isRename()) {
                rename(taskFolderState);
                getPicNameFile(taskFolderState);
            }
            if (taskFolderState.isGetImageUrl() && taskFolderState.isDownloadImage() && taskFolderState.isGetTrademarkRow() && taskFolderState.isRename() && !taskFolderState.isGetPicNameFile()) {
                getPicNameFile(taskFolderState);
            }
            if (taskFolderState.isGetImageUrl() && taskFolderState.isDownloadImage() && taskFolderState.isGetTrademarkRow() && taskFolderState.isRename() && taskFolderState.isGetPicNameFile() && !taskFolderState.isImageScreening()) {
                imageScreening(taskFolderState);

            }
        }
        downloadCompleteHandle();
        imageScreenCompleteHandle();


    }

    private void imageScreenCompleteHandle() {
        List<TaskFolderState> taskFolderStateList = AnnConfigManager.instance.getTaskFolderStateList();
        boolean isAllComplete = true;
        for (TaskFolderState taskFolderState : taskFolderStateList) {
            if (taskFolderState.isGetImageUrl() &&
                    taskFolderState.isDownloadImage() &&
                    taskFolderState.isGetTrademarkRow() &&
                    taskFolderState.isRename() &&
                    taskFolderState.isGetPicNameFile() &&
                    taskFolderState.isImageScreening()) {
            }else {
                isAllComplete = false;
            }
        }
        if (isAllComplete) {
            PreAnnConfig preAnnConfig = GlobalConfigManager.instance.getPreAnnConfig();
            preAnnConfig.storeState(PreAnnConfig.STATE_COMPLETE);
            L.console("图片筛选完成");
        }
    }



    private void imageScreening(TaskFolderState taskFolderState) {
        new ImageScreeningTask(taskFolderState).run();
    }

    private void getTrademarkRow(TaskFolderState taskFolderState) {
        GetTrademarkDispatcher.doFunction(client, new MyGetTrademarkCallback(taskFolderState), taskFolderState.getStartTrademarkPageNum(), taskFolderState.getEndTrademarkPageNum());
    }


    private void getUrl(TaskFolderState taskFolderState) {
        L.i("开始获取图片url");
        GetImageUrlDispatcher.doFunction(client, new MyGetPicUrlCallback(taskFolderState), taskFolderState.getStartImagePageNum(), taskFolderState.getEndImagePageNum());
        L.i("完成获取图片url");
    }


    private void downloadImage(TaskFolderState taskFolderState) {
        DownImageDispatcher.doFunction(
                client,
                new MyDownloadAndImageDocNumCallback(taskFolderState),
                new File(taskFolderState.getFolderPath()),
                new File(AnnConfigManager.instance.getImageUrlFilePath(taskFolderState)));

    }


    private void downloadCompleteHandle() {
        PreAnnConfig preAnnConfig = GlobalConfigManager.instance.getPreAnnConfig();
        L.console("下载任务完成,还差筛选任务，文件放置成功后请重启");
        if (preAnnConfig.isShutDownIfComplete()) {
            try {
                Runtime.getRuntime().exec("Shutdown -s");
            } catch (IOException e) {
                L.exception(e);
            }
        }
    }

    private void rename(TaskFolderState taskFolderState) {
        String imageFolderPath = taskFolderState.getFolderPath();
        String trademarkRowFilePath = AnnConfigManager.instance.getTrademarkRowFilePath(taskFolderState);
        new ReNameTask(imageFolderPath, trademarkRowFilePath, taskFolderState).run();

    }


    private void getPicNameFile(TaskFolderState taskFolderState) {
        new GetPicNameFile(taskFolderState).run();
    }


}