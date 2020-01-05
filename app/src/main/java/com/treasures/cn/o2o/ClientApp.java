package com.treasures.cn.o2o;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;
import android.widget.PopupWindow;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.treasures.cn.handler.DataInitializer;
import com.treasures.cn.o2o.activity.MainActivity;
import com.treasures.cn.utils.UtilBox;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.UMShareAPI;

import java.lang.reflect.Field;

public class ClientApp extends Application {
    public static ClientApp mInstance;
    private boolean is9Image;
    public ClientApp() {
        super();
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        DataInitializer.init(this);
        UtilBox.getBox().initBox();
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
        UMConfigure.init(this,"5e0068fc570df337eb000148"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        UMShareAPI.get(this);
        setIs9Image(false);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        if (Build.VERSION.SDK_INT >= 18){
            builder.detectFileUriExposure();
        }
        StrictMode.setVmPolicy(builder.build());

        CrashReport.initCrashReport(getApplicationContext(), "66cc05226f", false);
        Beta.canShowUpgradeActs.add(MainActivity.class);
    }

    /**
     * popwindow覆盖到顶部
     *
     * @param popupWindow
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void fitPopupWindowOverStatusBar(PopupWindow popupWindow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(popupWindow, true);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isIs9Image() {
        return is9Image;
    }

    public void setIs9Image(boolean is9Image) {
        this.is9Image = is9Image;
    }


    public static ClientApp getInstance() {
        return mInstance;
    }

    public static void setInstance(ClientApp mInstance) {
        ClientApp.mInstance = mInstance;
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public static String getVersion() {
        try {
            PackageManager manager = getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getInstance().getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前Build版本号
     *
     */
    public static String getBuildVersion() {
        try {
            PackageManager manager = getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getInstance().getPackageName(), 0);
            return Integer.toString(info.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
