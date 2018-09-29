package com.jackson.domain;

/**
 * Create by: Jackson
 */
public class TimerBean {

    long waitTime;

    public TimerBean(long waitTime) {
        this.waitTime = waitTime;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
