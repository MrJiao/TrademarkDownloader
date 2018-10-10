package com.jackson.funny.test.question;

import com.jackson.funny.domain.FunnyBean;
import com.jackson.funny.domain.FunnyQuestion;

/**
 * Create by: Jackson
 */
public class Question1 extends BaseQuestion{

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
        funnyQuestion.addQuestion("徐老师，我喜欢你，你知道吗?");
        funnyQuestion.addAnswer("知道");
        funnyQuestion.addAnswer("zhidao");
        funnyQuestion.addAnswer("yes");
        funnyQuestion.addAnswer("嗯");
        funnyQuestion.addAnswer("en");
        funnyQuestion.addError("");
        funnyQuestion.setErrorChildId("4");
        funnyQuestion.setSuccessChildId("2");
        return funnyQuestion;
    }

    private FunnyQuestion question4() {
        FunnyQuestion funnyQuestion = new FunnyQuestion();
        funnyQuestion.setId("4");
        funnyQuestion.addQuestion("那现在我告诉你，我喜欢你");
        funnyQuestion.addAnswer(FunnyQuestion.allRight);
        funnyQuestion.setSuccessChildId("2");
        return funnyQuestion;
    }


    private FunnyQuestion question2() {
        FunnyQuestion funnyQuestion = new FunnyQuestion();
        funnyQuestion.setId("2");
        funnyQuestion.addQuestion("那你喜欢我吗？");
        funnyQuestion.addAnswer("嗯");
        funnyQuestion.addAnswer("en");
        funnyQuestion.addAnswer("喜欢");
        funnyQuestion.addAnswer("超喜欢");
        funnyQuestion.setErrorChildId("3");
        return funnyQuestion;
    }

    private FunnyQuestion question3() {
        FunnyQuestion funnyQuestion = new FunnyQuestion();
        funnyQuestion.setId("3");
        funnyQuestion.addQuestion("不喜欢吗?再给你一次机会，喜欢吗?");
        funnyQuestion.addAnswer("嗯");
        funnyQuestion.addAnswer("en");
        funnyQuestion.addAnswer("喜欢");
        funnyQuestion.addAnswer("love");
        funnyQuestion.addAnswer("超喜欢");
        funnyQuestion.addAnswer("xihuan");
        funnyQuestion.setErrorChildId("1");
        return funnyQuestion;
    }
}
