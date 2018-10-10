package com.jackson.funny.domain;

import com.jackson.funny.utils.HappyInputUtil;
import com.jackson.funny.utils.HappyLogUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Create by: Jackson
 */
public class FunnyQuestion implements Comparable<FunnyQuestion> {

    String id;

    List<String> questionList = new ArrayList<>();
    List<String> errorMsgList = new ArrayList<>();

    Set<String> answers = new HashSet<>();

    FunnyQuestion successChild;
    FunnyQuestion errorChild;

    String successChildId;
    String errorChildId;

    public String getSuccessChildId() {
        return successChildId;
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<String> answers) {
        this.answers = answers;
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

    public void addAnswer(String answer){
        answers.add(answer);
    }

    public static String allRight = "jackson_all";
    public void askQuestion(){
        HappyLogUtil.question(questionList);
        String input = HappyInputUtil.getInput();
        if(answers.contains(input) || answers.contains(allRight)){
            if(successChild!=null)
                successChild.askQuestion();
        }else {
            if(errorChild!=null){
                HappyLogUtil.errerMsg(errorMsgList);
                errorChild.askQuestion();
            }
        }
    }


    @Override
    public int compareTo(FunnyQuestion o) {
        return Integer.parseInt(id)-Integer.parseInt(o.getId());
    }
}
