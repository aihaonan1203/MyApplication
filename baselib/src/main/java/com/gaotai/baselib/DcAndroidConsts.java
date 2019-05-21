package com.gaotai.baselib;

import java.io.File;

import android.os.Environment;

/**
 * <p>
 * 框架基础常量定义
 * </p>
 * @author MengLiang
 */
public interface DcAndroidConsts {
	/**
	 * 开始下载文件
	 */
	public static final int down_file = 0x0107;
	/**
	 * 取消下载文件
	 */
	public static final int down_cancle = 0x0108;
	/**
	 * 下载文件异常
	 */
	public static final int down_error = 0x0109;
	/**
	 * 重新下载
	 */
	public static final int down_reboot = 0x0110;

	/**
	 * 基础路径
	 */
	public static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar;
	/**
	 * 文件地址
	 */
	public String FILE_DIR = Environment.getExternalStorageDirectory().getPath() + "/zhxybase/";
	/**
	 * 日志目录
	 */
	public String LOG_PATH = FILE_DIR + "log/";
	/**
	 * 上下文中存储的缓存路径
	 */
	public static final String CLIENET_FILEPATH_PROPERTY =  FILE_DIR + "temp/";

	/**
	 * APK 存放地址  FilePathHelper.getAblePath(BaseUpdateActivity.this) + APK_PATH   File.separatorChar
	 */
	public static final String APK_PATH = FILE_DIR + "apk/";

	/**
	 * 下载数据库名称
	 */
	public static final String DB_TASK_NAME = "zhxytask.db";
	
	/**
	 * 即时聊天 聊天室标识
	 */
	public final String XMPP_SERVICE_CONFERENCE = "@conference.";
	/**
	 * 即时聊天 resource 
	 */
	public final String XMPP_SERVICE_RESOURCE  = "android";
	/**
	 * 聊天消息类别 好友发送的
	 */
	public static final int CHAT_MESSAGET_PACKET_TYPE_DEF = 1;
	/**
	 * 聊天消息类别 通知公告
	 */
	public static final int CHAT_MESSAGET_PACKET_HEADLINE = 2;
	/**
	 * 聊天消息类别 群聊消息
	 */
	public static final int CHAT_MESSAGET_PACKET_GROUP = 3;
	/**
	 * 聊天消息类别  删除或退出 群组员通知
	 */
	public static final int CHAT_MESSAGET_PACKET_KICKGROUPUSER = 4;
	/**
	 * 聊天消息类别 群组解散通知
	 */
	public static final int CHAT_MESSAGET_PACKET_DISSOLUTIONGROUP= 5;
	/**
	 * 聊天消息类别 多身份登录通知
	 */
	public static final int CHAT_MESSAGET_MAC_RECONNECT = 98;
	
	/**
	 * 聊天消息类别 重连服务通知
	 */
	public static final int CHAT_MESSAGET_PACKET_RECONNECT = 99;
	
	/**
	 * iq 消息接收  群组创建
	 */
	public static final int CHAT_IQPROVIDER_TYPE_GROUPCREATE = 1;
	/**
	 * iq 消息接收  获取群组列表
	 */
	public static final int CHAT_IQPROVIDER_TYPE_GROUPLIST = 2;
	/**
	 * iq 消息接收  获取群组的成员
	 */
	public static final int CHAT_IQPROVIDER_TYPE_GROUPMEMBER = 3;

	/**
	 * 系统提示
	 */
	public static final String MSG_TYPE_SYSTEM = "-1";

	/**
	 * 文本消息
	 */
	public static final String MSG_TYPE_TEXT = "1";
	/**
	 *  图片
	 */
	public static final String MSG_TYPE_IMG = "2";
	/**
	 * 语音
	 */
	public static final String MSG_TYPE_VOICE = "3";
	/**
	 * 位置
	 */
	public static final String MSG_TYPE_LOCATION = "4";
	/**
	 * 视频
	 */
	public static final String MSG_TYPE_VIDEO = "5";
	/**
	 * 链接
	 */
	public static final String MSG_TYPE_WEBLINK = "6";	
	/**
	 * 富文本
	 */
	public static final String MSG_TYPE_NEWS = "7";
	/**
	 * 图片 发送网络图片路径
	 */
	public static final String MSG_TYPE_IMG_URL = "8";
	/**
	 *  即时聊天 内容分隔符
	 */
	public static final String SPLIT = "卍";
	/**
	 * 即时聊天  发送时间标识
	 */
	public static final String MSG_KEY_TIME = "zhxymessage.time";
	/**
	 * 群组标识
	 */
	public static final String MSG_KEY_GROUP_TYPE = "1";

	/**
	 * 即时聊天 回执节点
	 */
	public static final String MSG_KEY_ELEMENT_NAME = "recvok";
	/**
	 * 即时聊天 回执节点命名空间
	 */
	public static final String MSG_KEY_NAME_SPACE = "com.gaotai.cn.recvflag";
	/**
	 * 即时聊天 接收自己的消息标识 命名空间
	 */
	public static final String MSG_KEY_CARBONS_SPACE = "urn:xmpp:carbons:2";
	/**
	 * 即时聊天 消息类别节点
	 */
	public static final String MSG_TYPE_KEY_ELEMENT_NAME = "msgtype_zhxy";
	/**
	 * 即时聊天 消息类别命名空间
	 */
	public static final String MSG_TYPE_KEY_NAME_SPACE = "com.gaotai.cn.msgtype";
	/**
	 * 即时聊天 消息类别子节点
	 */
	public static final String MSG_TYPE_KEY_ELEMENT_CHILD_NAME = "msgtype";
		
	/**
	 * 消息的链接地址
	 */
	public static final String MSG_URL_KEY_ELEMENT_NAME = "msgurl_zhxy";
	/**
	 * 消息的链接地址命名空间
	 */
	public static final String MSG_URL_KEY_NAME_SPACE = "com.gaotai.cn.msgurl";
	/**
	 * 消息的链接地址子节点
	 */
	public static final String MSG_URL_KEY_ELEMENT_CHILD_NAME = "msgurl";
	
	/**
	 * 富文本消息节点名称
	 */
	public static final String MSG_NEWS_KEY_ELEMENT_NAME = "msgnews_zhxy";
	/**
	 * 富文本消息的命名空间
	 */
	public static final String MSG_NEWS_KEY_NAME_SPACE = "com.gaotai.cn.msgnews";
	/**
	 * 富文本消息的子节点
	 */
	public static final String MSG_NEWS_KEY_ELEMENT_CHILD_NAME = "details";
	/**
	 * 消息参数的节点名称
	 */
	public static final String MSG_ATT_KEY_ELEMENT_NAME = "msgattrparams_zhxy";
	/**
	 * 消息参数命名空间
	 */
	public static final String MSG_ATT_KEY_NAME_SPACE = "com.gaotai.cn.msgattrparams";
	/**
	 * 消息参数子节点
	 */
	public static final String MSG_ATT_KEY_ELEMENT_CHILD_NAME = "attrparams";
	
	
	/**
	 * 即时聊天 消息群组 命名空间
	 */	
	public static final String MSG_ROOMS_NAME_SPACE = "com.gaotai.cn.rooms.all";
	/**
	 * 即时聊天 消息 群组成员 命名空间
	 */
	public static final String MSG_MUCMEMBERS_NAME_SPACE = "com.gaotai.cn.muc.members";

	/**
	 * 群组创建 命名空间
	 */
	public static final String MSG_ROOMCREATE_NAME_SPACE = "com.gaotai.cn.rooms.create";

	/**
	 * 即时聊天 消息中心标识
	 */
	public static final String MSGCENTER_KEY_ELEMENT_NAME = "msgcenter";

	/**
	 * 即时聊天 消息中心 命名空间
	 */
	public static final String MSGCENTER_KEY_NAME_SPACE = "com.gaotai.cn.msgcenter";

	public static final String PIC_SIGN = "<picture_zhxy>";
	public static final String PIC_SIGN_FOOT = "</picture_zhxy>";
	public static final String VOICE_SIGN = "<voice_zhxy>";
	public static final String VOICE_SIGN_FOOT = "</voice_zhxy>";
	public static final String VOICE_TIME = "<voice_time_zhxy>";
	public static final String VOICE_TIME_FOOT = "</voice_time_zhxy>";
	public static final String LOCATION_SIGN = "<location_zhxy>";
	public static final String LOCATION_SIGN_FOOT = "</location_zhxy>";
	public static final String LOCATIONADDRESS_SIGN = "<locationaddress_zhxy>";
	public static final String LOCATIONADDRESS_SIGN_FOOT = "</locationaddress_zhxy>";
	public static final String VIDEO_SIGN = "<video_zhxy>";
	public static final String VIDEO_SIGN_FOOT = "</video_zhxy>";
	public static final String VOICE_TIME_SPLIT = "卍";

	public static final String WEBLINK_URL = "<weblink_url_zhxy>";
	public static final String WEBLINK_URL_FOOT = "</weblink_url_zhxy>";
	public static final String WEBLINK_TITLE = "<weblink_title_zhxy>";
	public static final String WEBLINK_TITLE_FOOT = "</weblink_title_zhxy>";
	public static final String WEBLINK_PIC = "<weblink_pic_zhxy>";
	public static final String WEBLINK_PIC_FOOT = "</weblink_pic_zhxy>";
	/**
	 * 描述冲连接，
	 */
	public static final boolean RECONNECT_STATE_SUCCESS = true;
	public static final boolean RECONNECT_STATE_FAIL = false;
	/**
	 * 重连接状态acttion
	 * 
	 */
	public static final String ACTION_RECONNECT_STATE = "action_reconnect_state";
	/**
	 * 描述冲连接状态的关机子，寄放的intent的关键字
	 */
	public static final String RECONNECT_STATE = "reconnect_state";
	/**
	 * 是否在线的SharedPreferences名称
	 */
	public static final String PREFENCE_USER_STATE = "prefence_user_state";
	public static final String IS_ONLINE = "is_online";

	/**
	 * 服务器的配置
	 */
	public static final String LOGIN_SET = "zhxy_login_set";// 登录设置

	/*
	* 上一次操作时间
	*/
	public static final String OPERATETIME = "operate_time";

	/** 下载的APK文件类型标识 */
	public static final String DOWN_FILE_TYPE_APK = "apk";
	/** 下载的差分包文件类型标识 */
	public static final String DOWN_FILE_TYPE_PATCH = "patch";
}
