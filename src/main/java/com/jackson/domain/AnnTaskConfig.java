package com.jackson.domain;


/**
 * 图片下载的信息
 * Create by: Jackson
 */
public class AnnTaskConfig {

    int pageSize;//返回的数量
    int total;//总数
    int annNum;
    String searchId;

    public int getAnnNum() {
        return annNum;
    }

    public void setAnnNum(int annNum) {
        this.annNum = annNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}
