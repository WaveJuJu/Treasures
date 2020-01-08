package com.treasures.cn.popView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.treasures.cn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.popView
 * @ClassName: PhotoSelectedPopView
 * @Description: java类作用描述 : 选择相册、相机
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-16 19:04
 */
public class PhotoSelectedPopView {
    private Activity mActivity;
    private PopupWindow popView;
    private int layoutId;
    @BindView(R.id.photo_txt)
    TextView photo_txt;
    @BindView(R.id.camera_txt)
    TextView camera_txt;

    public PhotoSelectedPopView(Activity activity, int layoutId) {
        this.mActivity = activity;
        this.layoutId = layoutId;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.pop_window_phone_selected_layout, null);
        ButterKnife.bind(this, layout);
        popView = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popView.setFocusable(true);
        popView.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popView.setOutsideTouchable(true);
    }

    public void show() {
        if (popView != null && !popView.isShowing()) {
            popView.showAtLocation(mActivity.findViewById(layoutId), Gravity.BOTTOM, 0, 0);
        }
    }

    public void show(String topName,String bottomName){
        if (popView != null && !popView.isShowing()) {
            photo_txt.setText(topName);
            camera_txt.setText(bottomName);
            popView.showAtLocation(mActivity.findViewById(layoutId), Gravity.BOTTOM, 0, 0);
        }
    }

    public void close() {
        if (popView != null && popView.isShowing()) {
            popView.dismiss();
        }
    }

    @OnClick({R.id.photo_txt, R.id.camera_txt,R.id.back_view})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back_view:
                close();
                break;
            case R.id.photo_txt:
                if (clickListener != null) {
                    clickListener.OnPhotoClickListener();
                    close();
                }
                break;
            case R.id.camera_txt:
                if (clickListener != null) {
                    clickListener.OnCameraClickListener();
                    close();
                }
                break;
        }
    }

    public interface OnPhotoClickListener {
        void OnCameraClickListener();
        void OnPhotoClickListener();
    }

    private OnPhotoClickListener clickListener;

    public void setClickListener(OnPhotoClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
