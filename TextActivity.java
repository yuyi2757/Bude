package com.atguigu.bude.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.bude.R;
import com.atguigu.bude.adapter.RecycleViewAdapter;
import com.atguigu.bude.bean.headerBean;
import com.atguigu.bude.bean.recommeBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TextActivity extends AppCompatActivity {

    private LayoutInflater inflater;
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
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
    @InjectView(R.id.recycleview)
    RecyclerView recycleview;
    @InjectView(R.id.activity_text)
    LinearLayout activityText;
    private headerBean hb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.inject(this);
        getData();
        setData();
    }

    private void setData() {
        String user_name = hb.getUser_name();
        String passtime = hb.getPasstime();
        String url = hb.getUser_icon();
        String tvtext = hb.getTvtext();

        Glide.with(this).load(url).into(ivHeadpic);
        tvName.setText(user_name + "");
        tvTimeRefresh.setText(passtime);
        tvContext.setText(tvtext + "");
        TextAdapter adapter = new TextAdapter();
        recycleview.setAdapter(adapter);

//        getDataFromNet();
    }

//    private void getDataFromNet() {
//        OkHttpUtils.get().url(Constants.RECOM_MEND).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Log.e("TAG", "联网失败");
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                processData();
//            }
//        });
//    }
//
//    private void processData() {
//new Gson().fromJson()
//    }

    //获取数据
    private void getData() {
        hb = (headerBean) getIntent().getSerializableExtra(RecycleViewAdapter.HEARD_BEAN);
    }

    recommeBean rb = new recommeBean();

    class TextAdapter extends RecyclerView.Adapter<TextAdapter.MyViewHolder> {


        public TextAdapter() {
            inflater = LayoutInflater.from(TextActivity.this);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(TextActivity.this, inflater.inflate(R.layout.video_holder_item, null));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            recommeBean.ListBean listBean = rb.getList().get(position);
//            holder.tvName.setText(listBean.getU().getName());
//            holder.content.setText(listBean.getTop_comments().get(0).getContent());
//            Glide.with(TextActivity.this).load(listBean.getU().getHeader().get(0));
            List<recommeBean.ListBean.TopCommentsBean> top = listBean.getTop_comments();
            if(top!=null &&top.size()>0) {
                for (int i=0;i<top.size();i++){
                    StringBuffer buff = new StringBuffer();
                    if(i<3) {
                        buff.append(listBean.getTop_comments().get(i).getContent());
                        holder.tvName.setText(listBean.getU().getName());
                        holder.content.setText(buff.toString());
                        Glide.with(TextActivity.this).load(listBean.getU().getHeader().get(0));
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            return rb.getList().size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
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
            public MyViewHolder(TextActivity textActivity, View itemView) {
                super(itemView);
                ButterKnife.inject(this,itemView);
            }


        }
    }

}
