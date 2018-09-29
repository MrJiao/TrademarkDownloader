package com.jackson.postman;

import com.jackson.utils.L;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by: Jackson
 */
public class PostMan {

    public static void main(String[] args){

        try {
            L.d(send());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static String send() throws IOException {
        String url = "http://localhost:7982/tlevel-datax/temp/in";
        //1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();


        HashMap<String, String> paramMap = new HashMap<>();
        HashMap<String, String> headerMap = new HashMap<>();

        paramMap.put("a","1");
        headerMap.put("header","mmm");


        HttpPost httpPost = post(url, paramMap, headerMap);

        CloseableHttpResponse execute = client.execute(httpPost);
        HttpEntity entity = execute.getEntity();
        return EntityUtils.toString(entity);
    }

    public static HttpPost post(String url, Map<String, String> param, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        String reqEntity = "";
        for(Map.Entry<String, String> entry:headers.entrySet()) {
            post.addHeader(entry.getKey(), entry.getValue());

        }

        for (Map.Entry<String, String> entry : param.entrySet()) {
            try {
                reqEntity += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8") + "&";
            } catch (UnsupportedEncodingException e) {
                System.out.println("请求实体转换异常，不支持的字符集，由于 " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

        try {
            post.setEntity(new StringEntity(reqEntity));
        } catch (UnsupportedEncodingException e) {
            System.out.println("请求设置实体异常，不支持的字符集， 由于 " + e.getLocalizedMessage());
            e.printStackTrace();
        }



        return post;
    }





}
