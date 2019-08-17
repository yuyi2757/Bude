package com.atguigu.bude.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.atguigu.bude.R;
import com.atguigu.bude.base.BaseFragment;
import com.atguigu.bude.pager.EssencePage;
import com.atguigu.bude.pager.LatesPage;
import com.atguigu.bude.pager.MyPage;
import com.atguigu.bude.pager.NewsPager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    ArrayList<BaseFragment> list;
    @InjectView(R.id.frameLayout)
    FrameLayout frameLayout;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;
    public int position;
    @InjectView(R.id.rb_community)
    ImageButton rbCommunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initFragment();//初始化Fragment
        initListener();//监听
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override               //i就是指的checkedID
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_cart:
                        position = 2;
                        break;
                    case R.id.rb_user:
                        position = 3;
                        break;
                }
                //根据选择的位置切换不同的页面  集合添加顺序不要打乱
                Fragment currentFragment = getFragment(position);
                //选择好页面后相对的也要切换到对应的额显示页面，创建方法如下：
                switchFragment(currentFragment);//根据得到的这个Fragment显示
            }
        });
        rgMain.check(R.id.rb_home);//默认选择主页面
    }

    Fragment tempFragment;

    private void switchFragment(Fragment currentFragment) {
        if (currentFragment != tempFragment) {
            //要是没添加
            if (currentFragment != null) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!currentFragment.isAdded()) {

                    if (tempFragment != null) {//之前的不为空就隐藏
                        transaction.hide(tempFragment);
                    }//把当前的添加进去
                    transaction.add(R.id.frameLayout, currentFragment);
                } else {//要是添加了就显示出来
                    if (tempFragment != null) {
                        transaction.hide(tempFragment);
                    }
                    transaction.show(currentFragment);//显示了
                }
                transaction.commit();
                tempFragment = currentFragment;
            }
        }
    }

    ///根据位置返回对应的页面
    private Fragment getFragment(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        }
        return null;
    }

    private void initFragment() {
        list = new ArrayList<>();
        list.add(new EssencePage());
        list.add(new NewsPager());
        list.add(new LatesPage());
        list.add(new MyPage());
    }

    @OnClick(R.id.rb_community)
    public void onClick() {
//        Toast.makeText(MainActivity.this, "更多页面", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,AnimationActivity.class);
        startActivity(intent);
    }
}
