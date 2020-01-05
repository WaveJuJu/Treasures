package com.treasures.cn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.Time;

import java.util.Map;

/**
 * 封装SharedPreferences本地保存
 */
public class PrefUtils {

    private Context mContext;
    private static SharedPreferences sDefaultSP;

    private static final String DEFAULT_NAME = "treasures_config";

    private static SharedPreferences getSP(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public PrefUtils(Context context) {
        this.mContext = context;
        getSP(context);
    }

    private static SharedPreferences getSP(Context context) {
        if (sDefaultSP == null) {
            return getSP(context, DEFAULT_NAME);
        }
        return sDefaultSP;
    }

    private static Editor getEditor(Context context, String name) {
        return getSP(context, name).edit();
    }

    private static Editor getEditor(Context context) {
        return getSP(context).edit();
    }

    public static void setsDefaultSP(SharedPreferences sDefaultSP) {
        PrefUtils.sDefaultSP = sDefaultSP;
    }

    public void putBoolean(String key, boolean value) {
        getEditor(mContext).putBoolean(key, value).commit();
    }

    public void putFloat(String key, float value) {
        getEditor(mContext).putFloat(key, value).commit();
    }

    public void putInt(String key, int value) {
        getEditor(mContext).putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        getEditor(mContext).putLong(key, value).commit();
    }

    public void putString(String key, String value) {
        getEditor(mContext).putString(key, value).commit();
    }

    public Map<String, ?> getAll() {
        return getSP(mContext).getAll();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getSP(mContext).getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return getSP(mContext).getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return getSP(mContext).getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return getSP(mContext).getLong(key, defValue);
    }

    public String getString(String key, String defValue) {
        return getSP(mContext).getString(key, defValue);
    }

    public void putBoolean(String name, String key, boolean value) {
        getEditor(mContext, name).putBoolean(key, value).commit();
    }

    public void putFloat(String name, String key, float value) {
        getEditor(mContext, name).putFloat(key, value).commit();
    }

    public void putInt(String name, String key, int value) {
        getEditor(mContext, name).putInt(key, value).commit();
    }

    public void putLong(String name, String key, long value) {
        getEditor(mContext, name).putLong(key, value).commit();
    }

    public void putString(String name, String key, String value) {
        getEditor(mContext, name).putString(key, value).commit();
    }

    public Map<String, ?> getAll(String name) {
        return getSP(mContext,name).getAll();
    }

    public boolean getBoolean(String key, boolean defValue, String name) {
        return getSP(mContext, name).getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue, String name) {
        return getSP(mContext, name).getFloat(key, defValue);
    }

    public int getInt(String key, int defValue, String name) {
        return getSP(mContext, name).getInt(key, defValue);
    }

    public long getLong(String key, long defValue, String name) {
        return getSP(mContext, name).getLong(key, defValue);
    }

    public String getString(String key, String defValue, String name) {
        return getSP(mContext, name).getString(key, defValue);
    }

    public static String getSystemTime() {
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month + 1;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        String tag = "AM";
        if (hour >= 12) {
            tag = "PM";
        }
        String time = date + "/" + month + "/" + year + " " + hour + ":"
                + minute + " " + tag;
        return time;
    }
}
