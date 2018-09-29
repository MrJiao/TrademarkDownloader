package com.jackson.config;

import com.jackson.domain.*;
import com.jackson.exception.InitException;
import com.jackson.request.GetImageUrl;
import com.jackson.request.GetImageSearchId;
import com.jackson.request.GetTrademarkList;
import com.jackson.utils.L;
import com.jackson.utils.PropertiesUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Create by: Jackson
 */
public enum AnnConfigManager {
    instance;

    GlobalConfigManager gConfig = GlobalConfigManager.instance;
    int groupCount = 1000;

    public void init() {
        if (!initAnnFolder()) {
            throw new InitException("初始化期号文件夹失败");
        } else {
            L.i("初始化期号文件夹成功");
        }
        try {
            initAnnGlobalTaskConfig();
            L.i("初始化总任务数信息成功");
        } catch (IOException e) {
            throw new InitException("初始化总任务数信息失败");
        }
        try {
            if (!initTaskFolderAndState()) {
                throw new InitException("初始化子任务文件夹失败");
            } else {
                L.i("创建子任务文件夹成功");
            }
        } catch (IOException e) {
            throw new InitException("初始化子任务文件夹失败");
        }


    }


    public String getAnnFolderPath() {
        return gConfig.getRuntimePath() + File.separator + gConfig.getPreAnnConfig().getAnn_folder_name();
    }

    private boolean initAnnFolder() {
        File file = new File(getAnnFolderPath());
        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }

    AnnTaskConfig annTaskConfig;
    TrademarkConfig trademarkConfig;

    //获取总任务数等信息g
    private void initAnnGlobalTaskConfig() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        initAnnTaskConfig(client);
        initTrademarkConfig(client);
    }

    private void initTrademarkConfig(CloseableHttpClient client) {
        trademarkConfig = new TrademarkConfig();
        PreAnnConfig preAnnConfig = gConfig.getPreAnnConfig();
        int annNum = preAnnConfig.getAnnNum();
        trademarkConfig.setAnnNum(annNum);
        GetTrademarkBean getTrademarkBean = new GetTrademarkList(annNum + "", "1").request(client);
        long total = getTrademarkBean.getTotal();
        int page = (int) Math.ceil((total) / 20);
        trademarkConfig.setTotalSize(total);
        trademarkConfig.setTotalPage(page);
    }

    private void initAnnTaskConfig(CloseableHttpClient client) {
        annTaskConfig = new AnnTaskConfig();
        PreAnnConfig preAnnConfig = gConfig.getPreAnnConfig();
        annTaskConfig.setAnnNum(preAnnConfig.getAnnNum());
        //初始化任务文件夹
        String searchId = new GetImageSearchId(annTaskConfig.getAnnNum()).request(client);
        annTaskConfig.setSearchId(searchId);
        GetPicBean getPicBean = new GetImageUrl(annTaskConfig.getSearchId(), "1").request(client);//为了获取总共页数
        annTaskConfig.setPageSize(getPicBean.getPageSize());
        int endPageNum = preAnnConfig.getEnd_page_num();
        int total = getPicBean.getTotal();
        if (endPageNum > total) {
            endPageNum = total;
        }
        annTaskConfig.setTotal(endPageNum);
    }

    //获取trademarkList的富余量，因为他的页码获取不能每次都准确分页，为了保证数据有，所有增加了单个文件获取的页码
    private static final int affluentPage = 4;

    private boolean initTaskFolderAndState() throws IOException {
        AnnTaskConfig annTaskConfig = getAnnTaskConfig();
        int start_page_num = GlobalConfigManager.instance.getPreAnnConfig().getStart_page_num();
        int picTotal = annTaskConfig.getTotal();
        //算出有的文件夹
        ArrayList<TaskFolderState> folderList = new ArrayList<>();

        int current = start_page_num;
        int i = 1;//序号，用来排序文件夹的
        while (current < picTotal) {
            int end = current + getGroupCount();
            if (end > picTotal) {
                end = picTotal + 1;
            }
            TaskFolderState taskFolderState = new TaskFolderState();
            taskFolderState.setFolderName(i + "_" + current + "_" + (end - 1));
            taskFolderState.setFolderPath(getAnnFolderPath() + File.separator + taskFolderState.getFolderName());
            taskFolderState.setStartImagePageNum(current);
            current = end;
            taskFolderState.setEndImagePageNum((current - 1));
            folderList.add(taskFolderState);
            i++;
        }


        for (TaskFolderState folderState : folderList) {
            int startImagePageNum = folderState.getStartImagePageNum();
            int endImagePageNum = folderState.getEndImagePageNum();

            int startTrademarkPageNum = (int) (Math.ceil(((float) startImagePageNum) / 20) - affluentPage);
            int endTrademarkPageNum = (int) (Math.ceil(((float) endImagePageNum) / 20) + affluentPage);

            int maxTrademarkPageNum = getTrademarkConfig().getTotalPage();

            if (startTrademarkPageNum < 1) {
                startTrademarkPageNum = 1;
            }

            if (endTrademarkPageNum > maxTrademarkPageNum) {
                endTrademarkPageNum = maxTrademarkPageNum;
            }

            folderState.setStartTrademarkPageNum(startTrademarkPageNum);
            folderState.setEndTrademarkPageNum(endTrademarkPageNum);
        }

        //存储配置文件，失败一个都不行
        for (TaskFolderState folderState : folderList) {
            if (!folderState.store()) {
                return false;
            }
        }
        return true;
    }

    public AnnTaskConfig getAnnTaskConfig() {
        return annTaskConfig;
    }

    public void setAnnTaskConfig(AnnTaskConfig annTaskConfig) {
        this.annTaskConfig = annTaskConfig;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public List<TaskFolderState> getTaskFolderStateList() {
        File file = new File(getAnnFolderPath());
        File[] files = file.listFiles();
        ArrayList<TaskFolderState> arr = new ArrayList<>();
        for (File f : files) {
            if (!f.isDirectory()) continue;
            File configFolder = new File(f, "config");
            if (!configFolder.exists()) continue;
            File propertiesFile = new File(configFolder, "task.properties");
            Properties properties = PropertiesUtil.loadProperties(propertiesFile);
            TaskFolderState folderState = new TaskFolderState();

            folderState.setStartImagePageNum(Integer.parseInt(properties.getProperty(TaskFolderState.properties_start_image_page_num)));
            folderState.setEndImagePageNum(Integer.parseInt(properties.getProperty(TaskFolderState.properties_end_image_page_num)));
            folderState.setEndTrademarkPageNum(Integer.parseInt(properties.getProperty(TaskFolderState.properties_end_trademark_page_num)));
            folderState.setStartTrademarkPageNum(Integer.parseInt(properties.getProperty(TaskFolderState.properties_start_trademark_page_num)));
            folderState.setGetImageUrl(Boolean.parseBoolean(properties.getProperty(TaskFolderState.properties_isGetImageUrl)));
            folderState.setGetImageDocNum(Boolean.parseBoolean(properties.getProperty(TaskFolderState.properties_isGetImageDocNum)));
            folderState.setDownloadImage(Boolean.parseBoolean(properties.getProperty(TaskFolderState.properties_isDownloadImage)));
            folderState.setRename(Boolean.parseBoolean(properties.getProperty(TaskFolderState.properties_isRename)));
            folderState.setGetTrademarkRow(Boolean.parseBoolean(properties.getProperty(TaskFolderState.properties_isGetTrademarkRow)));
            folderState.setFolderName(f.getName());
            folderState.setFolderPath(f.getAbsolutePath());

            String[] split = folderState.getFolderName().split("_");
            folderState.setIndex(Integer.parseInt(split[0]));
            arr.add(folderState);
            Collections.sort(arr, new MComparator());
        }
        return arr;
    }

    public String getImageUrlFilePath(TaskFolderState taskFolderState) {
        return taskFolderState.getFolderPath() + File.separator + "config" + File.separator + "image_url.txt";
    }

    public String getDocNumFilePath(TaskFolderState taskFolderState) {
        return taskFolderState.getFolderPath() + File.separator + "config" + File.separator + "docNum.txt";
    }

    public String getImageNameFilePath(TaskFolderState taskFolderState) {
        return taskFolderState.getFolderPath() + File.separator + "config" + File.separator + "imageName.txt";
    }

    public String getTrademarkRowFilePath(TaskFolderState taskFolderState) {
        return taskFolderState.getFolderPath() + File.separator + "config" + File.separator + "trademark_row.txt";
    }

    public TrademarkConfig getTrademarkConfig() {
        return trademarkConfig;
    }

    public String getImageEnglishFolder(TaskFolderState taskFolderState) {
        return taskFolderState.getFolderPath() + File.separator + "english";
    }

    public String getImageChineseFolder(TaskFolderState taskFolderState) {
        return taskFolderState.getFolderPath() + File.separator + "chinese";
    }

    public String getPicNameFilePath(TaskFolderState taskFolderState) {
        return getImageEnglishFolder(taskFolderState)+File.separator+"imageNames.txt";
    }


    private static class MComparator implements Comparator<TaskFolderState> {

        @Override
        public int compare(TaskFolderState o1, TaskFolderState o2) {
            return o1.getIndex() - o2.getIndex();
        }
    }


}
