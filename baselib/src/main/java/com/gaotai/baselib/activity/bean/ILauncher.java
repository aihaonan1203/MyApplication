package com.gaotai.baselib.activity.bean;

import com.gaotai.baselib.activity.ILauncherListener;

public interface ILauncher {
	void setLauncherListener(ILauncherListener listener);
	ILauncherListener getLauncherListener();
}
