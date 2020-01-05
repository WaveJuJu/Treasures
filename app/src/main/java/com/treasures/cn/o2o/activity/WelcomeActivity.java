package com.treasures.cn.o2o.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.treasures.cn.R;
import com.treasures.cn.o2o.BaseActivity;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.ScreenUtils;

import butterknife.BindView;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.o2o
 * @ClassName: WelcomeActivity
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2020-01-03 16:43
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2020-01-03 16:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.welcome_img)
    ImageView welcome_img;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_welcome_layout;
    }


    @Override
    protected void initView() {
        super.initView();
        welcome_img.post(() -> {
            Glide.with(getMActivity())
                    .load(Constant.start_figure)
                    .priority(Priority.IMMEDIATE)
                    .placeholder(R.mipmap.login_back)
                    .error(R.color.white)
                    .override(ScreenUtils.getWidth(getApplicationContext()),ScreenUtils.getHeight(getApplicationContext()))
                    .into(welcome_img);
        });
        handler.sendEmptyMessageDelayed(0, 2000);  //定个时 3秒
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };  //消息处理对象,负责发送与处理消息

    public void getHome() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
