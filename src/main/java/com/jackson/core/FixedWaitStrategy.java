package com.jackson.core;

/**
 * Create by: Jackson
 */
public class FixedWaitStrategy implements WaitStrategy {


    long waitTime = 200;

    public FixedWaitStrategy(){}

    public FixedWaitStrategy(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public long waitTime() {
        return waitTime;
    }
}
