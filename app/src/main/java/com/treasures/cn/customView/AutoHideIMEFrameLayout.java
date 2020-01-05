package com.treasures.cn.customView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.customView
 * @ClassName: AutoHideIMEFrameLayout
 * @Description: 隐藏键盘 -- 通常在我们使用EditText输入内容时，默认会唤起我们系统的键盘，但是在输入完之后，这些键盘收起比较困难。
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-09 11:18
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2019-12-09 11:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AutoHideIMEFrameLayout extends FrameLayout {

    public AutoHideIMEFrameLayout(Context context) {
        super(context);
    }

    public AutoHideIMEFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHideIMEFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Context context = getContext();
            if (context == null || !(context instanceof Activity)) {
                return super.dispatchTouchEvent(ev);
            }

            Activity activity = (Activity) context;
            View focusView = activity.getCurrentFocus();

            if (focusView != null && shouldHideInputMethod(focusView, ev)) {
                hideInputMethod(focusView);
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    private boolean shouldHideInputMethod(View focusView, MotionEvent event) {
        Rect rect = new Rect();
        focusView.getHitRect(rect);
        return !rect.contains((int) event.getX(), (int) event.getY());
    }

    private void hideInputMethod(View currentFocus) {
        if (currentFocus == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) currentFocus.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
