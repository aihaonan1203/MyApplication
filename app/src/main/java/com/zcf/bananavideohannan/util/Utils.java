package com.zcf.bananavideohannan.util;

import android.text.TextUtils;

import com.gaotai.baselib.util.DcDateUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nanchen.compresshelper.StringUtil.isSpace;

public class Utils {
    public static String getStrPlayNum(int playNum) {
        DecimalFormat df = new DecimalFormat("##0.0");
        StringBuffer sbstr = new StringBuffer();
        if (playNum < 10000) {
            sbstr.append(String.valueOf(playNum)).append("次播放");
        } else {
            double newPlayNum = playNum;
            String dnum = df.format(newPlayNum / 10000);
            sbstr.append(String.valueOf(dnum)).append("万次播放");
        }
        return sbstr.toString();
    }

    /**
     * 验证手机号是否合法
     * @return
     */
    public static boolean isMobileNO(String mobile){
        if (mobile.length() != 11)
        {
            return false;
        }else{
            /**
             * 移动号段正则表达式
             */
            String pat1 = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
            /**
             * 联通号段正则表达式
             */
            String pat2  = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
            /**
             * 电信号段正则表达式
             */
            String pat3  = "^((133)|(153)|(177)|(18[0,1,9])|(149))\\d{8}$";
            /**
             * 虚拟运营商正则表达式
             */
            String pat4 = "^((170))\\d{8}|(1718)|(1719)\\d{7}$";

            Pattern pattern1 = Pattern.compile(pat1);
            Matcher match1 = pattern1.matcher(mobile);
            boolean isMatch1 = match1.matches();
            if(isMatch1){
                return true;
            }
            Pattern pattern2 = Pattern.compile(pat2);
            Matcher match2 = pattern2.matcher(mobile);
            boolean isMatch2 = match2.matches();
            if(isMatch2){
                return true;
            }
            Pattern pattern3 = Pattern.compile(pat3);
            Matcher match3 = pattern3.matcher(mobile);
            boolean isMatch3 = match3.matches();
            if(isMatch3){
                return true;
            }
            Pattern pattern4 = Pattern.compile(pat4);
            Matcher match4 = pattern4.matcher(mobile);
            boolean isMatch4 = match4.matches();
            if(isMatch4){
                return true;
            }
            return false;
        }
    }


    public static String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis*1000);
        return simpleDateFormat.format(date);
    }

    public static String stampToDate(long timeMillis,String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time);
        Date date = new Date(timeMillis*1000);
        return simpleDateFormat.format(date);
    }

    public static String getStrDate(String datesin) {
        long time = Long.parseLong(datesin);
        Date date = new Date(time);
        return DcDateUtils.toString(date, DcDateUtils.FORMAT_HM_5);
    }

    public static String getFileName(final String filePath) {
        if (isSpace(filePath)) return "";
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    public static String getPhone(String phone) {
        String newMobile = "";
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            String mobile = phone;
            String start = mobile.substring(0, 4);
            String end = mobile.substring(7);
            newMobile = start + "****" + end;
            return newMobile;
        }
        return "";
    }
}
