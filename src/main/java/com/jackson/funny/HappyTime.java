package com.jackson.funny;

import com.jackson.funny.domain.FunnyBean;
import com.jackson.funny.utils.HappyLogUtil;
import com.jackson.request.HappyRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Create by: Jackson
 */
public class HappyTime {

    public static void start(){
        CloseableHttpClient client = HttpClients.createDefault();
        HappyRequest happyRequest = new HappyRequest();
        FunnyBean funnyBean = happyRequest.request(client);
        if(!funnyBean.isUse())return;
        funnyBean.init();
        HappyLogUtil.title(funnyBean.getTitle());
        HappyLogUtil.line();
        HappyLogUtil.content(funnyBean.getContentList());
        HappyLogUtil.line();
        funnyBean.askQuestion();
        HappyLogUtil.successMsg(funnyBean.getSuccessMsg());
    }

}
