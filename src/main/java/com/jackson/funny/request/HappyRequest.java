package com.jackson.funny.request;

import com.jackson.funny.domain.FunnyBean;
import com.jackson.funny.domain.FunnyQuestion;

/**
 * Create by: Jackson
 */
public class HappyRequest {


    public FunnyBean get() {
        FunnyBean funnyBean = new FunnyBean();
        funnyBean.setTitle("我是标题");

        funnyBean.addContent("我是内容1");
        funnyBean.addContent("我是内容2");

        funnyBean.addQuestion(question1());
        funnyBean.addQuestion(question2());

        return funnyBean;

    }

    private FunnyQuestion question1() {
        FunnyQuestion funnyQuestion = new FunnyQuestion();
        funnyQuestion.setId("1");
        funnyQuestion.addQuestion("请问人类有多少？");
        funnyQuestion.addQuestion("a:13 b:14 c:16");
        funnyQuestion.addError("回答错误");
        funnyQuestion.setErrorChildId("1");
        funnyQuestion.setSuccessChildId("2");
        return funnyQuestion;
    }

    private FunnyQuestion question2() {
        FunnyQuestion funnyQuestion = new FunnyQuestion();
        funnyQuestion.setId("2");
        funnyQuestion.addQuestion("你是美女吗？");
        funnyQuestion.addQuestion("a:是 b:不是");
        funnyQuestion.addError("回答错误");
        funnyQuestion.addError("回答错误！！！");
        funnyQuestion.setErrorChildId("2");
        return funnyQuestion;
    }


}
