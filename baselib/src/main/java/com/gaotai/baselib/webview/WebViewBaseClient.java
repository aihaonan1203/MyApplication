package com.gaotai.baselib.webview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * 自定义 WebViewClient 
 * <p>重写 shouldOverrideUrlLoading</p>
 * @author MengLiang
 */
public class WebViewBaseClient extends WebViewClient
{
	/**
	 * 显示加载中
	 */
	private ProgressDialog loadingBar;
	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * URL 补充参数  用于拦截URL 增加URL参数
	 */
	private String urlparm = "";
	/**
	 * WEB JS 交互 相关操作
	 */
	UrlOperation urlOperation;

	/**
	 * 根据url对UI进行操作
	 */
	ShowUrlAndOperation showUrlAndOperation;

	/**
	 * 重定向的外链url白名单
	 */
	private String[] whiteListUrls;

	/**
	 * 是否允许根据白名单控制外链url
	 */
	private  boolean whiteListEnable;
	/**
	 * 访问黑名单  如存在于黑名单内 则不访问网址
	 */
	private String[] blackListUrls = null;

	/**
	 * 设置 访问黑名单
	 */
	public void setBlackListUrls( String[] blackListUrls)
	{
		this.blackListUrls = blackListUrls;
	}

	public WebViewBaseClient(Context context, ProgressDialog loadingBar)
	{
		this.context = context;
		this.loadingBar = loadingBar;
		this.whiteListEnable = false; //关闭根据白名单控制外链url功能
	}

	public WebViewBaseClient(Context context, ProgressDialog loadingBar,String[] whiteListUrls)
	{
		this.context = context;
		this.loadingBar = loadingBar;
		this.whiteListEnable = true; //开启根据白名单控制外链url功能
		this.whiteListUrls = whiteListUrls;
	}

	/**
	 * 设置 URL 补充参数  用于拦截URL 增加URL参数
	 */
	public void SetUrlParm(String urlparm)
	{
		this.urlparm = urlparm;
	}

	/**
	 * 设置URL 接收接口
	 * @param urlOperation
     */
	public void SetUrlOperation(UrlOperation urlOperation)
	{
		this.urlOperation = urlOperation;
	}

	public void setShowUrlAndOperation(ShowUrlAndOperation showUrlAndOperation) {
		this.showUrlAndOperation = showUrlAndOperation;
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		try {
			if (loadingBar != null) {
				if (loadingBar.isShowing()) {
					loadingBar.dismiss();
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		if(showUrlAndOperation!=null){
			showUrlAndOperation.setWithJs(view,url);
		}
		super.onPageFinished(view, url);
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if(blackListUrls != null) {
			for (String blackUrl : blackListUrls) {
				if (url.toLowerCase().contains(blackUrl.toLowerCase().trim())) {
					//匹配其中一个黑名单条目，则终止访问
					return true;
				}
			}
		}
		if(url.indexOf(".3gp") != -1)
		{
			//3gp格式 打开视频播放页
			Intent localIntent = new Intent("android.intent.action.VIEW");
			Uri localUri = Uri.parse(url);
			localIntent.setDataAndType(localUri, "video/3gp");
			context.startActivity(localIntent);
			return true;
		}
		else if(url.indexOf(".mp4") != -1)
		{
			//mp4 格式 打开视频播放页
			Intent localIntent = new Intent("android.intent.action.VIEW");
			Uri localUri = Uri.parse(url);
			localIntent.setDataAndType(localUri, "video/mp4");
			context.startActivity(localIntent);
			return true;
		}
		else if(url.toLowerCase().indexOf("tel:") != -1)
		{
			//tel: 开头 调转到拨打电话页
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_CALL, uri);
			context.startActivity(intent);
			return true;
		}
		else if (url.toLowerCase().indexOf("download:") == 0) {
			// int length = url.toLowerCase().length();
			// 下载文件
			Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url
					.substring(9)));
			it.setClassName("com.android.browser",
					"com.android.browser.BrowserActivity");
			context.startActivity(it);
			return true;
		} else if (url.toLowerCase().indexOf("http://") == 0||url.toLowerCase().indexOf("https://") == 0) {
			if(!TextUtils.isEmpty(urlparm))
			{
				url = addUrlParm(url);
//				if (url.indexOf("?") > 0) {
//					//增加参数
//					url += "&" + urlparm;
//				}
//				else
//				{
//					url += "?" + urlparm;
//				}
			}

			//是否需要按照白名单进行url拦截
			if (this.whiteListEnable) {
				boolean  needInterceptFlag = true ; //是否需要url拦截标记
				for (String whiteUrl:whiteListUrls) {
					if (url.toLowerCase().contains(whiteUrl.toLowerCase().trim())) {
						needInterceptFlag = false;//命中其中一个白名单条目，则放行访问
						break;
					}
				}
				if (needInterceptFlag) {
					return true;
				}
				else {
					view.loadUrl(url);
					return true;
				}
			}
			else {
				view.loadUrl(url);
				return true;
			}
		}
		else if(url.toLowerCase().indexOf("js://") == 0)
		{
			//处理自定义JS
			if(urlOperation != null) {
				//处理自定义JS
				urlOperation.setWithJs(url.substring(5, url.length()));
			}
			return true;
		}
		else
		{
			//未处理的地址
			if(urlOperation != null) {
				//处理自定义JS
				urlOperation.loadUrl(url);
			}
			return true;
		}
	}

	/**
	 * 对地址进行添加参数信息
	 * @param url
	 * @return
     */
	private String addUrlParm(String url)
	{
		String strNewUrl = url;
		try {
			if (url.indexOf("?") > 0) {
				//验证参数是否有重复参数 没有新增参数
				String[] strPram = url.substring(url.indexOf("?") + 1, url.length()).split("&");
				String[] strUrlPram = urlparm.split("&");//新增的参数信息
				for (int j = 0; j < strUrlPram.length; j++) {
					String[] strParmInfo = strUrlPram[j].split("=");
					if (strParmInfo.length >= 2) {
						if (!TextUtils.isEmpty(strParmInfo[0])) {
							boolean isHaveToken = false;
							for (int i = 0; i < strPram.length; i++) {
								String[] strinfo = strPram[i].split("=");
								if (strinfo.length >= 2) {
									if (strinfo[0].toLowerCase().equals(strParmInfo[0].toLowerCase())) {
										isHaveToken = true;
									}
								}
							}
							if (!isHaveToken) {
								strNewUrl = strNewUrl + "&" + strParmInfo[0] + "=" + strParmInfo[1];
							}
						}
					}
				}
			} else {
				strNewUrl = url + "?" + urlparm;
			}
		}catch (Exception ex)
		{
			strNewUrl = url + "?" + urlparm;
		}
		return strNewUrl;
	}


	/***
	 * 断网前访问的页面地址
	 */
	private String strWebLastUrl = "";
	/**
	 * 获取  断网前访问的页面地址
	 */
	public String getWebLastUrl(){
		return strWebLastUrl;
	}
	/**
	 * 设置  断网前访问的页面地址
	 */
	public void setWebLastUrl(String strWebLastUrl)
	{
		this.strWebLastUrl = strWebLastUrl;
	}


	/***
	 * 页面链接失败转向的地址
	 */
	private String strErrorUrl = "file:///android_asset/error1.html";
	/**
	 * 设置 页面链接失败转向的地址
	 */
	public void setErrorUrl(String strErrorUrl)
	{
		this.strErrorUrl = strErrorUrl;
	}


	@Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        //页面访问异常 或 404 处理
		strWebLastUrl = failingUrl;
		//网络找不到
		view.loadUrl(strErrorUrl);
	}
}
