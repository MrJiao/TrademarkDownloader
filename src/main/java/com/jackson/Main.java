package com.jackson;

import com.jackson.config.AnnConfigManager;
import com.jackson.config.GlobalConfigManager;
import com.jackson.config.InputManager;
import com.jackson.core.*;
import com.jackson.domain.PreAnnConfig;
import com.jackson.domain.TaskFolderState;
import com.jackson.domain.TimerBean;
import com.jackson.task.GetPicNameFile;
import com.jackson.task.ReNameTask;
import com.jackson.utils.L;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by: Jackson
 */
public class Main {
    public static final boolean isPackage = true;
    private static TimerBean timerBean;

    public static void main(String[] args) {
        try {
            L.console("徐胖胖专属");
            GlobalConfigManager gConfig = GlobalConfigManager.instance;
            //全局信息初始化：全局config 文件、和运行path，上次任务信息
            gConfig.init();
            //初始化总任务信息
            InputManager.init();
            //根据上次任务情况进行初始化
            AnnConfigManager.instance.init();
            gConfig.storePreAnnConfig();
            L.i("初始化配置成功");
            PreAnnConfig preAnnConfig = gConfig.getPreAnnConfig();
            ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(2);
            if(preAnnConfig.getWaitTime()>0){
                timerBean = new TimerBean(preAnnConfig.getWaitTime());
                ShowDelayTask showDelayTask = new ShowDelayTask(timerBean);
                ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(showDelayTask,0,1,TimeUnit.MINUTES);
                showDelayTask.setScheduledFuture(scheduledFuture);
            }
            executor.schedule(new Mtask(),preAnnConfig.getWaitTime(),TimeUnit.MINUTES);
        }catch (Exception e){
            L.console("任务失败，关闭重试");
        }
    }

    private static class ShowDelayTask implements Runnable{
        TimerBean timerBean;
        ScheduledFuture scheduledFuture;

        public void setScheduledFuture(ScheduledFuture scheduledFuture) {
            this.scheduledFuture = scheduledFuture;
        }

        public ShowDelayTask(TimerBean timerBean) {
            this.timerBean = timerBean;
        }

        @Override
        public void run() {
            if(timerBean.getWaitTime()<=0){
                scheduledFuture.cancel(false);
            }else {
                L.d("还剩",timerBean.getWaitTime(),"分钟执行任务");
                timerBean.setWaitTime(timerBean.getWaitTime()-1);
            }

        }
    }

    private static class Mtask implements Runnable{
        CloseableHttpClient client = HttpClients.createDefault();
        @Override
        public void run() {

            //获取当前运行路径
            List<TaskFolderState> taskFolderStateList = AnnConfigManager.instance.getTaskFolderStateList();
            for (TaskFolderState taskFolderState : taskFolderStateList) {
                if(!taskFolderState.isGetImageUrl()){
                    getUrl(taskFolderState);
                    downloadImage(taskFolderState);
                    getTrademarkRow(taskFolderState);
                    rename(taskFolderState);
                    getPicNameFile(taskFolderState);
                }
                if(taskFolderState.isGetImageUrl()&&!taskFolderState.isDownloadImage()){
                    downloadImage(taskFolderState);
                    getTrademarkRow(taskFolderState);
                    rename(taskFolderState);
                    getPicNameFile(taskFolderState);
                }
                if(taskFolderState.isGetImageUrl()&&taskFolderState.isDownloadImage()&&!taskFolderState.isGetTrademarkRow()){
                    getTrademarkRow(taskFolderState);
                    rename(taskFolderState);
                    getPicNameFile(taskFolderState);
                }
                if(taskFolderState.isGetImageUrl()&&taskFolderState.isDownloadImage()&&taskFolderState.isGetTrademarkRow()&&!taskFolderState.isRename()){
                    rename(taskFolderState);
                    getPicNameFile(taskFolderState);
                }
            }
            completeHandle();
        }

        private void getTrademarkRow(TaskFolderState taskFolderState) {
            GetTrademarkDispatcher.doFunction(client,new MyGetTrademarkCallback(taskFolderState),taskFolderState.getStartTrademarkPageNum(),taskFolderState.getEndTrademarkPageNum());
        }


        private void getUrl(TaskFolderState taskFolderState){
            L.i("开始获取图片url");
            GetImageUrlDispatcher.doFunction(client,new MyGetPicUrlCallback(taskFolderState),taskFolderState.getStartImagePageNum(),taskFolderState.getEndImagePageNum());
            L.i("完成获取图片url");
        }


        private void downloadImage(TaskFolderState taskFolderState){
            L.i("开始下载图片");
            DownImageDispatcher.doFunction(
                    client,
                    new MyDownloadAndImageDocNumCallback(taskFolderState),
                    new File(taskFolderState.getFolderPath()),
                    new File(AnnConfigManager.instance.getImageUrlFilePath(taskFolderState)));
            L.i("下载图片完成");
        }


        private void completeHandle(){
            PreAnnConfig preAnnConfig = GlobalConfigManager.instance.getPreAnnConfig();
           // preAnnConfig.storeState(PreAnnConfig.STATE_COMPLETE);
            L.console("任务完成");
            if(preAnnConfig.isShutDownIfComplete()){
                try {
                    Runtime.getRuntime().exec("Shutdown -s");
                } catch (IOException e) {
                    L.exception(e);
                }
            }
        }

        private void rename(TaskFolderState taskFolderState){
            L.i("开始重名图片");
            String imageFolderPath = taskFolderState.getFolderPath();
            String trademarkRowFilePath = AnnConfigManager.instance.getTrademarkRowFilePath(taskFolderState);
            new ReNameTask(imageFolderPath,trademarkRowFilePath,taskFolderState).run();
            L.i("完成重名图片");
        }


        private void getPicNameFile(TaskFolderState taskFolderState){
            new GetPicNameFile(taskFolderState).run();
        }


    }


}
