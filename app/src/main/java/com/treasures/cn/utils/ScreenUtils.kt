package com.treasures.cn.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * 屏幕相关的操作类
 */
object ScreenUtils {
    /**
     * 获取宽度
     *
     * @param mContext 上下文
     * @return 宽度值，px
     */
    @JvmStatic
    fun getWidth(mContext: Context): Int {
        val displayMetrics = DisplayMetrics()
        (mContext.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    /**
     * 获取高度
     *
     * @param mContext 上下文
     * @return 高度值，px
     */
    @JvmStatic
    fun getHeight(mContext: Context): Int {
        val displayMetrics = DisplayMetrics()
        (mContext.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    /**
     * 获取屏幕密度
     *
     * @param mContext 上下文
     * @return 密度
     */
    @JvmStatic
    fun getDensity(mContext: Context): Float {
        val displayMetrics = DisplayMetrics()
        (mContext.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.density
    }
    /**
     * 是否在屏幕右侧
     *
     * @param mContext 上下文
     * @param xPos     位置的x坐标值
     * @return true：是。
     */
    @JvmStatic
    fun isInRight(mContext: Context, xPos: Int): Boolean {
        return xPos > getWidth(mContext) / 2
    }

    /**
     * 是否在屏幕左侧
     *
     * @param mContext 上下文
     * @param xPos     位置的x坐标值
     * @return true：是。
     */
    @JvmStatic
    fun isInLeft(mContext: Context, xPos: Int): Boolean {
        return xPos < getWidth(mContext) / 2
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    @JvmStatic
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.applicationContext.resources
                .displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    @JvmStatic
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.applicationContext.resources
                .displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    @JvmStatic
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.applicationContext.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    @JvmStatic
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.applicationContext.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}