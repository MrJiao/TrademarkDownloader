package com.jackson.domain;

import java.util.List;

/**
 * Create by: Jackson
 */
public class FunnyQuestion {

    String id;

    List<String> question;

    List<String> errorMsg;

    FunnyQuestion successChild;
    FunnyQuestion errorChild;

    public FunnyQuestion getSuccessChild() {
        return successChild;
    }

    public void setSuccessChild(FunnyQuestion successChild) {
        this.successChild = successChild;
    }

    public FunnyQuestion getErrorChild() {
        return errorChild;
    }

    public void setErrorChild(FunnyQuestion errorChild) {
        this.errorChild = errorChild;
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
