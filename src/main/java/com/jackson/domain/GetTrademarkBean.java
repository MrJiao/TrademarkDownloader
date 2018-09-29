package com.jackson.domain;

import java.util.List;

/**
 * Create by: Jackson
 */
public class GetTrademarkBean {

    private long total;
    private List<Row> rows;
    public void setTotal(long total) {
        this.total = total;
    }
    public long getTotal() {
        return total;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
    public List<Row> getRows() {
        return rows;
    }

    @Override
    public String toString() {
        return "GetTrademarkBean{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
