package com.atguigu.bude.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.bude.R;
import com.atguigu.bude.adapter.RecycleViewAdapter;
import com.atguigu.bude.bean.PinglunListView;
import com.atguigu.bude.bean.headerBean;
import com.atguigu.bude.uitls.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class PingLun extends Activity {


    //第2次提交git本地
    @InjectView(R.id.my_title)
    TextView myTitle;
    @InjectView(R.id.setting)
    ImageView setting;
    @InjectView(R.id.yewan)
    ImageView yewan;
    @InjectView(R.id.iv_headpic)
    ImageView ivHeadpic;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_time_refresh)
    TextView tvTimeRefresh;
    @InjectView(R.id.ll_video_user_info)
    LinearLayout llVideoUserInfo;
    @InjectView(R.id.iv_right_more)
    ImageView ivRightMore;
    @InjectView(R.id.tv_context)
    TextView tvContext;
    @InjectView(R.id.iv_image_icon)
    ImageView ivImageIcon;
    @InjectView(R.id.iv_video_kind)
    ImageView ivVideoKind;
    @InjectView(R.id.tv_video_kind_text)
    TextView tvVideoKindText;
    @InjectView(R.id.tv_ding)
    TextView tvDing;
    @InjectView(R.id.tv_shenhe_ding_number)
    TextView tvShenheDingNumber;
    @InjectView(R.id.ll_ding)
    LinearLayout llDing;
    @InjectView(R.id.iv_cai)
    TextView ivCai;
    @InjectView(R.id.tv_shenhe_cai_number)
    TextView tvShenheCaiNumber;
    @InjectView(R.id.ll_cai)
    LinearLayout llCai;
    @InjectView(R.id.tv_posts_number)
    TextView tvPostsNumber;
    @InjectView(R.id.ll_share)
    LinearLayout llShare;
    @InjectView(R.id.iv_more)
    ImageView ivMore;
    @InjectView(R.id.ll_download)
    LinearLayout llDownload;
    @InjectView(R.id.lisview)
    ListView lisview;
    private headerBean hb;
    private List<PinglunListView.HotBean.ListBean> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ping_lun);
        ButterKnife.inject(this);
        getData();
        setData();
        listener();
//        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get().url(Constants.XAINGQING_PAGER).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "评论数据联网失败了");
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
            }
        });
    }

    private void processData(String response) {
        PinglunListView pinglunListView = new Gson().fromJson(response, PinglunListView.class);
        Log.e("TAG", "评论解析成功了");
        datas = pinglunListView.getHot().getList();
        if (datas != null && datas.size() > 0) {
            pinglunLisviewAdapter adapter = new pinglunLisviewAdapter();
            lisview.setAdapter(adapter);
        }

    }

    private void listener() {
        yewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //设置数据
    private void setData() {
        String user_name = hb.getUser_name();
        String passtime = hb.getPasstime();
        String url = hb.getUser_icon();//头像地址
        String tvtext = hb.getTvtext();//文本
        String image_url = hb.getImage_url();

        tvName.setText(user_name + "");
        tvTimeRefresh.setText(passtime);
        tvContext.setText(tvtext + "");
        ivImageIcon.setImageResource(R.drawable.bg_activities_item_end_transparent);
        Glide.with(this).load(image_url).placeholder(R.drawable.bg_activities_item_end_transparent)
                .error(R.drawable.bg_activities_item_end_transparent).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImageIcon);
        Glide.with(this).load(url).into(ivHeadpic);

        //暂时未设置点赞，分享数量等底部数据

        getDataFromNet();
    }

    //获取数据
    private void getData() {
        hb = (headerBean) getIntent().getSerializableExtra(RecycleViewAdapter.HEARD_BEAN);
    }

    class pinglunLisviewAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return datas.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(PingLun.this, R.layout.pinglun_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            //绑定数据
            PinglunListView.HotBean.ListBean listBean = datas.get(i);
            Glide.with(PingLun.this).load(listBean.getUser().getProfile_image()).into(holder.ivHeadpic);
            holder.tvName.setText(listBean.getUser().getUsername() + "");
            holder.content.setText(listBean.getContent()+"");
            return view;
        }


         class ViewHolder {
            @InjectView(R.id.iv_headpic)
            ImageView ivHeadpic;
            @InjectView(R.id.tv_name)
            TextView tvName;
            @InjectView(R.id.content)
            TextView content;
            @InjectView(R.id.ll_video_user_info)
            LinearLayout llVideoUserInfo;
            @InjectView(R.id.iv_right_more)
            ImageView ivRightMore;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

}
