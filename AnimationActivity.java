package com.atguigu.bude.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.bude.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnimationActivity extends AppCompatActivity {

    @InjectView(R.id.lin1)
    LinearLayout lin1;
    @InjectView(R.id.lin2)
    LinearLayout lin2;
    @InjectView(R.id.lin3)
    LinearLayout lin3;
    @InjectView(R.id.lin4)
    LinearLayout lin4;
    @InjectView(R.id.lin5)
    LinearLayout lin5;
    @InjectView(R.id.lin6)
    LinearLayout lin6;
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.activity_animation)
    RelativeLayout activityAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.inject(this);

        TranslateAnimation animation6 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                , 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        animation6.setStartOffset(20);
        animation6.setDuration(850);
        lin6.startAnimation(animation6);

        TranslateAnimation animation5 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                , 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        animation5.setStartOffset(400);
        animation5.setDuration(500);
        lin5.startAnimation(animation5);

        TranslateAnimation animation4 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                , 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        animation4.setStartOffset(240);
        animation4.setDuration(630);
        lin4.startAnimation(animation4);

        TranslateAnimation animation3 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                , 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        animation3.setStartOffset(852);
        animation3.setDuration(700);
        lin3.startAnimation(animation3);

        TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                , 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        animation2.setStartOffset(1000);
        animation2.setDuration(500);
        lin2.startAnimation(animation2);

        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                , 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        animation1.setStartOffset(1150);
        animation1.setDuration(800);
        lin1.startAnimation(animation1);

        /////////////////////////////////////
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TranslateAnimation ma=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                        , 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                ma.setDuration(511);
                ma.setFillAfter(true);
                lin6.startAnimation(ma);

                TranslateAnimation ma1=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                        , 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                ma1.setDuration(240);
                ma1.setFillAfter(true);
                lin5.startAnimation(ma1);

                TranslateAnimation ma2=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                        , 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                ma2.setDuration(379);
                ma2.setFillAfter(true);
                lin4.startAnimation(ma2);

                TranslateAnimation ma3=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                        , 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                ma3.setDuration(651);
                ma3.setFillAfter(true);
                lin3.startAnimation(ma3);

                TranslateAnimation ma4=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                        , 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                ma4.setDuration(432);
                ma4.setFillAfter(true);
                lin2.startAnimation(ma4);

                TranslateAnimation ma5=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT
                        , 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                ma5.setDuration(735);
                ma5.setFillAfter(true);
                lin1.startAnimation(ma5);
                ////////////////////////////////////
                ma5.setAnimationListener(new lisintener());

            }
        });

    }
    class  lisintener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
