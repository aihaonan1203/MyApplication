package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 
 * PullToRefreshRecyclerView
 * 对PullToRefresh的扩展,增加支持RecyclerView
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context,
                                                 AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    public void setAdapter(Adapter adapter) {
        if (adapter != null)
        	mRefreshableView.setAdapter(adapter);
    }

    public void setLayoutManager(LinearLayoutManager linearLayoutManager) {
    	mRefreshableView.setLayoutManager(linearLayoutManager);
    }

    public void setItemAnimator(DefaultItemAnimator defaultItemAnimator) {
    	mRefreshableView.setItemAnimator(defaultItemAnimator);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
    	return mRefreshableView.getLayoutManager();
    }

    public boolean setSelection(int position) {

    	mRefreshableView.smoothScrollToPosition(position);
    	/*
    	LinearLayoutManager linearLayoutManager = (LinearLayoutManager)mRefreshableView.getLayoutManager();
    	int pos = linearLayoutManager.findFirstVisibleItemPosition();
    	View view = mRefreshableView.getChildAt(pos);
    	int top = 0;
    	if (view != null) {
    	    top = view.getTop();
    	}

    	linearLayoutManager.scrollToPositionWithOffset(position, top); */
    	/*
    	//先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (position <= firstItem ){
        //当要置顶的项在当前显示的第一个项的前面时
        	mRefreshableView.scrollToPosition(position);
        }else if ( position <= lastItem ){
        //当要置顶的项已经在屏幕上显示时
            int top = mRefreshableView.getChildAt(position - firstItem).getTop();
            mRefreshableView.scrollBy(0, top);
        }else{
        	//当要置顶的项在当前显示的最后一项的后面时
        	mRefreshableView.scrollToPosition(position);
        	return true;
        }
        return false;
        */
    	//mRefreshableView.getLayoutManager().scrollToPosition(position);

        return true;

    }

    //设置间距
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
    	mRefreshableView.addItemDecoration(decor);
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    /**
     * @Description: 判断第一个条目是否完全可见
     *
     */
    private boolean isFirstItemVisible() {
        final Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isFirstItemVisible. Empty View.");
            }
            return true;

        } else {
            // 第一个条目完全展示,可以刷新
            if (getFirstVisiblePosition() == 0) {
                return mRefreshableView.getChildAt(0).getTop() >= mRefreshableView
                        .getTop();
            }
        }

        return false;
    }

    /**
     * @Description: 获取第一个可见子View的位置下标
     *
     * @return int: 位置
     */
    private int getFirstVisiblePosition() {
        View firstVisibleChild = mRefreshableView.getChildAt(0);
        return firstVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(firstVisibleChild) : -1;
    }

    /**
     * @Description: 判断最后一个条目是否完全可见
     *
     * @return boolean:
     */
    private boolean isLastItemVisible() {
        final Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isLastItemVisible. Empty View.");
            }
            return true;

        } else {
            // 最后一个条目View完全展示,可以刷新
            int lastVisiblePosition = getLastVisiblePosition();
            if(lastVisiblePosition >= mRefreshableView.getAdapter().getItemCount()-1) {
                return mRefreshableView.getChildAt(
                        mRefreshableView.getChildCount() - 1).getBottom() <= mRefreshableView
                        .getBottom();
            }
        }

        return false;
    }

    /**
     * @Description: 获取最后一个可见子View的位置下标
     *
     * @return int: 位置
     */
    public int getLastVisiblePosition() {
        View lastVisibleChild = mRefreshableView.getChildAt(mRefreshableView
                .getChildCount() - 1);
        return lastVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(lastVisibleChild) : -1;
    }

}