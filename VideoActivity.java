package com.atguigu.bude.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.bude.R;
import com.atguigu.bude.adapter.RecycleViewAdapter;
import com.atguigu.bude.adapter.VideoPinglunRecycleView;
import com.atguigu.bude.bean.PinglunListView;
import com.atguigu.bude.bean.headerBean;
import com.atguigu.bude.uitls.Constants;
import com.atguigu.bude.uitls.Utils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;

public class VideoActivity extends AppCompatActivity {

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
    @InjectView(R.id.jcv_videoplayer)
    JCVideoPlayerStandard jcvVideoplayer;
    @InjectView(R.id.tv_play_nums)
    TextView tvPlayNums;
    @InjectView(R.id.tv_video_duration)
    TextView tvVideoDuration;
    @InjectView(R.id.rl_holder)
    RelativeLayout rlHolder;
    @InjectView(R.id.recycleview)
    RecyclerView recycleview;
    @InjectView(R.id.back)
    ImageView back;
    private headerBean datas;
    Utils utils;
    List<PinglunListView.HotBean.ListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        utils = new Utils();
        ButterKnife.inject(this);
        getData();
        setData();
        back.setOnClickListener(new backListener());
    }

    private void setData() {
        tvName.setText(datas.getUser_name() + "");
        tvTimeRefresh.setText(datas.getPasstime());
        tvContext.setText(datas.getTvtext() + "");
        boolean b = jcvVideoplayer.setUp(datas.getJcmedio_need_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        if (b) {
            Glide.with(this).load(datas.getVideo_url()).into(jcvVideoplayer.thumbImageView);
        }
        tvPlayNums.setText(datas.getPlayCount());
        tvVideoDuration.setText(datas.getVideo_time());
        tvPlayNums.setText(datas.getPlayCount() + "次播放");
        Glide.with(this).load(datas.getUser_icon()).into(ivHeadpic);
        GetDataFromNet();
    }

    private void GetDataFromNet() {
        OkHttpUtils.get().url(Constants.XAINGQING_PAGER).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "联网错误");
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
            }
        });
    }

    private void processData(String response) {
        PinglunListView pinglunListView = new Gson().fromJson(response, PinglunListView.class);
        list = pinglunListView.getHot().getList();
        if (list != null && list.size() > 0) {
            VideoPinglunRecycleView adapter = new VideoPinglunRecycleView(this, list);
            recycleview.setAdapter(adapter);
            recycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
    }

    //接收数据
    private void getData() {
        datas = (headerBean) getIntent().getSerializableExtra(RecycleViewAdapter.HEARD_BEAN);
    }
    class backListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }
}
