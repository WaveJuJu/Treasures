package com.treasures.cn.o2o.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.treasures.cn.o2o.BaseActivity;
import com.treasures.cn.R;
import com.treasures.cn.o2o.activity.MainActivity;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.utils.BusiConst;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    public static void openLoginActivity(Activity activity){
        Intent intent = new Intent();
        intent.setClass(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        activity.startActivity(intent);
    }
    @BindView(R.id.password_et)
    EditText passwordET;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login_layout;
    }

    @OnClick({R.id.login_btn,R.id.registered_btn})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.login_btn:
                if (MemoryData.isUserInfoEmpty()){
                    RegisteredActivity.openRegisteredActivity(mActivity, BusiConst.RegisteredStatus.REGISTERED);
                }else{
                    String password = passwordET.getText().toString();
                    if (TextUtils.isEmpty(password)){
                        Toast.makeText(mActivity, getString(R.string.password_null_hint), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!TextUtils.equals(password,MemoryData.USER_INFO.getPassword())){
                        Toast.makeText(mActivity, getString(R.string.password_error), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MemoryData.isLogin = true;
                    Intent intent = new Intent();
                    intent.setClass(mActivity, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent);
                }
                break;
            case R.id.registered_btn:
                RegisteredActivity.openRegisteredActivity(mActivity, BusiConst.RegisteredStatus.REGISTERED);
                break;
        }

    }
}
