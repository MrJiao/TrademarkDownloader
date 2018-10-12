package com.jackson.funny.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackson.funny.domain.FunnyBean;
import com.jackson.funny.test.question.Question1;
import com.jackson.funny.test.question.Question2;
import com.jackson.utils.L;

/**
 * Create by: Jackson
 */
public class HappyJsonCreator {



    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper= new ObjectMapper();
        FunnyBean funnyBean = new ChooseQuestion().question;
        funnyBean.setUse(true);
        String s = mapper.writeValueAsString(funnyBean);
        L.d(s);
    }






}
