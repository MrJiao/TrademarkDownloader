import com.jackson.domain.*;
import com.jackson.funny.domain.FunnyQuestion;
import com.jackson.request.GetDocNum;
import com.jackson.request.GetFunny;
import com.jackson.request.GetImageUrl;
import com.jackson.request.GetTrademarkList;
import com.jackson.utils.ChineseUtil;
import com.jackson.utils.L;
import com.jackson.utils.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by: Jackson
 */
public class Temp {


    @Test
    public void getPicBean() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        GetPicBean getPicBean = new GetImageUrl("e48b921465949e0b01659ce474b87a06", (1) + "").request(client);
        GetPicBean getPicBean2 = new GetImageUrl("e48b921465949e0b01659ce474b87a06", (21) + "").request(client);
        L.i(getPicBean);
    }

    @Test
    public void getDocNum() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://sbggwj.saic.gov.cn:8000/tmann/group1/M00/FC/81/yBQCH1uP71eAU7_YAAIwbaIwI7E896.jpg";
        GetDocNum getDocNum = new GetDocNum(url, "1614");
        GetDocNumBean getDocNumBean = getDocNum.request(client);
        L.i(getDocNumBean);
    }

    @Test
    public void loadPro() throws IOException {
        File file = new File("/Users/jiaoyubing/Desktop/a/a.properties");
        Properties properties = PropertiesUtil.loadProperties(file);
        properties.setProperty("a", "1");
        properties.store(new FileOutputStream(file), "");
    }

    @Test
    public void time() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月dd日HH时mm分ss秒");
        String format = simpleDateFormat.format(new Date());
        L.i(format);
    }

    @Test
    public void log() {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("logloglog");
    }

    @Test
    public void log2() {
        L.console("中国好男人阿斯蒂芬123");
    }


    @Test
    public void getTrademarkList() throws InterruptedException {
        CloseableHttpClient client = HttpClients.createDefault();

        GetTrademarkBean getTrademarkBean = new GetTrademarkList("1614", 1 + "").request(client);

        L.i(getTrademarkBean);
    }

    @Test
    public void ceil() {
        int i = 20;

        int page = (int) Math.ceil(((float) i) / 20);
        L.i(page);

        int i1 = (page * 20);
        L.i(i1);
    }


    @Test
    public void show() throws InterruptedException {
        CloseableHttpClient client = HttpClients.createDefault();

        TreeSet<Row> rowsTree = new TreeSet<>();
        int index = 4;
        int end = 15;
        for (int i = index; i < end; i++) {
            Thread.sleep(150);
            List<Row> rows = new GetTrademarkList("1614", i + "").request(client).getRows();
            rowsTree.addAll(rows);
        }

        int j = (index - 1) * 20 + 1;
        for (Row row : rowsTree) {
            if (row.getPage_no() != j) {
                while (row.getPage_no() != j) {
                    L.i(j);
                    j++;
                }
            }
            j++;

        }

    }

    @Test
    public void rename() {
        File file = new File("/Users/jiaoyubing/work_space/tempworkspace/MyPostMan/公告期号_1614_18年09月17日14时02分01秒/1_1_3/config/docNum.txt");
        file.renameTo(new File(file.getParentFile(), "修改后的.txt"));

    }

    @Test
    public void reNameIterator() {

        String[] imageStrs = {"4", "5", "6", "7", "8", "9", "10", "11", "13", "14"};
        String[] rowStrs = {"1", "3", "4", "5", "6", "9","11", "13", "14"};


        int rowIndex = 0;
        for (int i = 0; i < rowStrs.length; i++) {
            String rowStr = rowStrs[i];
            if (StringUtils.equals(imageStrs[0], rowStr)) {
                rowIndex = i;
            }
        }


        for (int i = 0; i < imageStrs.length; i++) {
            String imageStr = imageStrs[i];
            String rowStr = rowStrs[rowIndex];
            if (StringUtils.equals(imageStr, rowStr)) {
                L.d(imageStr, rowStr, "相等");
                rowIndex++;
            } else {
                L.d(imageStr, rowStr, "不相等");
            }

        }
    }

    @Test
    public void reNameIterator2() {

        int[] imageNums = {2,3,4,5,6,7,8,9,10,11,12,13,14};
        int[] rowNums = {1,4,6,7,8,9,10,11,12,13,14};


        int rowIndex = 0;
        for(int imageIndex = 0;imageIndex<imageNums.length;imageIndex++){
            if(imageNums[imageIndex]>rowNums[rowIndex]){
                rowIndex++;
                continue;
            }
            if(imageNums[imageIndex]==rowNums[rowIndex]){
                L.d(imageNums[imageIndex],rowNums[rowIndex],"相等");
                rowIndex++;
            }


        }

    }

    @Test
    public void split() {
        String name = "10.jpg";
        String s = name.split("\\.")[0];
        L.d(s);
    }


    @Test
    public void chineseUtil(){
        String str = "啊";
        boolean chinese = ChineseUtil.isChinese(str);
        L.d(chinese);
    }

    @Test
    public void funny(){
        String packageName = "com.jackson.main";
        String className = "com.jackson.main.Per";
        String substring = className.substring(packageName.length()+1);
        L.i(substring);

    }

    @Test
    public void simpleName(){
        String name = "1614__里查德米尔RICHARD MILLE里查.jpg";
        String s = name.split("__")[1];
        String simpleName = s.split("\\.")[0];


        String str = simpleName;

       // String reg = "[\u4e00-\u9fa5]";
        String reg = "^[\u4e00-\u9fa5]+$";

        Pattern pat = Pattern.compile(reg);

        Matcher mat=pat.matcher(str);

        String repickStr = mat.replaceAll("");

        L.i("去中文后:"+repickStr);

    }

    @Test
    public void getFunny(){
        CloseableHttpClient client = HttpClients.createDefault();
        FunnyQuestion request = new GetFunny().request(client);
        L.i(request);
    }

    @Test
    public void funnyJson(){
        FunnyQuestion funnyQuestion = new FunnyQuestion();
        funnyQuestion.setErrorMsgList(Arrays.asList("我是一个人","我是两个人"));
        funnyQuestion.setQuestionList(Arrays.asList("1234567","1234567"));
        funnyQuestion.setId("1");
    }


}