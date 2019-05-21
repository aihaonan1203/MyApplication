package com.zcf.bananavideohannan.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.activity.BaseFragment;
import com.gaotai.baselib.util.LoadImageUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.bean.GuangGaoBean;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 单个图片展示
 */
@ContentView(R.layout.fragment_photos_choice)
public class PictureViewPagerFragment2 extends BaseFragment {
    /**
     * 图片的URL
     */
    private String mImageUrl;
    /**
     * 图片控件
     */
    @ViewInject(R.id.iv_photo)
    private ImageView iv_photo;

    /**
     * 图片列表
     */
    public static final String EXTRA_IMAGURL = "url";
    public static String static_imageUrl;
    static GuangGaoBean mDomain;
    private String url;
    private DcAndroidContext app;
    private String uid;

    public static PictureViewPagerFragment2 newInstance(GuangGaoBean domain) {
        try {
            mDomain = domain;
            static_imageUrl = domain.getImage();
            final PictureViewPagerFragment2 f = new PictureViewPagerFragment2();
            final Bundle args = new Bundle();
            //"file:/"
            args.putString(EXTRA_IMAGURL, domain.getImage());
            args.putString("mUrl", domain.getAdlink());
            f.setArguments(args);
            return f;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 是否为网络地址
     */
    private boolean isHttpUrl = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url=getArguments() != null ? getArguments().getString("mUrl") : null;
        mImageUrl = getArguments() != null ? getArguments().getString(EXTRA_IMAGURL) : null;
        if (mImageUrl.indexOf("http:") != 0 && mImageUrl.indexOf("https:") != 0 && mImageUrl.indexOf("file:") != 0) {
            //不是网络图片 添加本地标识
            isHttpUrl = false;
            mImageUrl = "file:/" + mImageUrl;
        }
        app = (DcAndroidContext) getActivity().getApplication();
        if (app != null) {
            if (app.getParam(Consts.USER_UID) != null) {
                uid = app.getParam(Consts.USER_UID).toString();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = super.onCreateView(inflater, container, savedInstanceState);
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openBrowser(url);
//                ToastUtil.toastShort(getActivity().getApplicationContext(), "图片地址" + static_imageUrl.substring(static_imageUrl.indexOf("u="), static_imageUrl.indexOf("&fm")));
            }
        });
        return v;
    }

    // 顶部广告页
    private void openBrowser(String linkUrl) {
        Uri uri = Uri.parse("http://"+linkUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        OkGo.<String>post(Consts.DOMAIN_URL+"/index.php/index/user/adlinkinc")
                .tag(this)
                .params("uid",uid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("onSuccess: ",response.toString() );
                        try {
                            JSONObject jsonObject=new JSONObject(response.body());
                            ToastUtil.toastShort(getActivity(),jsonObject.getString("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LoadImageUtil.loadImg(mImageUrl, iv_photo, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
//        iv_photo.setImageResource(R.drawable.banner);
    }
}