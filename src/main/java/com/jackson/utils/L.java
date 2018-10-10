package com.jackson.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by: Jackson
 */
public class L {

    private static final boolean DEBUG = false;
    private static ThreadLocal<LogMan> tl = new ThreadLocal<>();
    private static String TAG = "";

    private static Logger logger = LoggerFactory.getLogger(L.class);
    public static void i(Object... message) {
        if(DEBUG){
            getLogMan().append(TAG).append("INFO").appendThreadInfo().appendTimeInfo().append(message).printOut();
        }else{
            getLogMan().append(TAG).append(message).i();
        }
    }

    public static void e(Object... message) {
        if(DEBUG){
            getLogMan().append(TAG).append("ERROR").appendThreadInfo().appendTimeInfo().append(message).printOut();
        }else{
            getLogMan().append(TAG).append(message).e();
        }
    }

    public static void d(Object... message) {
        if(DEBUG){
            getLogMan().append(TAG).append("DEBUG").appendThreadInfo().appendTimeInfo().append(message).printOut();
        }else{
            getLogMan().append(TAG).append(message).d();
        }
    }

    //输出exception的堆栈信息
    public static void exception(Exception e){
        logger.error(TAG,e);
    }


    public static void console(String message){
        if(StringUtils.isEmpty(message))return;
        StringBuilder sb = new StringBuilder();
        String title = "* * * * * * * * * * * * * * * * * * * * * * * * * * * *";
        sb.append(title).append(System.getProperty("line.separator"));
        int length = message.length();
        int bianju = (title.length() - length)/2;
        for(int i=0;i<bianju-1;i++){
            sb.append(" ");
        }
        sb.append(message);
        for(int i=0;i<bianju-1;i++){
            sb.append(" ");
        }
        sb.append(System.getProperty("line.separator"));
        sb.append(title);
        System.out.println(sb.toString());

    }


    private static LogMan getLogMan() {
        LogMan logMan = tl.get();
        if (logMan == null) {
            logMan = new LogMan();
            tl.set(logMan);
        }
        return logMan;
    }


    private static class LogMan {

        private StringBuilder sb = new StringBuilder();
        //"dd-MMM-yyyy HH:mm:ss:SSS"
        private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");

        LogMan appendThreadInfo() {
            add(Thread.currentThread().getName());
            return this;
        }

        LogMan appendTimeInfo() {
            add(formatter.format(new Date()));
            return this;
        }

        LogMan append(Object... msg) {
            if (msg == null) return this;
            for (Object s : msg) {
                add(s);
            }
            return this;
        }

        LogMan append(Object msg) {
            if (msg == null) return this;
            add(msg);
            return this;
        }

        void i(){
            logger.info(sb.toString());
            release();
        }

        void e(){
            logger.error(sb.toString());
            release();
        }

        void printOut() {
            sb.append(System.lineSeparator());
            System.out.print(sb.toString());
            release();
        }

        private void add(Object msg) {
            sb.append(msg).append(" ");
        }

        private void release() {
            sb.setLength(0);
        }

        public void d() {
            logger.debug(sb.toString());
            release();
        }
    }


}
