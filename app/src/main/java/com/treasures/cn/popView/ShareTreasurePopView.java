package com.treasures.cn.popView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.treasures.cn.R;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.popView
 * @ClassName: ShareTreasurePopView
 * @Description: java类作用描述 分享藏品弹窗
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-24 20:52
 */
public class ShareTreasurePopView {
    private Activity mActivity;
    private PopupWindow popView;
    private int layoutId;
    private Treasures mTreasures;
    @BindView(R.id.share_content_img)
    ImageView share_content_img;
    Bitmap bitmap;

    public ShareTreasurePopView(Activity activity, Treasures treasures, int layoutId) {
        this.mActivity = activity;
        this.layoutId = layoutId;
        this.mTreasures = treasures;
        initView();
    }

    public ShareTreasurePopView(Activity activity, Bitmap bitmap, int layoutId) {
        this.mActivity = activity;
        this.layoutId = layoutId;
        this.bitmap = bitmap;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.pop_window_share_treasure_layout, null);
        ButterKnife.bind(this, layout);
        popView = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popView.setFocusable(true);
        popView.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popView.setOutsideTouchable(true);//点击外面不关闭
    }

    @SuppressLint("SetTextI18n")
    private void reloadView() {
        @SuppressLint("InflateParams") View view = mActivity.getLayoutInflater().inflate(R.layout.include_share_view_layout, null);
        ImageView share_imageview = view.findViewById(R.id.share_imageview);
        TextView notImgTxt = view.findViewById(R.id.notImgTxt);
        TextView detail_name_txt = view.findViewById(R.id.detail_name_txt);
        TextView detail_desc_txt = view.findViewById(R.id.detail_desc_txt);
        TextView share_size_txt = view.findViewById(R.id.share_size_txt);
        TextView share_year_txt = view.findViewById(R.id.share_year_txt);
        if (mTreasures.getImages() != null && mTreasures.getImages().size() > 0) {
            share_imageview.setVisibility(View.VISIBLE);
            notImgTxt.setVisibility(View.GONE);
            Bitmap treasureBitmap = ImageUtils.getScaleBitmap(mActivity, mTreasures.getImages().get(0));
            share_imageview.setImageBitmap(treasureBitmap);
        } else {
            share_imageview.setVisibility(View.GONE);
            notImgTxt.setVisibility(View.VISIBLE);
        }
        detail_name_txt.setText(mTreasures.getSubTitle());
        if (TextUtils.isEmpty(mTreasures.getDescribe())) {
            detail_desc_txt.setVisibility(View.GONE);
        } else {
            detail_desc_txt.setVisibility(View.VISIBLE);
            detail_desc_txt.setText(mTreasures.getDescribe());
        }
        String size = mTreasures.getSize();
        if (size == null || TextUtils.isEmpty(size)) {
            size = " -- ";
        }
        share_size_txt.setText(mActivity.getString(R.string.detail_size_title) + size);
        String year = mTreasures.getYear();
        if (year == null || TextUtils.isEmpty(year)) {
            year = " -- ";
        }
        share_year_txt.setText(mActivity.getString(R.string.detail_year_title) + year);

        new Handler().postDelayed(() -> {
            layoutView(view, (int) mActivity.getResources().getDimension(R.dimen.dp_328)
                    , (int) mActivity.getResources().getDimension(R.dimen.dp_587));
            bitmap = ImageUtils.loadBitmapFromView(view);
            if (bitmap == null) {
                Toast.makeText(mActivity, mActivity.getString(R.string.image_create_error), Toast.LENGTH_SHORT).show();
                close();
                return;
            }
            share_content_img.setImageBitmap(bitmap);
        }, 500);
    }

    private static void layoutView(View v, int width, int height) {
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    public void show() {
        if (popView != null && !popView.isShowing()) {
            popView.showAtLocation(mActivity.findViewById(layoutId), Gravity.CENTER, 0, 0);
            reloadView();
        }
    }
    public void showShareImg(){
        if (popView != null && !popView.isShowing()) {
            popView.showAtLocation(mActivity.findViewById(layoutId), Gravity.CENTER, 0, 0);
            reloadShareImgView();
        }
    }

    private void close() {
        if (popView != null && popView.isShowing()) {
            popView.dismiss();
        }
    }

    @OnClick({R.id.wx_f_linear, R.id.wx_c_linear, R.id.share_close_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wx_f_linear:
                if (onShareTreasureClick != null) {
                    onShareTreasureClick.OnShareBlock(BusiConst.ShareType.WECHAT_FRIENDS, bitmap);
                    close();
                }
                break;
            case R.id.wx_c_linear:
                if (onShareTreasureClick != null) {
                    onShareTreasureClick.OnShareBlock(BusiConst.ShareType.WECHAT_CIRCLE, bitmap);
                    close();
                }
                break;
            case R.id.share_close_btn:
                close();
                break;
        }
    }

    private void reloadShareImgView() {
        if (bitmap == null) {
            close();
            return;
        }
        share_content_img.setImageBitmap(bitmap);
    }


    public interface OnShareTreasureClick {
        void OnShareBlock(BusiConst.ShareType type, Bitmap bitmap);
    }

    private OnShareTreasureClick onShareTreasureClick;

    public void setOnShareTreasureClick(OnShareTreasureClick onShareTreasureClick) {
        this.onShareTreasureClick = onShareTreasureClick;
    }

}
