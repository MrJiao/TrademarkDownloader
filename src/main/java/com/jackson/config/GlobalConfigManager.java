package com.jackson.config;

import com.jackson.Main;
import com.jackson.domain.PreAnnConfig;
import com.jackson.utils.L;
import com.jackson.utils.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Create by: Jackson
 */
public enum GlobalConfigManager {
    instance;

    String runtimePath;


    public String getRuntimePath() {
        return runtimePath;
    }

    //如果没有就创建，如果有就不动
    public void init() {
        initRuntimePath();
        initConfigFolderAndProperties();
    }

    //创建全局config文件夹，和上次任务运行状态的properties
    PreAnnConfig preAnnConfig;
    private void initConfigFolderAndProperties() {
        File globalConfigFolder = new File(getGlobalConfigPath());
        if(!globalConfigFolder.exists()){
            globalConfigFolder.mkdir();
        }

        File propertiesFile = new File(getPreAnnConfigPropertiesPath());
        Properties properties = PropertiesUtil.loadProperties(propertiesFile);
        preAnnConfig = new PreAnnConfig();
        String state = properties.getProperty(PreAnnConfig.properties_state, PreAnnConfig.STATE_NO_TASK);
        preAnnConfig.setState(PreAnnConfig.STATE_NO_TASK);
        if(!StringUtils.equals(state,PreAnnConfig.STATE_NO_TASK)){
            String ann_folder_name = properties.getProperty(PreAnnConfig.properties_ann_folder_name, "");
            File annFile = new File(getRuntimePath(), ann_folder_name);
            if(annFile.exists()){
                preAnnConfig.setState(state);
                String start_page_num = properties.getProperty(PreAnnConfig.properties_start_page_num, "");
                String end_page_num = properties.getProperty(PreAnnConfig.properties_end_page_num, "");
                String annNum = properties.getProperty(PreAnnConfig.properties_annNum, "");
                preAnnConfig.setAnn_folder_name(ann_folder_name);
                preAnnConfig.setStart_page_num(Integer.parseInt(start_page_num));
                preAnnConfig.setEnd_page_num(Integer.parseInt(end_page_num));
                preAnnConfig.setAnnNum(Integer.parseInt(annNum));
            }
        }
    }

    public String getPreAnnConfigPropertiesPath(){
        return getGlobalConfigPath()+File.separator+"pre_task.properties";
    }

    public String getGlobalConfigPath() {
        return getRuntimePath() + File.separator + "config";
    }


    private void initRuntimePath() {
        if(Main.isPackage){
            String path = GlobalConfigManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            runtimePath = new File(path).getParent();
        }else {
            runtimePath = System.getProperty("user.dir");
        }

    }


    public PreAnnConfig getPreAnnConfig() {
        return preAnnConfig;
    }

    //持久化当前任务情况
    public void storePreAnnConfig() throws IOException {
        try {
            PreAnnConfig preAnnConfig = getPreAnnConfig();
            File propertiesFile = new File(getPreAnnConfigPropertiesPath());
            Properties properties = PropertiesUtil.loadProperties(propertiesFile);
            properties.setProperty(PreAnnConfig.properties_state,preAnnConfig.getState());
            properties.setProperty(PreAnnConfig.properties_end_page_num,preAnnConfig.getEnd_page_num()+"");
            properties.setProperty(PreAnnConfig.properties_start_page_num,preAnnConfig.getStart_page_num()+"");
            properties.setProperty(PreAnnConfig.properties_ann_folder_name,preAnnConfig.getAnn_folder_name());
            properties.setProperty(PreAnnConfig.properties_annNum,preAnnConfig.getAnnNum()+"");
            FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
            properties.store(fileOutputStream, "");
            fileOutputStream.close();
        }catch (IOException e){
            L.e("存储全局任务信息失败");
        }
    }
}
