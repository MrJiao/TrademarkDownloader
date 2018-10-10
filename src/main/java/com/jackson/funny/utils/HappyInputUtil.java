package com.jackson.funny.utils;

import com.jackson.funny.utils.HappyLogUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * Create by: Jackson
 */
public class HappyInputUtil {

    public static String getInput(String ... chooseStr) {
        Scanner scan = new Scanner(System.in);
        String read = null;
        while (true) {
            read = scan.nextLine();
            if (StringUtils.isEmpty(read))
                continue;

            if(!isMatch(read,chooseStr)){
                printMust(chooseStr);
                continue;
            }
            break;
        }
        return read;
    }

/*    public static boolean getInputYesOrNO() {
        Scanner scan = new Scanner(System.in);
        String read = null;
        while ((read = scan.nextLine()) != null) {
            try {
                if (!StringUtils.equalsIgnoreCase("yes", read) && !StringUtils.equalsIgnoreCase("no", read)) {
                    HappyLogUtil.inputError("你输错啦，只能输yes 或 no");
                    continue;
                }
                break;
            } catch (Exception e) {
                L.console("请输入正确指令 eg:yes");
            }
        }
        if (StringUtils.equalsIgnoreCase("yes", read)) {
            return true;
        } else {
            return false;
        }
    }*/

    private static boolean isMatch(String input,String...msg){
        boolean match = false;
        if(msg==null || msg.length==0)return true;
        for (String s : msg) {
            if(StringUtils.equalsIgnoreCase(input,s)){
                match=true;
                break;
            }
        }
        return match;
    }

    private static void printMust(String... msg){
        String temp="";
        for (String s : msg) {
            temp +=s+" ";
        }
        HappyLogUtil.inputError("你输错啦，只能输入"+temp);
    }




}
