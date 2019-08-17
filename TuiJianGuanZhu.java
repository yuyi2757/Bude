package com.atguigu.bude.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.atguigu.bude.R;
import com.atguigu.bude.adapter.MyTuijianRecycleviewAdapter;
import com.atguigu.bude.adapter.TypeLeftAdapter;
import com.atguigu.bude.bean.Mytuijian;
import com.atguigu.bude.bean.MytuijianGuanzhu;
import com.atguigu.bude.uitls.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

public class TuiJianGuanZhu extends AppCompatActivity {
    List<MytuijianGuanzhu.ListBean> data;
    @InjectView(R.id.lv_left)
    ListView lvLeft;
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.yewan)
    ImageView yewan;
    private String[] titles = new String[]{"推荐", "网红", "工作室", "系列", "创意", "趣图", "自制", "视频",
            "搞笑", "图片", "精品"};
    private String[] urls = new String[]{Constants.left_tuijian, Constants.left_wanghong, Constants.left_jingpin,
            Constants.left_gaoxiao, Constants.left_chuangyi, Constants.left_shipin, Constants.left_tuwen, Constants.left_shenghuo};

    private TypeLeftAdapter leftadapter;
    private MytuijianGuanzhu mytuijianGuanzhu;
    private List<Mytuijian.TopListBean> top_list;
    private boolean ischeck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_jian_guan_zhu);
        ButterKnife.inject(this);
        initData();
        yewan.setOnClickListener(new backListener());
    }


    public static int Tag;

    private void initData() {//联网加载视图
        leftadapter = new TypeLeftAdapter(TuiJianGuanZhu.this, titles);
        lvLeft.setAdapter(leftadapter);
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                leftadapter.setChangeSelected(i);
                leftadapter.notifyDataSetChanged();
                if (i > 0) {
                    ischeck = true;//意思是：不点击任何一项，默认为false 适配器里就ischeck==false 才执行推荐数据的显示
                }               //只要点击下面任何一项，ischeck==true 就不满足适配器条件，执行另一个适配器显示，实现了不冲突的
                getDataFromeNet(urls[i]);//切换显示不同的数据适配器
            }
        });
        getDataFromeNet(urls[0]);//默认一打开就显示第一个推荐的数据，一旦点击就执行另一个适配器。两种数据公用一个适配器
    }

    private void getDataFromeNet(final String url) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "联网失败");
            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null) {
//                    if(ischeck==true) {
                    if (url.equals(urls[0])) {
                        Tag = 1;
                        progressTuiJian(response);
                    }

//                    }
                    processData(response);

                }
            }
        });
    }

    private void progressTuiJian(String response) {
        Mytuijian mytuijian = new Gson().fromJson(response, Mytuijian.class);
        top_list = mytuijian.getTop_list();
        MyTuijianRecycleviewAdapter adapter = new MyTuijianRecycleviewAdapter(this, null, top_list, false);
        listview.setAdapter(adapter);
        Log.e("TAG", "推荐数据解析成功");
    }

    private void processData(String response) {
        mytuijianGuanzhu = new Gson().fromJson(response, MytuijianGuanzhu.class);
        data = mytuijianGuanzhu.getList();
        if (data != null && data.size() > 0) {
            //适配器

            MyTuijianRecycleviewAdapter adapter = new MyTuijianRecycleviewAdapter(this, data, null, ischeck);
            listview.setAdapter(adapter);
            Log.e("TAG", "其他解析了");
        }

    }

    class backListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            finish();
        }
    }
}
