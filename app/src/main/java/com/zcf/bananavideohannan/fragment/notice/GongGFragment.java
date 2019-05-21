package com.zcf.bananavideohannan.fragment.notice;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.bean.GGBean;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.gaotai.baselib.util.AndroidUtil.dip2px;

/**
 * 标签筛选
 */
@ContentView(R.layout.fragment_notice_notice)
public class GongGFragment extends MyBaseFragment {
    @ViewInject(R.id.lv_notice)
    private ListView lv_notice;
    private MyAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadAllTypeData();
    }

    private void loadAllTypeData() {
        ProgressDialogUtil.show(getActivity(), "正在请求...", false);
        Disposable d = Network.getVideoApi().inform()
                .compose(MyBaseFragment.<CommonlyBean<GGBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<GGBean>>() {
                    @Override
                    public void accept(CommonlyBean<GGBean> commonlyBean) throws Exception {
                        ProgressDialogUtil.dismiss();
                        List<GGBean> data = commonlyBean.getData();
                        initSearchAdapter(data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ProgressDialogUtil.dismiss();
                    }
                });
    }

    private void initSearchAdapter(List<GGBean> data) {
        if (adapter==null){
            adapter = new MyAdapter(data);
            lv_notice.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    private class MyAdapter extends BaseAdapter {

        private List<GGBean> data;

        public MyAdapter(List<GGBean> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.gonggao_item, null, false);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tv_title.setText(data.get(position).getTitle());
            holder.tv_message.setText(data.get(position).getContent());
            holder.tv_time.setText(Utils.stampToDate(data.get(position).getCreate_time(),"yyyy年MM月dd日 HH:mm"));
            return convertView;
        }

        private class ViewHolder {
            public View rootView;
            public TextView tv_title;
            public TextView tv_message;
            public TextView tv_time;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
                this.tv_message = (TextView) rootView.findViewById(R.id.tv_message);
                this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            }

        }
    }

    /**
     * 设置高度
     */
    public static void setHeight(Context context, RecyclerView listView, List<?> list, int height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listView.getLayoutParams();
        params.height = dip2px(context, height) * list.size();
        listView.setLayoutParams(params);
    }


}
