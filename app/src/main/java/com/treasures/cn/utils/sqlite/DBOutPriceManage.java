package com.treasures.cn.utils.sqlite;

import android.database.Cursor;

import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.BusiException;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.utils.sqlite
 * @ClassName: DBInPriceManage
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-26 18:47
 * @UpdateUser: WaveJuJu
 * @UpdateDate: 2019-12-26 18:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DBOutPriceManage implements ResultSetHandler<Double>  {
    @Override
    public Double handle(Cursor cursor) throws BusiException {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseFactory.FIELD_PRICE));
    }

    /**
     * 获取所有出价总和
     *
     * @return
     * @throws BusiException
     */
    public static double getAllOutPrice() throws BusiException {
        String sql = "select sum "
                + "(" + DataBaseFactory.FIELD_PRICE + ")"
                + " from " + DataBaseFactory.TABLE_NAME
                + " where " + DataBaseFactory.FIELD_RECYCLE + " = '" +
                BusiConst.RecycleStatus.RECYCLE.toString() + "'";
        return SqlRunner.queryDouble(sql, null);
    }
}
