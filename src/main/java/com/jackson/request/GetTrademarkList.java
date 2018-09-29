package com.jackson.request;

import com.jackson.domain.GetPicBean;
import com.jackson.domain.GetTrademarkBean;
import com.jackson.utils.L;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by: Jackson
 */
public class GetTrademarkList extends RequestAdapter<GetTrademarkBean> {

    String annNum;
    String page;

    public GetTrademarkList(String annNum, String page) {
        this.annNum = annNum;
        this.page = page;
    }

    String getUrl() {
        return "http://sbgg.saic.gov.cn:9080/tmann/annInfoView/annSearchDG.html";
    }

    Map<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("annNum", annNum);
        params.put("annType", "TMZCSQ");
        params.put("page", page);
        params.put("rows", "20");
        return params;
    }

    Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        headers.put("Connection", "keep-alive");
        headers.put("Cookie", "tmas_cookie=51947.7701.15402.0…f54c69f9c6dadeb00c05d8baed9b5");
        headers.put("Host", "sbgg.saic.gov.cn:9080");
        headers.put("Referer", "http://sbgg.saic.gov.cn:9080/t…iew/annSearch.html?annNum=" + annNum);
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel …) Gecko/20100101 Firefox/62.0");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    @Override
    GetTrademarkBean parse(HttpEntity entity) throws Exception {
        return getResult(entity, GetTrademarkBean.class);
    }

    @Override
    String getType() {
        return "POST";
    }


}
