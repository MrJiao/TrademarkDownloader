package com.jackson.domain;

import java.util.List;

/**
 * Create by: Jackson
 */
public class FunnyBean {

    String title;

    String content;

    List<FunnyQuestion> funnyQuestionList;

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
}
