package com.treasures.cn.utils.sqlite;

import android.database.Cursor;

import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.BusiException;

import java.util.List;

/**
 * @ProjectName: Treasures
 * @Package: com.scgj.treasures.utils.sqlite
 * @ClassName: DBCategotyManage
 * @Description: java类作用描述
 * @Author: WaveJuJu
 * @CreateDate: 2019-12-18 14:13
 */
public class DBCategotyManage implements ResultSetHandler<Integer> {
    public static DBCategotyManage INSTANCE = new DBCategotyManage();

    @Override
    public Integer handle(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseFactory.FIELD_CATEGORY_ID));
    }


    public static List<Integer> getAllCategoryTypeIdArr() throws BusiException {
        String sql = "select" + " distinct " + DataBaseFactory.FIELD_CATEGORY_ID
                + " from " + DataBaseFactory.TABLE_NAME
                + " where " + DataBaseFactory.FIELD_RECYCLE + " = ?";
        return SqlRunner.queryList(sql, new String[]{BusiConst.RecycleStatus.RECYCLE.toString()}, INSTANCE);
    }

}
