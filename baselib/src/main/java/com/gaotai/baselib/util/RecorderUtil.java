package com.gaotai.baselib.util;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.util.Log;

/**
 * 录音
 * @author mengliang
 */
public class RecorderUtil
{
	/**
	 * 日志TAG
	 */
	private static String TAG = RecorderUtil.class.getSimpleName();;

	/** 用于完成录音 */
	private MediaRecorder mRecorder = null;

	public RecorderUtil()
	{
		mRecorder = null;
	}

	/**
	 * 开始录音
	 * 
	 * @param mFileName
	 *            录音的存储地址
	 * @return
	 */
	public boolean startVoice(String mFileName)
	{
		try
		{
			//System.out.println(mFileName);
			String state = android.os.Environment.getExternalStorageState();
			if(!state.equals(android.os.Environment.MEDIA_MOUNTED))
			{
				Log.i(TAG, "SD Card is not mounted,It is  " + state + ".");
			}
			File directory = new File(mFileName).getParentFile();
			if(!directory.exists() && !directory.mkdirs())
			{
				Log.i(TAG, "Path to file could not be created");
			}
			// Toast.makeText(getApplicationContext(), "开始录音", 0).show();
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
			mRecorder.setOutputFile(mFileName);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mRecorder.setAudioEncodingBitRate(16);//同ios
			mRecorder.setAudioSamplingRate(8000);//同ios
			mRecorder.setAudioChannels(1);//同ios
			try
			{
				mRecorder.prepare();
			}
			catch (IOException e)
			{
				Log.e(TAG, "prepare() failed");
			}
			mRecorder.start();
			return true;
		}
		catch (Exception ex)
		{
			try
			{
				mRecorder.release();
				mRecorder = null;
			}
			catch (Exception exx)
			{
				mRecorder = null;
				exx.printStackTrace();
			}
			Log.e(TAG, "录制错误:" + ex.getMessage());
		}
		return false;
	}

	/** 停止录音 */
	public boolean stopVoice()
	{
		try
		{
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
			return true;
		}
		catch (Exception ex)
		{
			mRecorder = null;
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取录音的当前声音大小
	 * 
	 * @return
	 */
	public double getMaxAmplitude()
	{
		try
		{
			return mRecorder.getMaxAmplitude();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}
}
