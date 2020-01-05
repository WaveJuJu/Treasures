package com.treasures.cn.popView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.treasures.cn.R;
import com.treasures.cn.utils.BlurUtil;
import com.treasures.cn.utils.Constant;
import com.makeramen.roundedimageview.RoundedImageView;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * @date: 编辑弹窗
 * @author: WaveJuJu
 */
public class ModifyTreasuresPopView {
    private Activity mActivity;
    private PopupWindow popView;
    private int layoutId;
    @BindView(R.id.rela_pop_view)
    RelativeLayout relaPopView;
    @BindView(R.id.back_img)
    RoundedImageView backImg;
    private BlurUtil blurUtil;

    @BindView(R.id.modify_img)
    ImageView modifyImg;
    @BindView(R.id.delete_img)
    ImageView deleteImg;
    @BindView(R.id.copy_img)
    ImageView copyImg;

    public ModifyTreasuresPopView(Activity activity, int layoutId) {
        this.mActivity = activity;
        this.layoutId = layoutId;
        blurUtil = new BlurUtil(activity);
        blurUtil.utils.info.initPhoneInfo(mActivity);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.pop_window_modify_treasures_layout, null);
        ButterKnife.bind(this, layout);

        popView = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(mActivity, R.color.transparent));
        popView.setBackgroundDrawable(dw);
        popView.setFocusable(true);
        popView.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popView.setOutsideTouchable(true);//点击外面不关闭
//        ClientApp.fitPopupWindowOverStatusBar(popView);
    }

    @OnClick({R.id.close_rela, R.id.modify_linear, R.id.delete_linear, R.id.copy_linear})
    void onBindClick(View v) {
        switch (v.getId()) {
            case R.id.close_rela:
                close();
                break;
            case R.id.modify_linear:
                if (modifyTreasuresBlock != null) {
                    modifyTreasuresBlock.onModifyTreasuresAction(v,Constant.ModifyAction.MODIFY);
                    close();
                }
                break;
            case R.id.delete_linear:
                if (modifyTreasuresBlock != null) {
                    modifyTreasuresBlock.onModifyTreasuresAction(v,Constant.ModifyAction.DELETE);
                    close();
                }
                break;
            case R.id.copy_linear:
                if (modifyTreasuresBlock != null) {
                    modifyTreasuresBlock.onModifyTreasuresAction(v,Constant.ModifyAction.COPY);
                    close();
                }
                break;
        }
    }

    public void show() {
        if (popView != null && !popView.isShowing()) {
            popView.showAtLocation(mActivity.findViewById(layoutId), Gravity.CENTER, 0, 0);
        }
        blurUtil.clickPopupWindow(relaPopView, backImg);

    }

    private void close() {
        blurUtil.clickClosePopupWindow(relaPopView, backImg);
        if (popView != null && popView.isShowing()) {
            popView.dismiss();
        }
    }

    private ModifyTreasuresBlock modifyTreasuresBlock;

    public void setModifyTreasuresBlock(ModifyTreasuresBlock modifyTreasuresBlock) {
        this.modifyTreasuresBlock = modifyTreasuresBlock;
    }

    public interface ModifyTreasuresBlock {
        void onModifyTreasuresAction(View view,String action);
    }

}
