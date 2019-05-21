package com.gaotai.baselib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/***
 * 用于ScrollView嵌套ListView时 ListView展示不全
 * 保留了ListView原有的所有方法，包括notifyDataSetChanged方法，相比其他方法是最趋近于完美的方法
 * ，只是需要在Activity中设定ScrollView滚动至顶端 ScrollView.smoothScrollTo(0, 0);
 * 注意： 嵌套 ListView 如果行数大于500 建议不要使用嵌套方式
 * @author MengLiang
 *
 */
public class ListViewForScrollView extends ListView
{
	public ListViewForScrollView(Context context)
	{
		super(context);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	
	/**
	 * 重写该方法，达到使ListView适应ScrollView的效果
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
