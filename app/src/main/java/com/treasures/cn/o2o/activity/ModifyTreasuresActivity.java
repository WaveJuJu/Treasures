package com.treasures.cn.o2o.activity;

import android.view.KeyEvent;

import com.treasures.cn.R;
import com.treasures.cn.o2o.BaseActivity;

import androidx.appcompat.app.AlertDialog;

public class ModifyTreasuresActivity extends BaseActivity {
    @Override
    public int getLayoutResId() {
        return R.layout.activity_modify_treasures_layout;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getMActivity());
            builder.setMessage(getMActivity().getString(R.string.confirm_close));
            builder.setPositiveButton("确定", (dialogInterface, i) -> {
                getMActivity().finish();
            });
            builder.setNegativeButton("取消", (dialogInterface, i) -> {
            });
            builder.show();
        }
        return false;
    }
}
