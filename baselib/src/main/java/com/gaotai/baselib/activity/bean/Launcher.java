package com.gaotai.baselib.activity.bean;

import com.gaotai.baselib.activity.ILauncherListener;

import android.app.Activity;

public class Launcher implements ILauncher
{
	ILauncherListener launcherListener;

	Activity activity;

	private int reqcode = 0;

	public Launcher(Activity activity)
	{
		this.activity = activity;
		this.launcherListener = (ILauncherListener) activity;

	}

	public void DoThings(int reqcode)
	{
		this.reqcode = reqcode;

		launcherListener.onSuccess(this, reqcode, "OK");
	}

	@Override
	public void setLauncherListener(ILauncherListener listener)
	{
		this.launcherListener = listener;
	}

	@Override
	public ILauncherListener getLauncherListener()
	{
		return this.launcherListener;
	}

}
