package com.gaotai.baselib.view.dialog;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 显示
 * 
 * @author mengliang
 * @version 1.0
 */
public class ToastUtil {

	/**
	 * <P>
	 * 浮出信息(时间短).
	 * </P>
	 * 
	 * @param contex
	 */
	public static void toastShort(Context context, CharSequence msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * <P>
	 * 浮出信息(时间短).
	 * </P>
	 * 
	 * @param contex
	 */
	public static void toastLong(Context context, CharSequence msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

}
