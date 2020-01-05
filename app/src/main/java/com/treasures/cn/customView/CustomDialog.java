package com.treasures.cn.customView;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.customView
 * @ClassName: CustomDialog
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-27 11:32
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2019-12-27 11:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    public void dismiss() {
        View view = getCurrentFocus();
        if(view instanceof TextView){
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.dismiss();
    }
}
