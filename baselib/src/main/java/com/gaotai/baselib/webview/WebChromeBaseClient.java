package com.gaotai.baselib.webview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
/**
 * 自定义 WebChromeClient 
 * <p>对Web弹出框进行重写</p>
 * @author MengLiang
 */
public class WebChromeBaseClient extends WebChromeClient
{
	/**
	 * 显示加载中
	 */
	private ProgressDialog loadingBar;
	/**
	 * 上下文
	 */
	private Context context;
	
	public WebChromeBaseClient(Context context, ProgressDialog loadingBar)
	{
		this.context = context;
		this.loadingBar = loadingBar;	
	}
	
	@Override
	public boolean onJsAlert(WebView view, String url, String message,
			final android.webkit.JsResult result) {
		if(loadingBar != null)
		{
			if (loadingBar.isShowing()) {
				loadingBar.dismiss();
			}
		}
		new AlertDialog.Builder(context)
				.setTitle("提示")
				.setMessage(message)
				.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}
						}).setCancelable(false).create().show();
		return true;
	}

	/**
	 * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
	 */
	@Override
	public boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue,
			final android.webkit.JsPromptResult result) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				view.getContext());

		builder.setTitle("提示").setMessage(message);
		final EditText et = new EditText(view.getContext());
		et.setSingleLine();
		et.setText(defaultValue);
		builder.setView(et)
				.setPositiveButton("确定",
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm(et.getText().toString());
							}

						})
				.setNeutralButton("取消",
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						});

		// 禁止响应按back键的事件
		// builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
	}

	/**
	 * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
	 */
	@Override
	public boolean onJsConfirm(WebView view, String url,
			String message, final android.webkit.JsResult result) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				view.getContext());
		builder.setTitle("提示")
				.setMessage(message)
				.setPositiveButton("确定",
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}
						})
				.setNeutralButton("取消",
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				result.cancel();
			}
		});

		// 禁止响应按back键的事件
		// builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
	}
}
