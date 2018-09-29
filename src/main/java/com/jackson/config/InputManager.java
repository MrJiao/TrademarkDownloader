package com.jackson.config;

import com.jackson.domain.PreAnnConfig;
import com.jackson.utils.L;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Create by: Jackson
 */
public class InputManager {



    public String getInput(){
        String read = null;
        Scanner scan = new Scanner(System.in);
        while ((read = scan.nextLine()) != null) {
            if(StringUtils.isEmpty(read)){
                L.console("别闹，写点什么");
            }
        }
        return read;
    }


    public static String getInputAnnNumber() {

        L.console("请输入公告期号 eg:1614");
        String read = null;
        Scanner scan = new Scanner(System.in);
        while ((read = scan.nextLine()) != null) {
            try {
                Integer.parseInt(read);
                break;
            } catch (Exception e) {
                L.console("请输入正确的公告期号 eg:1614");
            }
        }
        return read;
    }

    public static String getInputStartPage() {
        L.console("请输入开始位 eg:1");
        Scanner scan = new Scanner(System.in);
        String read = null;
        while ((read = scan.nextLine()) != null) {
            try {
                Integer.parseInt(read);
                break;
            } catch (Exception e) {
                L.console("请输入正确的开始位 eg:1");
            }
        }

        return read;
    }

    public static String getInputEndPage() {
        L.console("请输入结束位 eg:100");
        Scanner scan = new Scanner(System.in);
        String read = null;
        while ((read = scan.nextLine()) != null) {
            try {
                Integer.parseInt(read);
                break;
            } catch (Exception e) {
                L.console("请输入正确的结束位 eg:100");
            }
        }

        return read;
    }

    public static String getInputContinueState() {
        L.console("是否继续上次任务 输入:yes/no");
        Scanner scan = new Scanner(System.in);
        String read = null;
        while ((read = scan.nextLine()) != null) {
            try {
                if (!StringUtils.equalsIgnoreCase("yes", read) && !StringUtils.equalsIgnoreCase("no", read)) {
                    L.console("请输入正确指令 eg:yes");
                    continue;
                }
                break;
            } catch (Exception e) {
                L.console("请输入正确指令 eg:yes");
            }
        }
        return read;
    }

    public static String getInputDelayState() {
        L.console("是否要延迟执行 输入:yes/no");
        Scanner scan = new Scanner(System.in);
        String read = null;
        while ((read = scan.nextLine()) != null) {
            try {
                if (!StringUtils.equalsIgnoreCase("yes", read) && !StringUtils.equalsIgnoreCase("no", read)) {
                    L.console("请输入正确指令 eg:no");
                    continue;
                }
                break;
            } catch (Exception e) {
                L.console("请输入正确指令 eg:no");
            }
        }
        return read;
    }

    public static long getInputDelay() {
        L.console("请输入延迟时间(分钟) eg:30");
        Scanner scan = new Scanner(System.in);
        String read = null;
        long delay =0;
        while ((read = scan.nextLine()) != null) {
            try {
                delay = Long.parseLong(read);
                if (delay<=0) {
                    L.console("延迟时间必须大于0分钟");
                    continue;
                }
                break;
            } catch (Exception e) {
                L.console("请输入正整数");
            }
        }
        return delay;
    }

    public static boolean getInputShutdownState() {
        L.console("任务完成后是否关机 输入:yes/no");
        Scanner scan = new Scanner(System.in);
        String read = null;
        while ((read = scan.nextLine()) != null) {
            try {
                if (!StringUtils.equalsIgnoreCase("yes", read) && !StringUtils.equalsIgnoreCase("no", read)) {
                    L.console("请输入正确指令 eg:no");
                    continue;
                }
                break;
            } catch (Exception e) {
                L.console("请输入正确指令 eg:no");
            }
        }
        read = StringUtils.equalsIgnoreCase(read,"yes")?"true":"false";
        return Boolean.parseBoolean(read);
    }


    public static void init() {
        initProAnnConfig();
        initDelay();
        initShutDown();
    }

    private static void initShutDown() {
        boolean isShutdownState = getInputShutdownState();
        GlobalConfigManager gConfig = GlobalConfigManager.instance;
        PreAnnConfig preAnnConfig = gConfig.getPreAnnConfig();
        preAnnConfig.setShutDownIfComplete(isShutdownState);
    }

    private static void initProAnnConfig() {
        GlobalConfigManager gConfig = GlobalConfigManager.instance;
        PreAnnConfig preAnnConfig = gConfig.getPreAnnConfig();
        //没有任务，输入初始化任务信息
        if (!StringUtils.equals(preAnnConfig.getState(), PreAnnConfig.STATE_START)) {
            reSetPreAnnConfig(preAnnConfig);
        } else {//有任务，判断是否继续
            String continueState = getInputContinueState();
            if (StringUtils.equalsIgnoreCase("no", continueState)) {
                reSetPreAnnConfig(preAnnConfig);
            }
        }
    }

    private static void initDelay() {
        GlobalConfigManager gConfig = GlobalConfigManager.instance;
        PreAnnConfig preAnnConfig = gConfig.getPreAnnConfig();
        String delayState = getInputDelayState();
        if (StringUtils.equalsIgnoreCase("yes", delayState)) {
            long delay = getInputDelay();
            preAnnConfig.setWaitTime(delay);
        }
    }


    private static void reSetPreAnnConfig(PreAnnConfig preAnnConfig) {
        String annNum = InputManager.getInputAnnNumber();
        String startPage = InputManager.getInputStartPage();
        String endPage = InputManager.getInputEndPage();
        preAnnConfig.setStart_page_num(Integer.parseInt(startPage));
        preAnnConfig.setEnd_page_num(Integer.parseInt(endPage));
        preAnnConfig.setAnnNum(Integer.parseInt(annNum));
        preAnnConfig.setState(PreAnnConfig.STATE_START);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月dd日HH时mm分ss秒");
        String date = simpleDateFormat.format(new Date());
        preAnnConfig.setAnn_folder_name("公告期号_"+annNum + "_" + date);

    }
}
