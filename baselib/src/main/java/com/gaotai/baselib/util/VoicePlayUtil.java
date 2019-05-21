package com.gaotai.baselib.util;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;


/**
 * 录音播放
 */
public class VoicePlayUtil
{
	/**
	 * 日志TAG
	 */
	private static String TAG = VoicePlayUtil.class.getSimpleName();	
	/** 用于语音播放 */
	private MediaPlayer mPlayer = null;
	/** 播放监听  */	
	PlayVoiceListener playVoiceListener;
	
	public VoicePlayUtil(PlayVoiceListener playVoiceListener)
	{
		this.playVoiceListener = playVoiceListener;
	}
	
	/** 上一次播放的ID  */
	private long lastid = -1;
	
	/**
	 * 播放录音
	 * 
	 * @param id 
	 * @param fielpath 本地文件路径
	 */
	public void playVoice(final long id, String fielpath) {
		try {
			if (mPlayer != null) {
				if (mPlayer.isPlaying()) {
					// 如果播放 停止播放
					mPlayer.stop();
					mPlayer.release();
					mPlayer = null;
					// 第一次播放 或者 播放同一个
					if (lastid == -1 || lastid == id) {
						playVoiceListener.onPlaying(id);
						lastid = -1;
						return;
					} 
					else
					{
						if (lastid > 0) {							
							playVoiceListener.onPlayStop(id);
						}
					}
				}
			}
		} catch (Exception ex) {
			mPlayer = null;
			return;
		}
		mPlayer = new MediaPlayer();
		
		try {
			if (!FileUtils.isFileExist(fielpath)) {
				playVoiceListener.onPlayError(id);
				return;
			}
			lastid = id;
			playVoiceListener.onPlaying(id);			
			// mPlayer.setVolume(leftVolume, rightVolume)
			mPlayer.setDataSource(fielpath);
			mPlayer.prepare();
			mPlayer.start();

			// 设置播放完毕监听
			mPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					lastid = -1;
					playVoiceListener.onPlayStop(id);
				}
			});
			// 播放出错
			mPlayer.setOnErrorListener(new OnErrorListener() {
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					playVoiceListener.onPlayError(id);
					lastid = -1;
					return false;
				}
			});

		} catch (IOException e) {
			playVoiceListener.onPlayError(id);
		}
	}
	
	/**
	 * 停止播放录音
	 */
	public void stopplayVoice() {
		try {
			if (mPlayer != null) {
				// 如果播放 停止播放
				mPlayer.stop();
				mPlayer.release();
				mPlayer = null;
			}
		} catch (Exception ex) {
			mPlayer = null;
			return;
		}
	}
	
	
	/**
	 * 即时消息通知Listener
	 * @author mengliang
	 */
	public interface PlayVoiceListener {
		/**
		 * 播放中
		 */
		public void onPlaying(long id);
		/**
		 * 播放结束
		 */
		public void onPlayStop(long id);
		/**
		 * 播放失败
		 */
		public void onPlayError(long id);
	}

	
	
}
