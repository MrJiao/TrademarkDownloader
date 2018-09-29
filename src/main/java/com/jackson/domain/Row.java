package com.jackson.domain;

import java.util.Date;

/**
 * Create by: Jackson
 */
public class Row implements Comparable<Row>{

    private int page_no;
    private String tm_name;
    private String ann_type_code;
    private String tmname;
    private String reg_name;
    private String ann_type;
    private String ann_num;
    private String reg_num;
    private String id;
    private int rn;
    private Date ann_date;
    private String regname;
    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }
    public int getPage_no() {
        return page_no;
    }

    public void setTm_name(String tm_name) {
        this.tm_name = tm_name;
    }
    public String getTm_name() {
        return tm_name;
    }

    public void setAnn_type_code(String ann_type_code) {
        this.ann_type_code = ann_type_code;
    }
    public String getAnn_type_code() {
        return ann_type_code;
    }

    public void setTmname(String tmname) {
        this.tmname = tmname;
    }
    public String getTmname() {
        return tmname;
    }

    public void setReg_name(String reg_name) {
        this.reg_name = reg_name;
    }
    public String getReg_name() {
        return reg_name;
    }

    public void setAnn_type(String ann_type) {
        this.ann_type = ann_type;
    }
    public String getAnn_type() {
        return ann_type;
    }

    public void setAnn_num(String ann_num) {
        this.ann_num = ann_num;
    }
    public String getAnn_num() {
        return ann_num;
    }

    public void setReg_num(String reg_num) {
        this.reg_num = reg_num;
    }
    public String getReg_num() {
        return reg_num;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setRn(int rn) {
        this.rn = rn;
    }
    public int getRn() {
        return rn;
    }

    public void setAnn_date(Date ann_date) {
        this.ann_date = ann_date;
    }
    public Date getAnn_date() {
        return ann_date;
    }

    public void setRegname(String regname) {
        this.regname = regname;
    }
    public String getRegname() {
        return regname;
    }

    @Override
    public String toString() {
        return "Row{" +
                "page_no=" + page_no +
                ", tm_name='" + tm_name + '\'' +
                ", ann_type_code='" + ann_type_code + '\'' +
                ", tmname='" + tmname + '\'' +
                ", reg_name='" + reg_name + '\'' +
                ", ann_type='" + ann_type + '\'' +
                ", ann_num='" + ann_num + '\'' +
                ", reg_num='" + reg_num + '\'' +
                ", id='" + id + '\'' +
                ", rn=" + rn +
                ", ann_date=" + ann_date +
                ", regname='" + regname + '\'' +
                '}';
    }

    @Override
    public int compareTo(Row o) {
        return getPage_no()-o.getPage_no();
    }
}
