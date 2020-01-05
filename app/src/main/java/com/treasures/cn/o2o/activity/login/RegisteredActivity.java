package com.treasures.cn.o2o.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.treasures.cn.R;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.handler.UserInfoHelp;
import com.treasures.cn.o2o.BaseActivity;
import com.treasures.cn.o2o.activity.MainActivity;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.BusiException;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.DateTimeUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisteredActivity extends BaseActivity {

    public static void openRegisteredActivity(Activity activity, BusiConst.RegisteredStatus status) {
        Intent intent = new Intent();
        intent.setClass(activity, RegisteredActivity.class);
        intent.putExtra(Constant.REGISTERED_ACTIVITY_KEY, status.toString());
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        activity.startActivity(intent);
    }

    @BindView(R.id.nice_name_et)
    EditText nice_name_et;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.confirm_password_et)
    EditText confirm_password_et;
    @BindView(R.id.back_txt)
    TextView backTxt;
    @BindView(R.id.registered_title_txt)
    TextView registered_title_txt;
    @BindView(R.id.login_btn)
    Button login_btn;
    boolean isModify = false;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_registered_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        String toType = getIntent().getStringExtra(Constant.REGISTERED_ACTIVITY_KEY);
        if (!TextUtils.isEmpty(toType)) {
            isModify = TextUtils.equals(toType, BusiConst.RegisteredStatus.MODIFY.toString());
        }
        login_btn.post(() -> {
            login_btn.setText(mActivity.getString(isModify ? R.string.save : R.string.login));
            registered_title_txt.setText(mActivity.getString(isModify ? R.string.modify_password : R.string.registered));
            backTxt.setText(mActivity.getString(isModify ? R.string.back : R.string.login));
        });
    }

    @OnClick({R.id.login_btn, R.id.back_linear})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back_linear:
                this.finish();
                break;
            case R.id.login_btn:
                saveLoginData();
                break;
        }
    }

    private void saveLoginData() {
//        String niceName = nice_name_et.getText().toString();
        String passwordStr = password_et.getText().toString();
        String confirmPasswordStr = confirm_password_et.getText().toString();
//        if (TextUtils.isEmpty(niceName)){
//            Toast.makeText(mActivity, "用户名不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(passwordStr)) {
//            Toast.makeText(mActivity, "密码不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(confirmPasswordStr)) {
//            Toast.makeText(mActivity, "请确认密码", Toast.LENGTH_SHORT).show();
//            return;
//        }
        try {
            String jsonStr = MemoryData.serializeUserInfo(MemoryData.USER_INFO);
            if (TextUtils.isEmpty(passwordStr) && TextUtils.isEmpty(confirmPasswordStr)) {
                MemoryData.USER_INFO.setPassword("");
                UserInfoHelp.saveUser();
                Toast.makeText(mActivity, mActivity.getString(R.string.cancel_password_success), Toast.LENGTH_SHORT).show();
                this.finish();
                return;
            }
            if (!TextUtils.equals(passwordStr, confirmPasswordStr)) {
                Toast.makeText(mActivity, mActivity.getString(R.string.different_passwords), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(MemoryData.USER_INFO.getCreateTime())){
                MemoryData.USER_INFO.setCreateTime(DateTimeUtil.formatDateTime(new Date()));
            }
            MemoryData.USER_INFO.setPassword(passwordStr);
            UserInfoHelp.saveUser();
            if (isModify) {
                LoginActivity.openLoginActivity(mActivity);
                return;
            }
            Intent intent = new Intent(mActivity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);
        } catch (BusiException e) {
            Toast.makeText(mActivity, mActivity.getString(R.string.save_failure), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
