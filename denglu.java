package com.atguigu.bude.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.atguigu.bude.R;
import com.atguigu.bude.uitls.CacheUtils;
import com.atguigu.bude.uitls.MyConstants;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class denglu extends Activity {


    @InjectView(R.id.phonenumber)
    EditText phonenumber;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.denglv)
    ImageView denglu;
    @InjectView(R.id.qq_denglu)
    ImageView qqDenglu;
    @InjectView(R.id.xinlang_denglu)
    ImageView xinlangDenglu;
    @InjectView(R.id.tencentweibo_denglu)
    ImageView tencentweiboDenglu;
    @InjectView(R.id.weixin)
    ImageView weixin;
    @InjectView(R.id.q_q)
    ImageView qQ;
    public static Tencent mTencent;
    public UserInfo mInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denglu_item);
        ButterKnife.inject(this);
        initData();
    }



    @OnClick({R.id.denglv, R.id.qq_denglu, R.id.xinlang_denglu, R.id.tencentweibo_denglu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.denglv:
                Toast.makeText(this, "登录了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.qq_denglu:
                Toast.makeText(this, "QQ登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.xinlang_denglu:
                Toast.makeText(this, "新浪登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tencentweibo_denglu:
                Toast.makeText(this, "腾讯微博登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.weixin:
                break;
            case R.id.q_q:
                break;
        }
    }

    private void initData() {
//QQ的初始化
        mTencent = Tencent.createInstance("1105704769", this.getApplicationContext());
        mInfo = new UserInfo(this, mTencent.getQQToken());
    }
    @OnClick(R.id.q_q)
    void qq_login_click() {
        mTencent.login(this, "all", loginListener);
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {

            initOpenidAndToken(values);

            //下面的这个必须放到这个地方，要不然就会出错
            updateUserInfo();

        }
    };


    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);

                }

                @Override
                public void onCancel() {

                }
            };

            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        } else {

        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                return;
            }
            doComplete((JSONObject) response);
        }

        @Override
        public void onError(UiError e) {

        }

        @Override
        public void onCancel() {

        }

        protected void doComplete(JSONObject values) {

        }
    }

    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private String imgurl;
    private String nickname;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            JSONObject response = (JSONObject) msg.obj;

            try {
                Log.e("TAG", "response==========" + response);
                imgurl = response.getString("figureurl_qq_2");
                nickname = response.getString("nickname");
                String password = "123456";

//                //设置 用户名 密码
//                setAvator(imgurl);
//                etLoginPhone.setText(nickname);
//                etLoginPwd.setText(password);
                //保存
                CacheUtils.putString(denglu.this,
                        MyConstants.USER_NAME, nickname);
                CacheUtils.putString(denglu.this,
                        MyConstants.USER_PASSWORD, "123456");
                CacheUtils.putString(denglu.this,
                        MyConstants.IMAGE_URL,imgurl);

                //设置返回的结果
                Intent intent = getIntent();
                intent.putExtra("screen_name",nickname);
                intent.putExtra("profile_image_url",imgurl);
                setResult(RESULT_OK,intent);

                Log.e("TAG", "nickname=========" + nickname);

                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ruolan", "-->onActivityResult " + requestCode + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
