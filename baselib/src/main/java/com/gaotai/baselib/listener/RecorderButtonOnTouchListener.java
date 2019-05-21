package com.gaotai.baselib.listener;

import java.io.File;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.util.RecorderUtil;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 录音按钮监听
 * 
 * @author mengliang
 */
public class RecorderButtonOnTouchListener implements OnTouchListener
{
	private RecorderListener spearkListener;

	RecorderUtil recorderUtil;

	/**
	 * 录音文件存放路径   例如： /sdcard/
	 */
	private String fileppath = DcAndroidConsts.FILE_DIR;
	
	public RecorderButtonOnTouchListener(RecorderListener spearkListener,String fileppath)
	{
		this.spearkListener = spearkListener;
		isrecord = false;
		spearkStatus = 0;
		mHandler.removeCallbacks(mPollTask);
		recorderUtil = new RecorderUtil();
		this.fileppath = fileppath;
	}

	/**
	 * 日志TAG
	 */
	private static String TAG = RecorderButtonOnTouchListener.class.getSimpleName();

	/**
	 * 是否录制中
	 */
	private boolean isrecord = false;

	/** 语音文件保存路径 */
	private String mFileName = null;

	/**
	 * 开始时间
	 */
	long beforeTime = 0;

	/**
	 * 录制结束的时间
	 */
	long afterTime;

	/**
	 * 焦点开始位置
	 */
	float erent_y = 0;

	/**
	 * y 大于 150 取消发送
	 */
	long FLING_MIN_DISTANCE = 150;

	private Handler mHandler = new Handler();

	/**
	 * 录制状态
	 */
	int spearkStatus = 0;

	/**
	 * 录制中
	 */
	private static int SPEAK_ING = 1;

	/**
	 * 录制待取消
	 */
	private static int SPEAK_CANCEL = 2;

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		// System.out.println("X:" + event.getX() + "Y" + event.getY());
		// 录制中
		if(isrecord)
		{
			// 本次 位置 移动 getY 向上为 负值 -
			if(event.getY() < -150)
			{
				spearkStatus = 2;
				spearkListener.onTouchMoveUp();
			}
			else
			{
				spearkStatus = 1;
				spearkListener.onTouchMoveDown();
			}
		}
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				if(!isrecord)
				{
					erent_y = event.getY();
					// 500毫秒 之间重复点击 不再次执行
					if(beforeTime == 0 || System.currentTimeMillis() - beforeTime > 500)
					{
						isrecord = true;
						beforeTime = System.currentTimeMillis();
						v.setPressed(true);
						spearkListener.onRecorderStart();
						// 设置录音保存路径
						mFileName = fileppath + String.valueOf(System.currentTimeMillis()) + ".amr";
						if(recorderUtil.startVoice(mFileName))
						{
							mHandler.postDelayed(mPollTask, POLL_INTERVAL);
						}
						else
						{
							// 录制出错
							isrecord = false;
							spearkListener.onRecorderError();
						}
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				if(!isrecord)
				{
					return false;
				}
				isrecord = false;
				afterTime = System.currentTimeMillis();
				v.setPressed(false);
				mHandler.removeCallbacks(mPollTask);
				if(recorderUtil.stopVoice())
				{
					// 是否取消发送
					if(spearkStatus == SPEAK_CANCEL)
					{
						//取消录制
						spearkListener.onRecorderCancel();
						try
						{
							File file = new File(mFileName);
							file.delete();
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}
					else
					{
						if("".equals(mFileName))
						{
							spearkListener.onRecorderError();
						}
						else
						{
							spearkListener.onRecorderFinished(afterTime - beforeTime, mFileName);
						}
					}
				}
				else
				{
					// 录制出错
					isrecord = false;
					if(spearkStatus != SPEAK_CANCEL)
					{
						spearkListener.onRecorderError();
					}
				}
				break;
			default:
				break;
		}
		return false;

	}

	/**
	 * 监控音量间隔
	 */
	private static final int POLL_INTERVAL = 300;

	/**
	 * 监听音量
	 */
	private Runnable mPollTask = new Runnable(){
		public void run()
		{
			try
			{
				double amp = recorderUtil.getMaxAmplitude() / 2700.0;
				spearkListener.onRecorderRunning(amp);
				mHandler.postDelayed(mPollTask, POLL_INTERVAL);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	};

}
