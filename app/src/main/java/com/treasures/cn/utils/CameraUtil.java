package com.treasures.cn.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.utils
 * @ClassName: CameraUtil
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2020-01-03 18:19
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2020-01-03 18:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CameraUtil {
    public static Uri getOutputMediaFileUri(Context context) {
        File mediaFile = null;
        String cameraPath;
        try {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
            mediaFile = new File(mediaStorageDir.getPath()
                    + File.separator
                    + "Pictures/temp.jpg");//注意这里需要和filepaths.xml中配置的一样
            cameraPath = mediaFile.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// sdk >= 24  android7.0以上
            Uri contentUri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".fileprovider",//与清单文件中android:authorities的值保持一致
//                    ${applicationId}.fileprovider
                    mediaFile);//FileProvider方式或者ContentProvider。也可使用VmPolicy方式
            return contentUri;

        } else {
            return Uri.fromFile(mediaFile);//或者 Uri.isPaise("file://"+file.toString()

        }
    }
}
