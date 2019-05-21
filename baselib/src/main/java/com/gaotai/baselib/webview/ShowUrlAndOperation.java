package com.gaotai.baselib.webview;

import android.webkit.WebView;

/**
 * Created by wangchuan on 2017/11/9.
 * 网页加载完成后获取到url地址，即可根据url对UI进行展示
 */
public interface ShowUrlAndOperation {
    /**
     * 解析URL字符串 实现各种操作
     */
    void setWithJs(WebView view,String url);
}
