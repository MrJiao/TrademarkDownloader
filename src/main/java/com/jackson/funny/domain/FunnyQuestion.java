package com.jackson.funny.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by: Jackson
 */
public class FunnyQuestion {

    String id;

    List<String> questionList = new ArrayList<>();

    List<String> errorMsgList = new ArrayList<>();

    FunnyQuestion successChild;
    FunnyQuestion errorChild;

    String successChildId;
    String errorChildId;

    public String getSuccessChildId() {
        return successChildId;
    }

    public void setSuccessChildId(String successChildId) {
        this.successChildId = successChildId;
    }

    public String getErrorChildId() {
        return errorChildId;
    }

    public void setErrorChildId(String errorChildId) {
        this.errorChildId = errorChildId;
    }

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

    public List<String> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<String> questionList) {
        this.questionList = questionList;
    }

    public List<String> getErrorMsgList() {
        return errorMsgList;
    }

    public void setErrorMsgList(List<String> errorMsgList) {
        this.errorMsgList = errorMsgList;
    }

    public void addQuestion(String question){
        questionList.add(question);
    }

    public void addError(String errorMsg){
        errorMsgList.add(errorMsg);
    }
}
