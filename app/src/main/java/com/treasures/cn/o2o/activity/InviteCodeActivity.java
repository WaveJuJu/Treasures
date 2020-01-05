package com.treasures.cn.o2o.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.treasures.cn.R;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.handler.UserInfoHelp;
import com.treasures.cn.o2o.BaseActivity;
import com.treasures.cn.utils.InviteCodeUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.o2o.activity
 * @ClassName: InviteCodeActivity
 * @Description: java类作用描述 邀请码
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-23 16:29
 */
public class InviteCodeActivity extends BaseActivity {

    public static void openTreasureDetailActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, InviteCodeActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.my_invite_code_txt)
    TextView my_invite_code_txt;
    @BindView(R.id.invite_code1_txt)
    TextView invite_code1_txt;
    @BindView(R.id.invite_code2_txt)
    TextView invite_code2_txt;
    @BindView(R.id.invite_code3_txt)
    TextView invite_code3_txt;


    @BindView(R.id.timeOutTxt)
    TextView timeOutTxt;
    @BindView(R.id.code_success1)
    ImageView code_success1;
    @BindView(R.id.code_success2)
    ImageView code_success2;
    @BindView(R.id.code_success3)
    ImageView code_success3;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_invite_code_layout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        super.initView();
        my_invite_code_txt.post(() -> {
            String inviteCode = MemoryData.USER_INFO.getInviteCode();
            if (TextUtils.isEmpty(inviteCode)) {
                my_invite_code_txt.setTextColor(ContextCompat.getColor(getMActivity(), R.color.gray_b2));
                my_invite_code_txt.setText(getMActivity().getString(R.string.invitation_error));
            } else {
                my_invite_code_txt.setText(inviteCode);
                if (InviteCodeUtils.isInviteCodeValid(true, inviteCode)) {
                    my_invite_code_txt.setTextColor(ContextCompat.getColor(getMActivity(), R.color.title_clolor));
                    timeOutTxt.setVisibility(View.GONE);
                } else {
                    my_invite_code_txt.setTextColor(ContextCompat.getColor(getMActivity(), R.color.gray_b2));
                    timeOutTxt.setVisibility(View.VISIBLE);
                }
            }
        });
        reloadInviteCode();
    }

    private void reloadInviteCode() {
        if (MemoryData.USER_INFO.getInviteCodeArr().size() > 0) {
            String inviteCode1 = MemoryData.USER_INFO.getInviteCodeArr().get(0);
            invite_code1_txt.setText(inviteCode1);
            code_success1.setVisibility(View.VISIBLE);
            if (MemoryData.USER_INFO.getInviteCodeArr().size() > 1) {
                String inviteCode2 = MemoryData.USER_INFO.getInviteCodeArr().get(1);
                invite_code2_txt.setText(inviteCode2);
                code_success2.setVisibility(View.VISIBLE);
                if (MemoryData.USER_INFO.getInviteCodeArr().size() > 2) {
                    String inviteCode3 = MemoryData.USER_INFO.getInviteCodeArr().get(2);
                    invite_code3_txt.setText(inviteCode3);
                    code_success3.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @OnClick({R.id.back_btn})
    public void backClick(View v) {
        finish();
    }

    @OnClick({R.id.invite_code1_txt, R.id.invite_code2_txt, R.id.invite_code3_txt})
    public void onClick(View v) {
        showInputInviteCode((TextView) v);
    }

    private void showInputInviteCode(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText().toString())) {
            return;
        }
        showInvitationCodePop();
    }

    private void showInvitationCodePop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getMActivity());
        builder.setIcon(R.mipmap.logo_icon);
        builder.setTitle(getMActivity().getString(R.string.please_input_invite_code));
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getMActivity()).inflate(R.layout.edit_user_name_dialog_layout, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
        EditText inviteCodeET = view.findViewById(R.id.userNameEdit);
        inviteCodeET.setHint(R.string.invitation_code);
        builder.setPositiveButton("确定", (dialog, which) -> {
            String inviteCode = inviteCodeET.getText().toString().trim();
            if (!TextUtils.isEmpty(inviteCode)) {
                if (!InviteCodeUtils.isInviteCodeValid(false, inviteCode)) {
                    Toast.makeText(getMActivity(), getMActivity()
                                    .getString(R.string.invitation_invalid)
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                MemoryData.USER_INFO.getInviteCodeArr().add(inviteCode);
                UserInfoHelp.saveUser();
                reloadInviteCode();
            }
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
        });
        builder.show();

    }

}
