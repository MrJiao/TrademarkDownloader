package com.jackson.core;

import com.jackson.task.DownImageAndDocNumTask;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;

/**
 * Create by: Jackson
 */
public class DownImageDispatcher {

//    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void doFunction(CloseableHttpClient client, DownImageAndDocNumTask.DownImageAndDocCallBack callBack, File downloadFolder, File imageUrlFile) {
        CloseableHttpClient downloadClient = HttpClients.createDefault();
        new DownImageAndDocNumTask(downloadClient,new FixedWaitStrategy(100),callBack,imageUrlFile,downloadFolder).run();
    }


}
