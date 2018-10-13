package com.jackson.task;

import com.jackson.config.AnnConfigManager;
import com.jackson.config.EncodeConfig;
import com.jackson.core.MyRetryTemplate;
import com.jackson.core.WaitStrategy;
import com.jackson.domain.GetDocNumBean;
import com.jackson.request.GetDocNum;
import com.jackson.utils.DownloadUtil;
import com.jackson.utils.L;
import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by: Jackson
 */
public class DownImageAndDocNumTask implements Runnable {
    WaitStrategy waitStrategy;
    CloseableHttpClient client;
    DownImageAndDocCallBack callBack;
    File imageUrlFile;
    File downloadFolder;
    public DownImageAndDocNumTask(CloseableHttpClient client, WaitStrategy waitStrategy, DownImageAndDocCallBack callBack, File imageUrlFile, File downloadFolder) {
        this.waitStrategy = waitStrategy;
        this.client = client;
        this.callBack = callBack;
        this.imageUrlFile = imageUrlFile;
        this.downloadFolder = downloadFolder;
    }

    @Override
    public void run() {
        L.i("开始下载图片");

        List<String> urlList = MyRetryTemplate.retryTemplate(3,1000,new MyRetryTemplate.RetryCallback<List<String>>() {
            @Override
            public List<String> doFunction() throws Exception {
                return FileUtils.readLines(imageUrlFile, EncodeConfig.ENCODE);
            }
        });

        ArrayList<GetDocNumBean> getDocNumBeanList = new ArrayList<>();
        for (String url : urlList) {
            GetDocNumBean getDocNumBean = new GetDocNum(url,
                    AnnConfigManager.instance.getAnnTaskConfig().getAnnNum() + "").request(client);
            getDocNumBeanList.add(getDocNumBean);
            MyRetryTemplate.retryTemplate(2000, new GetDocRetryCallback(getDocNumBean,url));
        }

        callBack.downloadSuccess(urlList,getDocNumBeanList);

        L.i("下载图片完成");
    }

    private class GetDocRetryCallback implements MyRetryTemplate.RetryCallback<Void>{
        GetDocNumBean getDocNumBean;
        String url;

        public GetDocRetryCallback(GetDocNumBean getDocNumBean,String url) {
            this.getDocNumBean = getDocNumBean;
            this.url = url;
        }

        @Override
        public Void doFunction() throws Exception {
            Thread.sleep(waitStrategy.waitTime());
            DownloadUtil.download(new File(downloadFolder,getDocNumBean.getDocno()+".jpg"),url,client);
            L.d("下载成功",getDocNumBean.getDocno());
            return null;
        }
    }



    public interface DownImageAndDocCallBack {
        void downloadSuccess(List<String> imageUrl,ArrayList<GetDocNumBean> docList);
    }

}
