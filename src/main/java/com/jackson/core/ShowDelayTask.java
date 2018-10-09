package com.jackson.core;

import com.jackson.domain.TimerBean;
import com.jackson.utils.L;

import java.util.concurrent.ScheduledFuture;

/**
 * Create by: Jackson
 */
public class ShowDelayTask implements Runnable{
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