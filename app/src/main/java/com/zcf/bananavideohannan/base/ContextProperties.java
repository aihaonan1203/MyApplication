package com.zcf.bananavideohannan.base;

import android.content.Context;
import android.util.Log;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.util.DcDateUtils;
import com.zcf.bananavideohannan.bean.LoginBean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * 将内存中数据存储到程序文件中
 *
 * @author MengLiang
 */
public class ContextProperties {
    public static final String IS_LOGIN_TRUE_YES = "true";
    public static final String IS_LOGIN_TRUE_NO = "false";
    /**
     * 文件名称
     */
    public static String REM_FILENAME = "xjsp.properties";
    /**
     * 用户账号
     */
    public static String REM_ACCOUNUT = "remember.account";
    /**
     * 用户昵称
     */
    public static String REM_NICKNAME = "remember.nickname";
    public static String REM_NAME = "remember.name";
    /**
     * 用户密码
     */
    public static String REM_PASSWORD = "remember.password";
    /**
     * 用户身份ID
     */
    public static String REM_UID = "remember.identityId";

    /**
     * 集团密码
     */
    public static String REM_GROUP_PASSWORD = "remember.group_password";
    /**
     * 用户身份ID
     */
    public static String REM_IS_LOGINED = "remember.islogined";
    /**
     * 用户性别
     */
    public static String REM_SEX = "remember.sex";

    /**
     * 登陆时间
     */
    public static String REM_LOGINTIME = "remember.logintime";
    /**
     * 用户头像
     */
    public static String REM_HEAD_IMG = "remember.headimg";
    /**
     * 邀请码
     */
    public static String REM_YQ_CODE = "remember.yqcode";
    public static String REM_YQ_PERSON = "remember.person";//邀请人
    public static String REM_TIMES = "remember.times";//观看次数
    public static String REM_DAYTIMES = "remember.day.times";//当天观看次数
    public static String REM_TIMESSUPPER = "remember.supper.times";//观看次数上限
    public static String REM_CACHE = "remember.rem_cache";//缓存
    public static String REM_CACHESUPPER = "remember.rem_cache.SUPPER";//缓存上限
    public static String REM_RECOMMEND = "remember.recommend";//推荐人数
    public static String REM_MONEY = "remember.recommend";//余额
    public static String REM_TOKEN = "remember.token";//token
    public static String REM_BLV = "remember.blv";//距离升级剩余邀请人数
    /**
     * 等级
     */
    public static String REM_USER_LEVEL = "remember.level";
    /**
     * 距离下一等差人数
     */
    public static String REM_USER_LESSNUM = "remember.lessnum";
    /**
     * 已播放次数
     */
    public static String REM_USER_PLAYED_NUM = "remember.playnum";
    /**
     * 最大播放次数
     */
    public static String REM_USER_MAX_PLAYNUM = "remember.maxplaynum";
    /**
     * app地址
     */
    public static String REM_USER_MAX_APPURL = "remember.appurl";
    /**
     * 交流群地址
     */
    public static String REM_USER_MAX_LINKURL = "remember.linkurl";

    /**
     * 用户账号手机号码
     */
    public static String REM_USERMOBILE = "remember.usermobile";

    /**
     * 最后一次发送短信验证码的时间
     */
    public static String REM_LAST_SEND_SMS_TIME = "remember.lastSendSmsTime";

    /***
     * 获取文件保存的内容
     * @param context
     * @param ppname
     * @return
     */
    public static String readRemember(Context context, String ppname) {
        Properties pp = new Properties();
        InputStream is;
        String value = "";
        try {
            is = context.openFileInput(REM_FILENAME);
            pp.load(is);
            value = pp.getProperty(ppname);
        } catch (IOException e) {
        }

        return value;
    }


    /***
     * 保存内容到文件
     * @param context
     * @param ppname
     * @param ppvalue
     */
    public static void writeRemember(Context context, String ppname, String ppvalue) {
        Properties pp = new Properties();
        InputStream is;
        try {
            is = context.openFileInput(REM_FILENAME);
            pp.load(is);
        } catch (IOException e) {
        }
        pp.setProperty(ppname, ppvalue);
        FileOutputStream out;
        try {
            out = context.openFileOutput(REM_FILENAME, context.MODE_PRIVATE);
            pp.store(out, "");
        } catch (Exception e) {
            Log.d("记录remember信息出错", e.toString());
        }
    }


    /***
     * 从文件恢复到内存
     * @param context
     */
    public static void reLoadLogin(Context context) {
        Properties pp = new Properties();
        InputStream is;
        try {
            is = context.openFileInput(REM_FILENAME);
            pp.load(is);
        } catch (IOException e) {
        }

        DcAndroidContext dcAndroidContext = ((DcAndroidContext) context.getApplicationContext());
        //用户名
        Object userName = pp.get(REM_ACCOUNUT);
        if (userName != null) {
            dcAndroidContext.setParam(REM_ACCOUNUT, userName.toString());
        }
        //密码
        Object passWord = pp.get(REM_PASSWORD);
        if (passWord != null) {
            dcAndroidContext.setParam(REM_PASSWORD, passWord.toString());
        }

        //用户ID
        Object utid = pp.get(REM_UID);
        if (utid != null) {
            dcAndroidContext.setParam(REM_UID, utid.toString());
        }

        // 性别
        Object accesstoken = pp.get(REM_SEX);
        if (accesstoken != null) {
            dcAndroidContext.setParam(REM_SEX, accesstoken.toString());
        }

        //登陆时间
        Object logintime = pp.get(REM_LOGINTIME);
        if (logintime != null) {
            dcAndroidContext.setParam(REM_LOGINTIME, DcDateUtils.toDate(logintime.toString(), DcDateUtils.FORMAT_DATE_TIME_1));
        }

        //用户头像
        Object headImg = pp.get(REM_HEAD_IMG);
        if (headImg != null) {
            dcAndroidContext.setParam(REM_PASSWORD, headImg.toString());
        }

        // 手机号
        Object usermobile = pp.get(REM_USERMOBILE);
        if (usermobile != null) {
            dcAndroidContext.setParam(REM_USERMOBILE, usermobile.toString());
        }

        // 邀请码
        Object usercode = pp.get(REM_YQ_CODE);
        if (usercode != null) {
            dcAndroidContext.setParam(REM_YQ_CODE, usercode.toString());
        }

        // token
        Object token = pp.get(REM_TOKEN);
        if (token != null) {
            dcAndroidContext.setParam(REM_TOKEN, token.toString());
        }

        // token
        Object blv = pp.get(REM_BLV);
        if (token != null) {
            dcAndroidContext.setParam(REM_BLV, blv.toString());
        }


        Log.e("zhxy.ZhxyProperties", "已重新加载内存数据");
    }


    /***
     *  从内存读取登录信息保存到文件
     * @param context
     */
    public static void writeLoginRemember(Context context) {
        Properties pp = new Properties();
        InputStream is;
        try {
            is = context.openFileInput(REM_FILENAME);
            pp.load(is);
        } catch (IOException e) {
        }

        DcAndroidContext dcAndroidContext = ((DcAndroidContext) context.getApplicationContext());
        //用户名
        Object userName = dcAndroidContext.getParam(Consts.USER_ACCOUNT);
        if (userName != null) {
            pp.setProperty(REM_ACCOUNUT, userName.toString());
        }
        //密码
        Object passWord = dcAndroidContext.getParam(Consts.USER_PASSWORD);
        if (passWord != null) {
            pp.setProperty(REM_PASSWORD, passWord.toString());
        }
        //身份ID
        Object utid = dcAndroidContext.getParam(Consts.USER_UID);
        if (utid != null) {
            pp.setProperty(REM_UID, utid.toString());
        }

        //登陆时间
        Object logintime = dcAndroidContext.getParam(Consts.COOKIE_ADD_TIME);
        if (logintime != null) {
            pp.setProperty(REM_LOGINTIME, DcDateUtils.toString((Date) logintime, DcDateUtils.FORMAT_DATE_TIME_1).toString());
        }

        //用户头像
        Object headImg = dcAndroidContext.getParam(Consts.USER_HEADIMG);
        if (headImg != null) {
            pp.setProperty(REM_HEAD_IMG, headImg.toString());
        }

        Object usermobile = dcAndroidContext.getParam(Consts.USER_MOBILE);
        if (usermobile != null) {
            pp.setProperty(REM_USERMOBILE, usermobile.toString());
        }

        FileOutputStream out;
        try {
            out = context.openFileOutput(REM_FILENAME, context.MODE_PRIVATE);
            pp.store(out, "");
        } catch (Exception e) {
            Log.d("记录remember信息出错", e.toString());
        }
    }

    public static void clearRem(Context mContext) {
        DcAndroidContext app = (DcAndroidContext) mContext.getApplicationContext();

        ContextProperties.writeRemember(mContext, ContextProperties.REM_UID, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_ACCOUNUT, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_PASSWORD, "");

        ContextProperties.writeRemember(mContext, ContextProperties.REM_USERMOBILE, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_NICKNAME, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_SEX, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_HEAD_IMG, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_YQ_CODE, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_USER_LEVEL, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_USER_LESSNUM, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_USER_PLAYED_NUM, "");
        ContextProperties.writeRemember(mContext, ContextProperties.REM_USER_MAX_PLAYNUM, "");

        ContextProperties.writeRemember(mContext, ContextProperties.REM_IS_LOGINED, ContextProperties.IS_LOGIN_TRUE_NO);

        if (app != null) {
            app.removeParam(Consts.USER_MOBILE);
            app.removeParam(Consts.USER_NICK_NAME);
            app.removeParam(Consts.USER_SEX);
            app.removeParam(Consts.USER_HEADIMG);
            app.removeParam(Consts.USER_YQCODE);

            app.removeParam(Consts.USER_LEVEL);
            app.removeParam(Consts.USER_LESSNUM);
            app.removeParam(Consts.USER_PLAYNUM);
            app.removeParam(Consts.USER_MAX_PLAYNUM);
            app.removeParam(Consts.USER_APP_URL);
            app.removeParam(Consts.USER_LINK_URL);
            app.removeParam(Consts.MY_HISTORY_COUNT);
            app.removeParam(Consts.MY_LIKES_COUNT);
        }
    }

    public static void remeberCache(Context mContext, String account, String pwd, String uid) {
        ContextProperties.writeRemember(mContext, ContextProperties.REM_ACCOUNUT, account);
        ContextProperties.writeRemember(mContext, ContextProperties.REM_PASSWORD, pwd);
        ContextProperties.writeRemember(mContext, ContextProperties.REM_UID, uid);
        ContextProperties.writeRemember(mContext, ContextProperties.REM_IS_LOGINED, ContextProperties.IS_LOGIN_TRUE_YES);

        DcAndroidContext app = (DcAndroidContext) mContext.getApplicationContext();
        if (app != null) {
            app.setParam(ContextProperties.REM_UID, uid);
            app.setParam(ContextProperties.REM_ACCOUNUT, account);
            app.setParam(ContextProperties.REM_PASSWORD, pwd);
        }
    }

    public static void writeProperties(Context mContext, LoginBean.DataBean.UserinfoBean userData) {
        DcAndroidContext app = (DcAndroidContext) mContext.getApplicationContext();

        ContextProperties.writeRemember(mContext, ContextProperties.REM_USERMOBILE, String.valueOf(userData.getMobile()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_YQ_CODE, String.valueOf(userData.getInvite()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_YQ_PERSON, String.valueOf(userData.getRefer()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_TIMES, String.valueOf(userData.getTimes()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_DAYTIMES, String.valueOf(userData.getDaytimes()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_TIMESSUPPER, String.valueOf(userData.getTimesupper()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_CACHE, String.valueOf(userData.getCache()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_CACHESUPPER,String.valueOf(userData.getCacheupper()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_RECOMMEND,String.valueOf(userData.getRecommend()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_NICKNAME, String.valueOf(userData.getNickname()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_NAME,String.valueOf(userData.getUsername()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_HEAD_IMG, String.valueOf(userData.getAvatar()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_MONEY, String.valueOf(userData.getMoney()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_USER_LEVEL, String.valueOf(userData.getLevel()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_SEX, String.valueOf(userData.getSex()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_TOKEN, String.valueOf(userData.getToken()));
        ContextProperties.writeRemember(mContext, ContextProperties.REM_BLV, String.valueOf(userData.getBlv()));


        if (app == null) {
            app = (DcAndroidContext) mContext.getApplicationContext();
        }

        if (app != null) {
            app.setParam( ContextProperties.REM_USERMOBILE, userData.getMobile());
            app.setParam( ContextProperties.REM_YQ_CODE, userData.getInvite());
            app.setParam( ContextProperties.REM_YQ_PERSON, String.valueOf(userData.getRefer()));
            app.setParam( ContextProperties.REM_TIMES, userData.getTimes());
            app.setParam( ContextProperties.REM_DAYTIMES, userData.getDaytimes());
            app.setParam( ContextProperties.REM_TIMESSUPPER, userData.getTimesupper());
            app.setParam( ContextProperties.REM_CACHE, userData.getCache());
            app.setParam( ContextProperties.REM_CACHESUPPER,userData.getCacheupper());
            app.setParam( ContextProperties.REM_RECOMMEND,String.valueOf(userData.getRecommend()));
            app.setParam( ContextProperties.REM_NICKNAME, userData.getNickname());
            app.setParam( ContextProperties.REM_NAME, userData.getUsername());
            app.setParam( ContextProperties.REM_HEAD_IMG, userData.getAvatar());
            app.setParam( ContextProperties.REM_MONEY, userData.getMoney());
            app.setParam( ContextProperties.REM_USER_LEVEL, String.valueOf(userData.getLevel()));
            app.setParam( ContextProperties.REM_SEX, userData.getSex());
            app.setParam( ContextProperties.REM_TOKEN, userData.getToken());
            app.setParam( ContextProperties.REM_BLV, String.valueOf(userData.getBlv()));
        }
    }
}
