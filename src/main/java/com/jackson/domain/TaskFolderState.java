package com.jackson.domain;

import com.jackson.utils.L;
import com.jackson.utils.PropertiesUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 任务里分文件夹使用状态的
 * Create by: Jackson
 */
public class TaskFolderState {

    public static final String properties_start_image_page_num = "start_image_page_num";
    public static final String properties_end_image_page_num = "end_image_page_num";
    public static final String properties_start_trademark_page_num = "start_trademark_page_num";
    public static final String properties_end_trademark_page_num = "end_trademark_page_num";

    public static final String properties_isGetImageUrl = "isGetImageUrl";
    public static final String properties_isGetImageDocNum = "isGetImageDocNum";
    public static final String properties_isDownloadImage = "isDownloadImage";
    public static final String properties_isGetTrademarkRow = "isGetTrademarkRow";
    public static final String properties_isRename = "isRename";
    public static final String properties_isGetPicNameFile = "isGetPicNameFile";
    public static final String properties_isImageScreening = "isImageScreening";

    String folderName;
    String folderPath;
    int startImagePageNum;
    int endImagePageNum;

    int startTrademarkPageNum;
    int endTrademarkPageNum;

    public int getStartTrademarkPageNum() {
        return startTrademarkPageNum;
    }

    public void setStartTrademarkPageNum(int startTrademarkPageNum) {
        this.startTrademarkPageNum = startTrademarkPageNum;
    }

    public int getEndTrademarkPageNum() {
        return endTrademarkPageNum;
    }

    public void setEndTrademarkPageNum(int endTrademarkPageNum) {
        this.endTrademarkPageNum = endTrademarkPageNum;
    }

    int index;//用来排序图片下载文件夹顺序的

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    boolean isGetImageUrl;
    boolean isGetImageDocNum;
    boolean isDownloadImage;
    boolean isRename;
    boolean isGetTrademarkRow;
    boolean isGetPicNameFile;
    boolean isImageScreening;

    public boolean isImageScreening() {
        return isImageScreening;
    }

    public void setImageScreening(boolean imageScreening) {
        isImageScreening = imageScreening;
    }

    public void storeImageScreening(boolean isImageScreening) {
        storeProperties(properties_isImageScreening, isImageScreening + "");
        this.isImageScreening = isImageScreening;
    }

    public boolean isGetTrademarkRow() {
        return isGetTrademarkRow;
    }

    public void setGetTrademarkRow(boolean getTrademarkRow) {
        isGetTrademarkRow = getTrademarkRow;
    }

    public void storeGetTrademarkRow(boolean isGetTrademarkRow) {
        storeProperties(properties_isGetTrademarkRow, isGetTrademarkRow + "");
        this.isGetTrademarkRow = isGetTrademarkRow;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public int getStartImagePageNum() {
        return startImagePageNum;
    }

    public void setStartImagePageNum(int startImagePageNum) {
        this.startImagePageNum = startImagePageNum;
    }

    public int getEndImagePageNum() {
        return endImagePageNum;
    }

    public void setEndImagePageNum(int endImagePageNum) {
        this.endImagePageNum = endImagePageNum;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public boolean isGetImageUrl() {
        return isGetImageUrl;
    }

    public void setGetImageUrl(boolean getImageUrl) {
        isGetImageUrl = getImageUrl;
    }

    public void storeGetImageUrl(boolean getImageUrl) {
        storeProperties(properties_isGetImageUrl, getImageUrl + "");
        isGetImageUrl = getImageUrl;
    }

    public boolean isGetImageDocNum() {
        return isGetImageDocNum;
    }

    public void setGetImageDocNum(boolean getImageDocNum) {
        isGetImageDocNum = getImageDocNum;
    }

    public void storeGetImageDocNum(boolean getImageDocNum) {
        storeProperties(properties_isGetImageDocNum, getImageDocNum + "");
        isGetImageDocNum = getImageDocNum;
    }

    public boolean isDownloadImage() {
        return isDownloadImage;
    }

    public void setDownloadImage(boolean downloadImage) {
        isDownloadImage = downloadImage;
    }

    public void storeDownloadImage(boolean downloadImage) {
        storeProperties(properties_isDownloadImage, downloadImage + "");
        isDownloadImage = downloadImage;
    }

    public boolean isRename() {
        return isRename;
    }

    public void setRename(boolean rename) {
        isRename = rename;
    }

    public void storeRename(boolean rename) {
        storeProperties(properties_isRename, rename + "");
        isRename = rename;
    }

    public boolean isGetPicNameFile() {
        return isGetPicNameFile;
    }

    public void setGetPicNameFile(boolean getPicNameFile) {
        isGetPicNameFile = getPicNameFile;
    }

    public void storeGetPicNameFile(boolean isGetPicNameFile) {
        storeProperties(properties_isGetPicNameFile, isGetPicNameFile + "");
        this.isGetPicNameFile = isGetPicNameFile;
    }

    private void storeProperties(String key, String value) {
        File file = new File(folderPath + File.separator + "config", "task.properties");
        if (!file.exists()) throw new RuntimeException("TaskFolderState配置文件不见了，删除" + getFolderName() + " 后重试");
        Properties properties = PropertiesUtil.loadProperties(file);
        properties.setProperty(key, value);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, "");
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            L.exception(e);
            throw new RuntimeException("TaskFolderState配置文件不见了，删除" + getFolderName() + " 后重试");
        } catch (IOException e) {
            L.exception(e);
            throw new RuntimeException("写入TaskFolderState配置文件失败，删除" + getFolderName() + " 后重试");
        }
    }


    public boolean store() throws IOException {
        File file = new File(getFolderPath());
        if (!file.exists()) {
            if (!file.mkdir()) {
                return false;
            } else {
                File configFolder = new File(file, "config");
                configFolder.mkdir();
                File propertiesFile = new File(configFolder, "task.properties");
                //写入配置文件
                Properties properties = PropertiesUtil.loadProperties(propertiesFile);
                properties.setProperty(TaskFolderState.properties_isDownloadImage, isDownloadImage()+"");
                properties.setProperty(TaskFolderState.properties_isGetImageUrl, isGetImageUrl()+"");
                properties.setProperty(TaskFolderState.properties_isGetImageDocNum, isGetImageDocNum()+"");
                properties.setProperty(TaskFolderState.properties_isRename, isRename()+"");
                properties.setProperty(TaskFolderState.properties_isGetPicNameFile, isGetPicNameFile()+"");
                properties.setProperty(TaskFolderState.properties_isGetTrademarkRow, isGetTrademarkRow() + "");
                properties.setProperty(TaskFolderState.properties_isImageScreening, isImageScreening() + "");
                properties.setProperty(TaskFolderState.properties_end_image_page_num, getEndImagePageNum() + "");
                properties.setProperty(TaskFolderState.properties_start_image_page_num, getStartImagePageNum() + "");
                properties.setProperty(TaskFolderState.properties_end_trademark_page_num, getEndTrademarkPageNum() + "");
                properties.setProperty(TaskFolderState.properties_start_trademark_page_num, getStartTrademarkPageNum() + "");
                FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
                properties.store(fileOutputStream, "");
                fileOutputStream.close();
            }
        }
        return true;
    }

}
