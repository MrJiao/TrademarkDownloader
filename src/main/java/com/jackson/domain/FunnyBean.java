package com.jackson.domain;

import java.util.List;

/**
 * Create by: Jackson
 */
public class FunnyBean {


    String id;

    List<String> question;

    List<String> errorMsg;

    FunnyBean child;

    public FunnyBean getChild() {
        return child;
    }

    public void setChild(FunnyBean child) {
        this.child = child;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getQuestion() {
        return question;
    }

    public void setQuestion(List<String> question) {
        this.question = question;
    }

    public List<String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(List<String> errorMsg) {
        this.errorMsg = errorMsg;
    }
}
