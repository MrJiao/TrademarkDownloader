package com.jackson;

import com.jackson.config.AnnConfigManager;
import com.jackson.config.GlobalConfigManager;
import com.jackson.config.InputManager;
import com.jackson.core.*;
import com.jackson.domain.PreAnnConfig;
import com.jackson.domain.TimerBean;
import com.jackson.funny.HappyTime;
import com.jackson.utils.L;

import java.io.IOException;
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
            HappyTime.start();
            GlobalConfigManager gConfig = GlobalConfigManager.instance;
            //初始化数据
            init(gConfig);
            PreAnnConfig preAnnConfig = gConfig.getPreAnnConfig();
            ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(2);
            //显示倒计时
            showCountdown(preAnnConfig,executor);
            //开启任务
            executor.schedule(new Mtask(),preAnnConfig.getWaitTime(),TimeUnit.MINUTES);
        }catch (Exception e){
            L.console("任务失败，关闭重试");
        }
    }


    public static void init(GlobalConfigManager gConfig) throws IOException {
        //全局信息初始化：全局config 文件、和运行path，上次任务信息
        gConfig.init();
        //初始化总任务信息
        InputManager.init();
        //根据上次任务情况进行初始化
        AnnConfigManager.instance.init();
        gConfig.storePreAnnConfig();
        L.i("初始化配置成功");
    }

    public static void showCountdown(PreAnnConfig preAnnConfig, ScheduledThreadPoolExecutor executor){
        if(preAnnConfig.getWaitTime()>0){
            timerBean = new TimerBean(preAnnConfig.getWaitTime());
            ShowDelayTask showDelayTask = new ShowDelayTask(timerBean);
            ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(showDelayTask,0,1,TimeUnit.MINUTES);
            showDelayTask.setScheduledFuture(scheduledFuture);
        }
    }


}
