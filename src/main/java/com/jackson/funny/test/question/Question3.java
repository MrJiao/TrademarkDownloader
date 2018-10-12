package com.jackson.funny.test.question;

import com.jackson.funny.domain.FunnyBean;
import com.jackson.funny.domain.FunnyQuestion;

/**
 * Create by: Jackson
 */
public class Question3 extends BaseQuestion{

    @Override
    public FunnyBean initFunnyBean() {
        FunnyBean funnyBean = new FunnyBean();
        funnyBean.setTitle("徐老师考试加油");
        funnyBean.addContent("必过必过必过必过必过必过必过必过必过必过必过必过必过必过必过必过");
        funnyBean.setSuccessMsg("一次必过");
        funnyBean.setUse(true);
        return funnyBean;
    }


    private FunnyQuestion question1() {
        FunnyQuestion funnyQuestion = new FunnyQuestion();
        funnyQuestion.setId("1");
        funnyQuestion.addQuestion("徐老师，好好看书！");
        funnyQuestion.addAnswer(FunnyQuestion.allRight);
        return funnyQuestion;
    }


}
