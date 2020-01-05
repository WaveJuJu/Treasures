package com.treasures.cn.handler;

import com.treasures.cn.o2o.ClientApp;
import com.treasures.cn.utils.BusiException;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.PrefUtils;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.handler
 * @ClassName: UserInfoHelp
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-23 19:11
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2019-12-23 19:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UserInfoHelp {
    public static void saveUser() {
        String jsonStr = null;
        try {
            jsonStr = MemoryData.serializeUserInfo(MemoryData.USER_INFO);
            new PrefUtils(ClientApp.mInstance).putString(Constant.SHARED_PREFERENCES_KEY.SP_TREASURES_USER_INFO_KEY, jsonStr);
        } catch (BusiException e) {
            e.printStackTrace();
        }
    }

    public static boolean isSuccessInvice() {
        return MemoryData.USER_INFO.getInviteCodeArr().size() >= 3;
    }
}
