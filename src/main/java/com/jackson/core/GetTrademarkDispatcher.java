package com.jackson.core;

import com.jackson.task.GetTrademarkTask;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


/**
 * Create by: Jackson
 */
public class GetTrademarkDispatcher {

//    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void doFunction(CloseableHttpClient client, GetTrademarkTask.GetTrademarkCallBack callBack,int startPage,int endPage) {
        CloseableHttpClient downloadClient = HttpClients.createDefault();
        new GetTrademarkTask(downloadClient,callBack,new FixedWaitStrategy(100),startPage,endPage).run();
    }


}
