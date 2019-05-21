package com.gaotai.baselib.listener;
/**
 * 录音监听
 * @author  mengliang
 */
public interface RecorderListener {
	
	/**
	 * 手指上滑
	 */
	public void onTouchMoveUp();	
	/**
	 * 手指下滑
	 */
    public void onTouchMoveDown();  
	/**
	 * 取消录制
	 */
    public void onRecorderCancel();  
    /**
     * 录音开始
     */
	public void onRecorderStart();	
	/**
	 * 录音中
	 * @param signalEMA 音量大小
	 */
	public void onRecorderRunning(double signalEMA);
	/**
	 * 录制完成
	 * @param time  录音时长 毫秒
	 * @param filename 录音文件地址
	 */
	public void onRecorderFinished(long time,String filename);
	/**
	 * 录制失败
	 */
	public void onRecorderError();
}
