package com.zcf.bananavideohannan.base;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public abstract class GTBaseAdapter<T> extends android.widget.BaseAdapter {

	private Context context;
	private List<T> data;
	private LayoutInflater inflater;

	public GTBaseAdapter(Context context, List<T> data) {
		super();
		setContext(context);
		setData(data);
		setInflater(inflater);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		if (context == null) {
			throw new IllegalArgumentException("mContext=null");
		}
		this.context = context;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		if (data == null) {
			data = new ArrayList<T>();
		}
		this.data = data;
	}

	public LayoutInflater getInflater() {
		return inflater;
	}

	public void setInflater(LayoutInflater inflater) {
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
