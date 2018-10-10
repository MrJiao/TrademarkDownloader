package com.jackson.funny.test.question;

import com.jackson.funny.domain.FunnyBean;
import com.jackson.funny.domain.FunnyQuestion;

/**
 * Create by: Jackson
 */
public class Question2 extends BaseQuestion{

    @Override
    public FunnyBean initFunnyBean() {
        FunnyBean funnyBean = new FunnyBean();
        funnyBean.setTitle("徐老师和我的HappyTime");
        funnyBean.addContent("从今天开始，我每天都会告诉你我有多喜欢你");
        funnyBean.addContent("爱，就要大声说出来");
        funnyBean.setSuccessMsg("么么哒");
        funnyBean.setUse(true);
        return funnyBean;
    }


    private FunnyQuestion question1() {
        FunnyQuestion funnyQuestion = new FunnyQuestion();
        funnyQuestion.setId("1");
        funnyQuestion.addQuestion("徐老师，回来给我买葡萄");
        funnyQuestion.addAnswer("知道");
        funnyQuestion.addAnswer("zhidao");
        funnyQuestion.addAnswer("yes");
        funnyQuestion.addAnswer("嗯");
        funnyQuestion.addAnswer("en");
        funnyQuestion.addAnswer("hao");
        funnyQuestion.addAnswer("好");
        funnyQuestion.addError("");
        funnyQuestion.setErrorChildId("1");
        return funnyQuestion;
    }


}
