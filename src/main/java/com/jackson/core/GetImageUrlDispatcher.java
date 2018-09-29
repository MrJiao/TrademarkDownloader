package com.jackson.core;

import com.jackson.task.GetImageUrlTask;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Create by: Jackson
 */
public class GetImageUrlDispatcher {


    public static void doFunction(CloseableHttpClient client, GetImageUrlTask.GetPicBeanCallBack callBack, int startNum, int endNum) {
        new GetImageUrlTask(client,callBack,new FixedWaitStrategy(),startNum,endNum).run();
    }


}
