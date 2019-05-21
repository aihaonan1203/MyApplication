package com.gaotai.baselib.webview;
/**
 * URL操作接口
 * <p>对自定义的URL操作 进行重写实现 </p>
 * @author MengLiang
 */
public interface UrlOperation
{
	/**
	 * 解析URL字符串 实现各种操作
	 */
	void setWithJs(String url);
	/**
	 * 加载未处理的地址
	 */
	 void loadUrl(String url);
}
