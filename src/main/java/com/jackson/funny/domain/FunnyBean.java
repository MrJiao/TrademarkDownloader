package com.jackson.funny.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create by: Jackson
 */
public class FunnyBean {
    boolean isUse;
    String id;

    String title;

    String successMsg;



    List<String> contentList = new ArrayList<>();

    List<FunnyQuestion> funnyQuestionList = new ArrayList<>();

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FunnyQuestion> getFunnyQuestionList() {
        return funnyQuestionList;
    }

    public void setFunnyQuestionList(List<FunnyQuestion> funnyQuestionList) {
        this.funnyQuestionList = funnyQuestionList;
    }

    public List<String> getContentList() {
        return contentList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }

    public void addContent(String content){
        contentList.add(content);
    }

    public void addQuestion(FunnyQuestion funnyQuestion){
        funnyQuestionList.add(funnyQuestion);
    }

    //将id 对应上的对象赋值
    public void init(){
        for (FunnyQuestion funnyQuestion : funnyQuestionList) {
            for (FunnyQuestion question : funnyQuestionList) {
                if(StringUtils.equals(funnyQuestion.getErrorChildId(),question.getId())){
                    funnyQuestion.setErrorChild(question);
                }
                if(StringUtils.equals(funnyQuestion.getSuccessChildId(),question.getId())){
                    funnyQuestion.setSuccessChild(question);
                }
            }
        }
        Collections.sort(funnyQuestionList);
    }


    public void askQuestion() {
        //因为问题是连贯，一个接着一个的，是有顺序的
        if(getFunnyQuestionList().size()==0)return;
        FunnyQuestion funnyQuestion = getFunnyQuestionList().get(0);
        funnyQuestion.askQuestion();
    }

}
