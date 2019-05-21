package com.zcf.bananavideohannan.fragment.feekbackfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.bean.IssueBean;
import com.zcf.bananavideohannan.network.Network;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@ContentView(R.layout.fragment_feedback_wenti)
public class CommonQuestionFragment extends MyBaseFragment {

    @ViewInject(R.id.lv_content)
    private ListView lv_content;

    @ViewInject(R.id.iv_none)
    private ImageView iv_none;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        ProgressDialogUtil.show(getActivity(), "正在请求...", false);
        Disposable d=Network.getVideoApi().problem(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString())
                .compose(MyBaseFragment.<CommonlyBean<IssueBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<IssueBean>>() {
                    @Override
                    public void accept(CommonlyBean<IssueBean> issueBeanCommonlyBean) throws Exception {
                        ProgressDialogUtil.dismiss();
                        List<IssueBean> data = issueBeanCommonlyBean.getData();
                        if (data==null||data.size()==0){
                            iv_none.setVisibility(View.VISIBLE);
                            lv_content.setVisibility(View.GONE);
                        }else {
                            lv_content.setAdapter(new MyAdapter(data));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ProgressDialogUtil.dismiss();
                    }
                });
    }



    private class MyAdapter extends BaseAdapter {

        private List<IssueBean> data;

        public MyAdapter(List<IssueBean> data) {
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

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyAdapter.ViewHolder holder=null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.issue_item, null, false);
                holder=new MyAdapter.ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder= (MyAdapter.ViewHolder) convertView.getTag();
            }
            holder.tv_title.setText((position+1)+"."+data.get(position).getAsk());
            holder.tv_message.setText(data.get(position).getAnswer());
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
            }

        }
    }
}
