package com.treasures.cn.handler;

import com.treasures.cn.entity.Treasures;
import com.treasures.cn.utils.comm.Asyc;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.handler
 * @ClassName: ScreeningTreasuresHandler
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-07 11:38
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2019-12-07 11:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ScreeningTreasuresHandler {
    private final static Asyc ASYC = new Asyc(ScreeningTreasuresHandler.class);

    public static List<Treasures> getTreasurArr(int pageIndex,int categoryId){
        List<Treasures> treasures = new ArrayList<>();

        return treasures;
    }


}
