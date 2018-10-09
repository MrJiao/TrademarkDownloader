package com.jackson.task;

import com.jackson.config.AnnConfigManager;
import com.jackson.core.MyRetryTemplate;
import com.jackson.core.WaitStrategy;
import com.jackson.domain.*;
import com.jackson.request.GetImageUrl;
import com.jackson.request.GetTrademarkList;
import com.jackson.utils.L;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 指定任务区间进行下载 开始位 和结束位,根据配置的分组大小进行返回结果
 * Create by: Jackson
 */
public class GetTrademarkTask implements Runnable {


    WaitStrategy waitStrategy;
    CloseableHttpClient client;
    GetTrademarkCallBack callBack;
    int startPage;
    int endPage;

    public GetTrademarkTask(CloseableHttpClient client, GetTrademarkCallBack callBack, WaitStrategy waitStrategy, int startPage, int endPage) {
        this.client = client;
        this.callBack = callBack;
        this.waitStrategy = waitStrategy;
        this.startPage = startPage;
        this.endPage = endPage;

    }


    /**
     * @param urlList
     * @return 达到GROUP_COUNT数据量后进行返回
     */
    List<Row> rowList = new ArrayList<>();

    private void addMemory(List<Row> urlList) {
        if (urlList == null || urlList.size() == 0) return;

        rowList.addAll(urlList);
        L.d("加入数据 当前数量为：", rowList.size());
    }

    @Override
    public void run() {
        L.i("开始获取Trademark");
        TrademarkConfig trademarkConfig = AnnConfigManager.instance.getTrademarkConfig();
        try {
            for (int i = startPage; i < endPage; i++) {
                L.d("获取 Trademark发送请求号：", i);
                GetTrademarkBean getTrademarkBean = new GetTrademarkList(trademarkConfig.getAnnNum() + "", i + "").request(client);
                addMemory(getTrademarkBean.getRows());
                Thread.sleep(waitStrategy.waitTime());
            }
            Collections.sort(rowList);
            callBack.onTaskSuccess(rowList);
            L.i("获取Trademark完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface GetTrademarkCallBack {
        void onTaskSuccess(List<Row> urlSet);
    }


}
