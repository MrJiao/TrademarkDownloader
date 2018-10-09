package com.jackson.funny;

import com.jackson.funny.domain.FunnyBean;
import com.jackson.funny.request.HappyRequest;
import com.jackson.funny.utils.HappyLogUtil;

/**
 * Create by: Jackson
 */
public class HappyTime {

    public static void main(String[] args){
        HappyRequest happyRequest = new HappyRequest();

        FunnyBean funnyBean = happyRequest.get();

        funnyBean.init();

        HappyLogUtil.title(funnyBean.getTitle());
        HappyLogUtil.content(funnyBean.getContentList());





    }

}
