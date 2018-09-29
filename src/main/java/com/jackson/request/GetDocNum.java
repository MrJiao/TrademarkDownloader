package com.jackson.request;

import com.jackson.domain.GetDocNumBean;
import org.apache.http.HttpEntity;


import java.util.HashMap;
import java.util.Map;

/**
 * 通过图片地址获取页码
 * Create by: Jackson
 */
public class GetDocNum extends RequestAdapter<GetDocNumBean> {

    String imgurl;
    String annNum;

    public GetDocNum(String imgurl, String annNum) {
        this.imgurl = imgurl;
        this.annNum = annNum;
    }

    @Override
    String getUrl() {
        return "http://sbgg.saic.gov.cn:9080/tmann/annInfoView/selectDocNoByUrl.html";
    }

    @Override
    Map<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("imgurl", imgurl);
        return params;
    }

    @Override
    Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Cookie", "tmas_cookie=51947.7680.15402.0…f3e1653e44f7a6d023b760fde36e5");
        headers.put("Host", "sbgg.saic.gov.cn:9080");
        headers.put("Referer", "http://sbgg.saic.gov.cn:9080/t…iew/annSearch.html?annNum=" + annNum);
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel …) Gecko/20100101 Firefox/62.0");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    @Override
    GetDocNumBean parse(HttpEntity entity)throws Exception {
        return getResult(entity, GetDocNumBean.class);
    }

    @Override
    String getType() {
        return "POST";
    }

}
