package com.jackson.domain;

/**
 * 列表名字的配置
 * Create by: Jackson
 */
public class TrademarkConfig {

    long totalSize;
    int totalPage;
    int annNum;

    public int getAnnNum() {
        return annNum;
    }

    public void setAnnNum(int annNum) {
        this.annNum = annNum;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
