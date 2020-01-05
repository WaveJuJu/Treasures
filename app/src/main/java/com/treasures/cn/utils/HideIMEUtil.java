package com.treasures.cn.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.treasures.cn.customView.AutoHideIMEFrameLayout;

import androidx.fragment.app.Fragment;

/**
 * @Description: 隐藏键盘工具类
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-09 11:22
 * @Version: 1.0
 */
public class HideIMEUtil {
    /**
     * @param activity 当前显示界面
     * @description 描述一下方法的作用:activity 添加当子View添加到键盘隐藏的View
     * @author: WaveJuJu
     */
    public static void wrap(Activity activity) {
        ViewGroup contentParent = activity.findViewById(android.R.id.content);
        contentParent.post(() -> wrap(contentParent));
    }

    /**
     * @param fragment 当前显示界面
     * @description 描述一下方法的作用:fragment 添加当子View添加到键盘隐藏的View
     * @author: WaveJuJu
     */
    public static void wrap(Fragment fragment) {
        ViewGroup contentParent = (ViewGroup) fragment.getView().getParent();
        contentParent.post(() -> wrap(contentParent));
    }

    /**
     * @param contentParent 需要添加的View
     * @description 描述一下方法的作用:将contentParent添加当子View添加到键盘隐藏的View
     * @author: WaveJuJu
     */
    public static void wrap(ViewGroup contentParent) {
        View content = contentParent.getChildAt(0);
        contentParent.removeView(content);
        if (content instanceof AutoHideIMEFrameLayout) {
            return;
        }
        ViewGroup.LayoutParams p = content.getLayoutParams();
        AutoHideIMEFrameLayout layout = new AutoHideIMEFrameLayout(content.getContext());
        layout.addView(content);
        contentParent.addView(layout, new ViewGroup.LayoutParams(p.width, p.height));
    }
}
