package com.treasures.cn.utils;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.utils
 * @ClassName: BusiConst
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-07 12:03
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2019-12-07 12:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BusiConst {
    public enum RecycleStatus {
        //有效数据    删除
        RECYCLE,DELETE,CLEAR
    }
    public enum SellingStatus{
        //升序    降序        空
        ASCENDING,DESCENDING,EMPTY
    }

    public enum RegisteredStatus{
        REGISTERED,MODIFY
    }

    public enum ModifyType{
        NEW_ADD,COPY,MODIFY
    }

    public enum ShareType{
        WECHAT_FRIENDS,WECHAT_CIRCLE
    }


}
