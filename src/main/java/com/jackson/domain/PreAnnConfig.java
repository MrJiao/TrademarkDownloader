package com.jackson.domain;

import com.jackson.config.GlobalConfigManager;
import com.jackson.utils.L;
import com.jackson.utils.PropertiesUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Create by: Jackson
 */
public class PreAnnConfig {


    public static final String properties_ann_folder_name="ann_folder_name";
    public static final String properties_start_page_num="start_page_num";
    public static final String properties_end_page_num="end_page_num";
    public static final String properties_state="state";
    public static final String properties_annNum="annNum";

    public static final String STATE_COMPLETE="STATE_COMPLETE";
    public static final String STATE_START="STATE_START";
    public static final String STATE_NO_TASK="STATE_NO_TASK";


    private String ann_folder_name;
    private int start_page_num;
    private int end_page_num;
    private int annNum;//公告期号
    private String state;

    private long waitTime;

    private boolean isShutDownIfComplete;

    public boolean isShutDownIfComplete() {
        return isShutDownIfComplete;
    }

    public void setShutDownIfComplete(boolean shutDownIfComplete) {
        isShutDownIfComplete = shutDownIfComplete;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public String getAnn_folder_name() {
        return ann_folder_name;
    }

    public void setAnn_folder_name(String ann_folder_name) {
        this.ann_folder_name = ann_folder_name;
    }

    public int getStart_page_num() {
        return start_page_num;
    }

    public void setStart_page_num(int start_page_num) {
        this.start_page_num = start_page_num;
    }

    public int getEnd_page_num() {
        return end_page_num;
    }

    public void setEnd_page_num(int end_page_num) {
        this.end_page_num = end_page_num;
    }

    public String getState() {
        return state;
    }

    public int getAnnNum() {
        return annNum;
    }

    public void setAnnNum(int annNum) {
        this.annNum = annNum;
    }

    public void setState(String state) {
        this.state = state;
    }


    public void storeState(String state){
        storeProperties(properties_state,state);
        setState(state);
    }


    private void storeProperties(String key, String value) {
        File file = new File(GlobalConfigManager.instance.getPreAnnConfigPropertiesPath());
        if (!file.exists()) throw new RuntimeException("ProAnnConfig配置文件不见了,请重启");
        Properties properties = PropertiesUtil.loadProperties(file);
        properties.setProperty(key, value);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, "");
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            L.exception(e);
            throw new RuntimeException("ProAnnConfig配置文件不见了,请重启");
        } catch (IOException e) {
            L.exception(e);
            throw new RuntimeException("写入TaskFolderState配置文件失败，请重启");
        }
    }
}
