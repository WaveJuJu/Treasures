package com.treasures.cn.popView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.treasures.cn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.popView
 * @ClassName: ConfirmDeletePopView
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-11 23:03
 */
public class ConfirmDeletePopView {
    private Activity mActivity;
    private PopupWindow popView;
    private int layoutId;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.rela_pop_view)
    RelativeLayout relaPopView;
    public ConfirmDeletePopView(Activity activity, int layoutId) {
        this.mActivity = activity;
        this.layoutId = layoutId;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.pop_window_confirm_handler_layout, null);
        ButterKnife.bind(this, layout);

        popView = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popView.setFocusable(true);
        popView.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popView.setOutsideTouchable(true);//点击外面不关闭
    }

    public void show(String hintStr) {
        if (hintStr != null || !TextUtils.isEmpty(hintStr)) {
            titleTxt.setText(hintStr);
        } else {
            titleTxt.setText("");
        }
        if (popView != null && !popView.isShowing()) {
            popView.showAtLocation(mActivity.findViewById(layoutId), Gravity.CENTER, 0, 0);
        }
    }

    public void close() {
        if (popView != null && popView.isShowing()) {
            popView.dismiss();
        }
    }

    @OnClick({R.id.cancel_txt, R.id.confirm_txt})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_txt:
                if (clickListener != null) {
                    clickListener.OnConfirmClickListener();
                }
                break;
            case R.id.cancel_txt:
                if (clickListener != null) {
                    clickListener.OnCancelClickListener();
                }
                break;
        }
    }


    public interface OnHintManagerClickListener {
        void OnCancelClickListener();

        void OnConfirmClickListener();
    }

    private OnHintManagerClickListener clickListener;

    public void setClickListener(OnHintManagerClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
