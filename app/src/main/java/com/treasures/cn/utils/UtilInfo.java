package com.treasures.cn.utils;

import android.content.Context;
import android.os.Build;

/**
 * 手机信息
 * <p>
 * Created by Bamboy on 2017/3/28.
 */
public class UtilInfo {
    /**
     * 手机屏幕宽度
     */
    private int phoneWidth = 0;
    /**
     * 手机屏幕高度
     */
    private int phoneHeigh = 0;
    /**
     * 手机SDK版本号
     */
    private int phoneSDK = 0;

    /**
     * 初始化手机信息
     * @param context
     */
    public void initPhoneInfo(Context context){
        phoneWidth = ScreenUtils.getWidth(context);
        phoneHeigh = ScreenUtils.getHeight(context);
        phoneSDK = Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机屏幕宽度
     *
     * @return
     */
    public int getPhoneWidth() {
        return phoneWidth;
    }

    /**
     * 设置手机屏幕宽度
     *
     * @param phoneWidth
     */
    public void setPhoneWidth(int phoneWidth) {
        this.phoneWidth = phoneWidth;
    }

    /**
     * 获取手机屏幕高度
     *
     * @return
     */
    public int getPhoneHeigh() {
        return phoneHeigh;
    }

    /**
     * 设置手机屏幕高度
     *
     * @param phoneHeigh
     */
    public void setPhoneHeigh(int phoneHeigh) {
        this.phoneHeigh = phoneHeigh;
    }

    /**
     * 获取手机SDK版本号
     *
     * @return
     */
    public int getPhoneSDK() {
        return phoneSDK;
    }

    /**
     * 设置手机SDK版本号
     *
     * @param phoneSDK
     */
    public void setPhoneSDK(int phoneSDK) {
        this.phoneSDK = phoneSDK;
    }
}
