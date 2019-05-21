package com.zcf.bananavideohannan.base;

import android.os.Environment;

public interface Consts {

    //    String DOMAIN_URL = "http://172.16.1.86:8082/com.zcf.laundry/";
    String DOMAIN_URL = "http://203.78.142.214";// 服务器地址
//    String DOMAIN_URL = "http://192.74.241.112/";// 服务器地址

    // 获取首页类型数据
    String ACTION_URL_GETALL_TYPE = DOMAIN_URL + "/api/index/category";
    //
    //    // 获取类型下所有视频数据
    String ACTION_URL_GET_VIDEO_BY_TYPE = DOMAIN_URL + "/api/videolist/category_news_video";

    // 获取首页-最新片源
    String ACTION_URL_GET_DEF_ZUIXIN = DOMAIN_URL + "/api/videolist/news_video";

    // 获取首页-重磅热播
    String ACTION_URL_GET_DEF_REBO = DOMAIN_URL + "/api/videolist/host_video";

    // 获取首页-猜你喜欢
    String ACTION_URL_GET_DEF_LIKES = DOMAIN_URL + "/api/videolist/rand_video";

    // 获取首页轮播图
    String ACTION_URL_GET_DEF_CUST = DOMAIN_URL + "/api/index/adlink";

    // 获取首页-自定义模板数据
    String ACTION_URL_GET_DEF_CUST2 = DOMAIN_URL + "/api/videolist/special";

    // 获取首页广告
    String ACTION_URL_GET_DEF_GUANGGAO = DOMAIN_URL + "/api/videolist/channel";

    // 热门搜索
    String ACTION_URL_SEARCH_HOT = DOMAIN_URL + "/api/videolist/hotlabel";

    // 公告
    String ACTION_URL_GET_GONGGAO = DOMAIN_URL + "/api/index/notice";

    // 发现-
    String ACTION_URL_GET_FIND_DATA = DOMAIN_URL + "/index.php/index/index/discover";

    // 频道-标签筛选
    String ACTION_URL_GET_BQSX_DATA = DOMAIN_URL + "/index.php/index/index/relabel";

    // 频道-通过标签筛选获取视频
    String ACTION_URL_GET_BY_BQSX = DOMAIN_URL + "/index.php/index/index/getVideoByLabels";

    // 频道-专栏推荐-推荐
    String ACTION_URL_GET_ZLTJ_TJ = DOMAIN_URL + "/index.php/index/index/recommend";

    // 频道-专栏推荐-热门专题
    String ACTION_URL_GET_ZLTJ_RMZT = DOMAIN_URL + "/index.php/index/index/special";

    // 频道-专栏推荐-女优列表
    String ACTION_URL_GET_ZLTJ_NYLB = DOMAIN_URL + "/index.php/index/index/actor";

    // 频道-专栏推荐-女优筛选
    String ACTION_URL_GET_ZLTJ_NYSX = DOMAIN_URL + "/index.php/index/index/actorscreen";

    // 频道-专栏推荐-女优详情
    String ACTION_URL_GET_NY_DETAIL = DOMAIN_URL + "/index.php/index/index/actorinfo";

    // 频道-专栏推荐-专题详情
    String ACTION_URL_GET_ZT_DETAIL = DOMAIN_URL + "/index.php/index/index/specialinfo";

    // 视频详情
    String ACTION_URL_GET_VIDEO_DETAIL = DOMAIN_URL + "/index.php/index/index/videoinfo";

    // 关键字模糊查询
    String ACTION_URL_SEARCH = DOMAIN_URL + "/index.php/index/index/dimselect";

    // 观看记录
    String ACTION_URL_GET_LOOK_HISTORY = DOMAIN_URL + "/index.php/index/user/userwatch";

    // 删除观看记录
    String ACTION_URL_DEL_LOOK_HISTORY = DOMAIN_URL + "/index.php/index/user/delwatch";


    // 我的喜欢
    String ACTION_URL_GET_VIDEOS_LIKE = DOMAIN_URL + "/api/user/moneylog";

    // 删除喜欢
//    String ACTION_URL_DEL_VIDEOS_LIKE = DOMAIN_URL + "/index.php/index/user/deluserlike";
    String ACTION_URL_DEL_VIDEOS_LIKE = DOMAIN_URL + "/index.php/index/user/dellike";

    // 通知消息列表
    String ACTION_URL_GET_NOTICE_LIST = DOMAIN_URL + "/index.php/index/user/notice";

    // 反馈列表
    String ACTION_URL_GET_FEEDBACK_LIST = DOMAIN_URL + "/api/user/myfeedback";

    // 发布反馈
    String ACTION_URL_PUBLISH_FEEDBACK = DOMAIN_URL + "/api/user/feedback";

    // 视频-添加到‘我的喜欢’
    String ACTION_URL_ADD_LIKE = DOMAIN_URL + "/index.php/index/user/userlikepost";

    // 视频-点赞
    String ACTION_URL_ADD_ZAN = DOMAIN_URL + "/index.php/index/user/good_bad";

    // 评论
    String ACTION_URL_PUBLISH_COMMENT = DOMAIN_URL + "/index.php/index/user/usercomment";
    // 评论
    String ACTION_URL_PUBLISH_COMMENT2 = DOMAIN_URL + "/api/user/comment";

    // 手机登录
    String ACTION_URL_LOGIN = DOMAIN_URL + "/api/user/login";

    // 用户名登录
    String ACTION_URL_ACCOUNT_LOGIN = DOMAIN_URL + "/index.php/index/user/namepost";

    // 注册
    String ACTION_URL_REGISTER = DOMAIN_URL + "/api/user/register";

    // 短信验证码
    String ACTION_URL_SMSCODE = DOMAIN_URL + "/api/user/smscode";

    // 修改用户信息
    String ACTION_URL_UPDATE_USERINFO = DOMAIN_URL + "/api/user/update_info";

    // 获取用户信息
    String ACTION_URL_GET_USERINFO = DOMAIN_URL + "/index.php/index/user/userinfo";

    // 修改密码
    String ACTION_URL_GET_CHANGE_PWD = DOMAIN_URL + "/index.php/index/user/changepassword";

    // 用户等级规则
    String ACTION_URL_GET_USERLEVEL = DOMAIN_URL + "/index.php/index/user/userlevel";

    // 上传头像
    String ACTION_URL_UPLOAD_HEADIMG = DOMAIN_URL + "/index.php/index/user/uploadimage";

    // 获取充值二维码图片 微信号
    String ACTION_URL_GET_XWINFO = DOMAIN_URL + "/index.php/index/user/getpayinfo";

    // 获取广告业背景图
    String ACTION_URL_GET_ADS = DOMAIN_URL + "/index.php/index/index/adlink";


    // 保存二维码增加观看次数
    String SAVE_ERWEICODE_Number = DOMAIN_URL + "/index.php/index/user/adlinkinc";

    // VIP兑换
    String VIP__GET = DOMAIN_URL + "/index.php/index/user/uservip";

    String VIDEO_CACHE_DIR = Environment.getExternalStorageDirectory().getPath() + "/xjsp/.video/";

    String FLAG_ISFIRST_LOGIN = "flag_is_first_login";

    //支付宝标识
    String PAY_ALI_PLUGIN_ID = "alipayDirectPlugin";

    // 微信标识
    String PAY_WEIXIN_PLUGIN_ID = "weixinpayPlugin";


    String USER_NAME = "username";
    String USER_OPENID = "useropenid";

    int RESULT_CODE_SUCCESS = 200;

    String LOG_TAG = "熊猫洗衣";

    String USER_UID = "identy_id";
    String USER_ACCOUNT = "usernamekey";
    String USER_NICK_NAME = "nick_name";
    String USER_PASSWORD = "passwordkey";
    String USER_SEX = "usersex";
    String COOKIE_ADD_TIME = "cookie_add_time";
    String USER_HEADIMG = "userheadimg";
    String USER_YQCODE = "useryqcode";
    String USER_MOBILE = "usermobile";
    String USER_LEVEL = "userlevel";
    String USER_LESSNUM = "userlessnum";
    String USER_PLAYNUM = "userplaynum";
    String USER_MAX_PLAYNUM = "usermaxplaynum";
    String USER_APP_URL = "userappurl";
    String USER_LINK_URL = "userlinkurl";

    String USER_VX_EWM = "uservxewm";
    String USER_VX_ACCOUNT = "uservxaccount";
    String USER_VX_CONTENT = "uservxcontent";

    String USER_ISREFRESH_PERSONINFO = "isfresh";// 是否刷新个人信息数据
    String USER_FRESH_YES = "yes";// 是否刷新个人信息数据
    String USER_FRESH_NO = "no";// 是否刷新个人信息数据

    String MY_HISTORY_COUNT = "history_count";
    String MY_LIKES_COUNT = "likes_count";

    String TXT_LOOK_LESS_NUM = VIDEO_CACHE_DIR + "looknum.txt";
}
