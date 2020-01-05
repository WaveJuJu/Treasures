package com.treasures.cn.handler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.treasures.cn.entity.UserInfo;
import com.treasures.cn.utils.BusiException;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.DateTimeUtil;
import com.treasures.cn.utils.InviteCodeUtils;
import com.treasures.cn.utils.PrefUtils;
import com.treasures.cn.utils.sqlite.DataBaseFactory;

import java.util.Date;

public class DataInitializer {

    public static void init(Context context) {
        //加载数据库数据
        DataBaseFactory.init(context);


        try {
            initUser(context);
            MemoryData.categories = MemoryData.deserializaCategoryList();
            initCategoryDate(context);
        } catch (BusiException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("LongLogTag")
    private static void initUser(Context context) throws BusiException {
        String userInfoJson = new PrefUtils(context).getString(Constant.SHARED_PREFERENCES_KEY.SP_TREASURES_USER_INFO_KEY, "");
        if (!TextUtils.isEmpty(userInfoJson)) {
            Log.d("DataInitializer -userInfoJson- > ", userInfoJson);
            MemoryData.USER_INFO = MemoryData.deserializeUserInfo(userInfoJson);
        }
        initUserData();
    }

    @SuppressLint("LongLogTag")
    private static void initCategoryDate(Context context) throws BusiException {
        String categoryTypesJson = new PrefUtils(context).getString(Constant.SHARED_PREFERENCES_KEY.SP_CATEGORY_TYPE_INFO_KEY, "");
        if (!TextUtils.isEmpty(categoryTypesJson)) {
            Log.d("DataInitializer -categoryTypesJson- > ", categoryTypesJson);
            MemoryData.selectedCategoryTypes = MemoryData.deserializaSelectedCategoryTypesList(categoryTypesJson);
        }
    }

    private static void initUserData() {
        boolean isSave = false;
        if (MemoryData.USER_INFO == null) {
            MemoryData.USER_INFO = new UserInfo();
        }
        if (MemoryData.USER_INFO.getUserId() <= 0 || String.valueOf(MemoryData.USER_INFO.getUserId()).length() < 9) {
            if (!isSave) {
                isSave = true;
            }
            MemoryData.USER_INFO.setUserId(DateTimeUtil.createUserId());
        }
        if (TextUtils.isEmpty(MemoryData.USER_INFO.getCreateTime())) {
            if (!isSave) {
                isSave = true;
            }
            MemoryData.USER_INFO.setCreateTime(DateTimeUtil.format(new Date()));
        }
        if (TextUtils.isEmpty(MemoryData.USER_INFO.getInviteCode())) {
            if (!isSave) {
                isSave = true;
            }
            MemoryData.USER_INFO.setInviteCode(InviteCodeUtils.idToCode(MemoryData.USER_INFO.getUserId()));
        }
        if (isSave) {
            UserInfoHelp.saveUser();
        }
    }


}
