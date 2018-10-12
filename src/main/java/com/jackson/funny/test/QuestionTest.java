package com.jackson.funny.test;

import com.jackson.funny.domain.FunnyBean;
import com.jackson.funny.domain.FunnyQuestion;
import com.jackson.funny.utils.HappyLogUtil;
import com.jackson.utils.L;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Create by: Jackson
 */
public class QuestionTest {

    private static FunnyBean funnyBean = new ChooseQuestion().question;
    public static void main(String[] args){
        checkId();
        checkLoop();
        checkInput();
        L.d("检测成功");
    }

    private static void checkLoop() {

        List<FunnyQuestion> funnyQuestionList = funnyBean.getFunnyQuestionList();
        for (FunnyQuestion funnyQuestion : funnyQuestionList) {
            if(StringUtils.equals(funnyQuestion.getId(),funnyQuestion.getSuccessChildId())){
                throw new RuntimeException("成功id和自己id重复了:"+funnyQuestion.getId());
            }
        }

    }

    private static void checkInput() {
        if(!funnyBean.isUse())return;
        funnyBean.init();
        HappyLogUtil.title(funnyBean.getTitle());
        HappyLogUtil.line();
        HappyLogUtil.content(funnyBean.getContentList());
        HappyLogUtil.line();
        funnyBean.askQuestion();
        HappyLogUtil.successMsg(funnyBean.getSuccessMsg());
    }

    public static void checkId(){
        List<FunnyQuestion> funnyQuestionList = funnyBean.getFunnyQuestionList();
        for (FunnyQuestion funnyQuestion : funnyQuestionList) {
            for (FunnyQuestion question : funnyQuestionList) {
                if(funnyQuestionList.indexOf(funnyQuestion)!=funnyQuestionList.indexOf(question)){
                    if(StringUtils.equals(question.getId(),funnyQuestion.getId())){
                        throw new RuntimeException("id有重复:"+question.getId());
                    }
                }
            }
        }
    }




}
