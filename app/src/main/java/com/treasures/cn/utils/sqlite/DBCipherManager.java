package com.treasures.cn.utils.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.treasures.cn.entity.FuzzySearch;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.handler.TreasuresHelp;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.BusiException;
import com.treasures.cn.utils.Cn2Spell;
import com.treasures.cn.utils.Constant;

import java.util.List;

public class DBCipherManager implements ResultSetHandler<Treasures> {
    public static DBCipherManager INSTANCE = new DBCipherManager();

    @Override
    public Treasures handle(Cursor cursor) throws BusiException {
//        String id = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseFactory.FIELD_ID));
//        String subTitle = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseFactory.FIELD_TITLE));
        int categoryTypeId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseFactory.FIELD_CATEGORY_ID));
        String dataJson = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseFactory.FIELD_DATA));
        return MemoryData.deserializeTreasures(dataJson);
    }

    /**
     * 保存对象到数据库
     *
     * @param treasures Treasures
     * @return isSuccess
     * @throws BusiException Exception
     */
    public static void saveTreasures(Treasures treasures) throws BusiException {
        String sql = "insert into " +
                DataBaseFactory.TABLE_NAME + "(" +
                DataBaseFactory.FIELD_ID + "," +
                DataBaseFactory.FIELD_TITLE + "," +
                DataBaseFactory.FIELD_TIME + "," +
                DataBaseFactory.FIELD_TAG + "," +
                DataBaseFactory.FIELD_KEY + "," +
                DataBaseFactory.FIELD_YEAR + "," +
                DataBaseFactory.FIELD_SIZE + "," +
                DataBaseFactory.FIELD_PRICE + "," +
                DataBaseFactory.FIELD_BUY_PRICE + "," +
                DataBaseFactory.FIELD_BUY_TIME + "," +
                DataBaseFactory.FIELD_CATEGORY_ID + "," +
                DataBaseFactory.FIELD_SOLD + "," +
                DataBaseFactory.FIELD_RECYCLE + "," +
                DataBaseFactory.FIELD_ENSHRINE + "," +
                DataBaseFactory.FIELD_UPLOAD + ", " +
                DataBaseFactory.FIELD_BOOL1 + ", " +
                DataBaseFactory.FIELD_BOOL2 + ", " +
                DataBaseFactory.FIELD_BOOL3 + ", " +
                DataBaseFactory.FIELD_BOOL4 + ", " +
                DataBaseFactory.FIELD_BOOL5 + ", " +
                DataBaseFactory.FIELD_STRING1 + ", " +
                DataBaseFactory.FIELD_STRING2 + ", " +
                DataBaseFactory.FIELD_STRING3 + ", " +
                DataBaseFactory.FIELD_STRING4 + ", " +
                DataBaseFactory.FIELD_STRING5 + ", " +
                DataBaseFactory.FIELD_DOUBLE1 + ", " +
                DataBaseFactory.FIELD_DOUBLE2 + ", " +
                DataBaseFactory.FIELD_DATE1 + ", " +
                DataBaseFactory.FIELD_DATE2 + ", " +
                DataBaseFactory.FIELD_DATA + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String dataJson = MemoryData.serializeTreasures(treasures);
        ContentValues values = new ContentValues();
        values.put(DataBaseFactory.FIELD_ID, treasures.getId());
        values.put(DataBaseFactory.FIELD_TITLE, treasures.getSubTitle());
        values.put(DataBaseFactory.FIELD_TIME, treasures.getCreateTime());
        String tag = treasures.getSubTitle()
                + treasures.getDescribe()
                + TreasuresHelp.getKeywordsStr(treasures.getKeywordsArr());
        values.put(DataBaseFactory.FIELD_TAG, Cn2Spell.getPinYin(tag));
        values.put(DataBaseFactory.FIELD_KEY, TreasuresHelp.getKeywordsStr(treasures.getKeywordsArr()));
        values.put(DataBaseFactory.FIELD_YEAR, treasures.getYear());
        values.put(DataBaseFactory.FIELD_SIZE
                , treasures.getSize());
        values.put(DataBaseFactory.FIELD_PRICE, treasures.getSellingPrice());
        values.put(DataBaseFactory.FIELD_BUY_PRICE, treasures.getBuyPrice());
        values.put(DataBaseFactory.FIELD_BUY_TIME, treasures.getBuyTime());
        values.put(DataBaseFactory.FIELD_CATEGORY_ID, treasures.getCategoryTypeId());
        values.put(DataBaseFactory.FIELD_SOLD, String.valueOf(treasures.getSoldType()));
        values.put(DataBaseFactory.FIELD_RECYCLE, BusiConst.RecycleStatus.RECYCLE.toString());
        values.put(DataBaseFactory.FIELD_ENSHRINE, treasures.isEnshrine());
        values.put(DataBaseFactory.FIELD_UPLOAD, treasures.isUpload());
        values.put(DataBaseFactory.FIELD_BOOL1, treasures.isAnyBool1());
        values.put(DataBaseFactory.FIELD_BOOL2, treasures.isAnyBool2());
        values.put(DataBaseFactory.FIELD_BOOL3, treasures.isAnyBool3());
        values.put(DataBaseFactory.FIELD_BOOL4, treasures.isAnyBool4());
        values.put(DataBaseFactory.FIELD_BOOL5, treasures.isAnyBool5());
        values.put(DataBaseFactory.FIELD_STRING1, treasures.getAnyString1());
        values.put(DataBaseFactory.FIELD_STRING2, treasures.getAnyString2());
        values.put(DataBaseFactory.FIELD_STRING3, treasures.getAnyString3());
        values.put(DataBaseFactory.FIELD_STRING4, treasures.getAnyString4());
        values.put(DataBaseFactory.FIELD_STRING5, treasures.getAnyString5());
        values.put(DataBaseFactory.FIELD_DOUBLE1, treasures.getAnyDouble1());
        values.put(DataBaseFactory.FIELD_DOUBLE2, treasures.getAnyDouble2());
        values.put(DataBaseFactory.FIELD_DATE1, treasures.getAnyDate1());
        values.put(DataBaseFactory.FIELD_DATE2, treasures.getAnyDate2());
        values.put(DataBaseFactory.FIELD_DATA, dataJson);
        long id = SqlRunner.insert(sql, DataBaseFactory.TABLE_NAME, values);
        if (id == -1) {
            BusiException exception = new BusiException();
            exception.setMsg("save treasures failure");
            throw exception;
        }
    }

    /**
     * 更新对象，保存数据库
     *
     * @param treasures Treasures
     * @throws BusiException .
     */
    public static void updateTreasuresSave(Treasures treasures) throws BusiException {
        Treasures oldTreasures = queryTreasuresById(treasures.getId());
        if (oldTreasures == null) {
            saveTreasures(treasures);
        } else {
            oldTreasures.setSubTitle(treasures.getSubTitle());
            updateDataByNo(treasures);
        }
    }

    /**
     * 通过id 在数据库查询对象
     *
     * @param id
     * @return
     * @throws BusiException
     */
    public static Treasures queryTreasuresById(String id) throws BusiException {
        String sql = "select * from " +
                DataBaseFactory.TABLE_NAME + " where " +
                DataBaseFactory.FIELD_ID + " = ?";
        return SqlRunner.query(sql, new String[]{id}, INSTANCE);
    }

    /**
     * Treasures收藏 操作
     *
     * @param treasuresId
     * @throws BusiException
     */
    public static Treasures treasuresEnshrine(String treasuresId, boolean isEnshrine) throws BusiException {
        Treasures treasures = queryTreasuresById(treasuresId);
        treasures.setEnshrine(isEnshrine);
        String dataJson = MemoryData.serializeTreasures(treasures);
        String sql = "update " +
                DataBaseFactory.TABLE_NAME + "set " +
                DataBaseFactory.FIELD_ENSHRINE + " = ? " +
                DataBaseFactory.FIELD_DATA + "= ? where" +
                DataBaseFactory.FIELD_ID + " = ?";
        ContentValues values = new ContentValues();
        values.put(DataBaseFactory.FIELD_ID, treasures.getId());
        values.put(DataBaseFactory.FIELD_ENSHRINE, treasures.isEnshrine());
        values.put(DataBaseFactory.FIELD_DATA, dataJson);
        boolean isSuccess = SqlRunner.update(sql, DataBaseFactory.TABLE_NAME, values, DataBaseFactory.FIELD_ID
                + " = ?", new String[]{treasures.getId()});
        if (!isSuccess) {
            BusiException exception = new BusiException();
            exception.setMsg("update enshrine" + DataBaseFactory.TABLE_NAME + " failure");
            throw exception;
        }
        return isSuccess ? queryTreasuresById(treasuresId) : null;
    }

    /**
     * 藏品删除与回收
     *
     * @param treasuresId
     * @param status
     * @throws BusiException
     */
    public static boolean treasuresDeleteRecycle(String treasuresId, BusiConst.RecycleStatus status) throws BusiException {
        String sql = "update " +
                DataBaseFactory.TABLE_NAME + "set " +
                DataBaseFactory.FIELD_RECYCLE + "= ? where" +
                DataBaseFactory.FIELD_ID + " = ?";
        ContentValues values = new ContentValues();
        values.put(DataBaseFactory.FIELD_ID, treasuresId);
        values.put(DataBaseFactory.FIELD_RECYCLE, status.toString());
        boolean isSuccess = SqlRunner.update(sql, DataBaseFactory.TABLE_NAME, values, DataBaseFactory.FIELD_ID
                + " = ?", new String[]{treasuresId});
        if (!isSuccess) {
            BusiException exception = new BusiException();
            exception.setMsg("update delete treasures failure");
            throw exception;
        }
        return isSuccess;
    }

    /**
     * 更新数据库
     *
     * @param treasures
     * @throws BusiException
     */
    public static void updateDataByNo(Treasures treasures) throws BusiException {
        String sql = "update " +
                DataBaseFactory.TABLE_NAME + "set " +
                DataBaseFactory.FIELD_TITLE + " = ?, " +
                DataBaseFactory.FIELD_TIME + " = ?, " +
                DataBaseFactory.FIELD_TAG + " = ?, " +
                DataBaseFactory.FIELD_KEY + " = ?, " +
                DataBaseFactory.FIELD_YEAR + " = ?, " +
                DataBaseFactory.FIELD_SIZE + " = ?, " +
                DataBaseFactory.FIELD_PRICE + " = ?, " +
                DataBaseFactory.FIELD_BUY_PRICE + " = ?, " +
                DataBaseFactory.FIELD_BUY_TIME + " = ?, " +
                DataBaseFactory.FIELD_CATEGORY_ID + " = ?, " +
                DataBaseFactory.FIELD_SOLD + " = ?, " +
                DataBaseFactory.FIELD_RECYCLE + " = ?, " +
                DataBaseFactory.FIELD_ENSHRINE + " = ?, " +
                DataBaseFactory.FIELD_UPLOAD + " = ?, " +
                DataBaseFactory.FIELD_BOOL1 + " = ?, " +
                DataBaseFactory.FIELD_BOOL2 + " = ?, " +
                DataBaseFactory.FIELD_BOOL3 + " = ?, " +
                DataBaseFactory.FIELD_BOOL4 + " = ?, " +
                DataBaseFactory.FIELD_BOOL5 + " = ?, " +
                DataBaseFactory.FIELD_STRING1 + " = ?, " +
                DataBaseFactory.FIELD_STRING2 + " = ?, " +
                DataBaseFactory.FIELD_STRING3 + " = ?, " +
                DataBaseFactory.FIELD_STRING4 + " = ?, " +
                DataBaseFactory.FIELD_STRING5 + " = ?, " +
                DataBaseFactory.FIELD_DOUBLE1 + " = ?, " +
                DataBaseFactory.FIELD_DOUBLE2 + " = ?, " +
                DataBaseFactory.FIELD_DATE1 + " = ?, " +
                DataBaseFactory.FIELD_DATE2 + " = ?, " +
                DataBaseFactory.FIELD_DATA + "= ? where " +
                DataBaseFactory.FIELD_ID + " = ?";
        String dataJson = MemoryData.serializeTreasures(treasures);
        ContentValues values = new ContentValues();
        values.put(DataBaseFactory.FIELD_ID, treasures.getId());
        values.put(DataBaseFactory.FIELD_TITLE, treasures.getSubTitle());
        values.put(DataBaseFactory.FIELD_TIME, treasures.getCreateTime());
        String tag = treasures.getSubTitle()
                + treasures.getDescribe()
                + TreasuresHelp.getKeywordsStr(treasures.getKeywordsArr());
        values.put(DataBaseFactory.FIELD_TAG, Cn2Spell.getPinYin(tag));
        values.put(DataBaseFactory.FIELD_KEY, TreasuresHelp.getKeywordsStr(treasures.getKeywordsArr()));
        values.put(DataBaseFactory.FIELD_YEAR, treasures.getYear());
        values.put(DataBaseFactory.FIELD_SIZE
                , treasures.getSize());
        values.put(DataBaseFactory.FIELD_PRICE, treasures.getSellingPrice());
        values.put(DataBaseFactory.FIELD_BUY_PRICE, treasures.getBuyPrice());
        values.put(DataBaseFactory.FIELD_BUY_TIME, treasures.getBuyTime());
        values.put(DataBaseFactory.FIELD_CATEGORY_ID, treasures.getCategoryTypeId());
        values.put(DataBaseFactory.FIELD_SOLD, treasures.getSoldType());
        values.put(DataBaseFactory.FIELD_RECYCLE, BusiConst.RecycleStatus.RECYCLE.toString());
        values.put(DataBaseFactory.FIELD_ENSHRINE, treasures.isEnshrine());
        values.put(DataBaseFactory.FIELD_UPLOAD, treasures.isUpload());
        values.put(DataBaseFactory.FIELD_BOOL1, treasures.isAnyBool1());
        values.put(DataBaseFactory.FIELD_BOOL2, treasures.isAnyBool2());
        values.put(DataBaseFactory.FIELD_BOOL3, treasures.isAnyBool3());
        values.put(DataBaseFactory.FIELD_BOOL4, treasures.isAnyBool4());
        values.put(DataBaseFactory.FIELD_BOOL5, treasures.isAnyBool5());
        values.put(DataBaseFactory.FIELD_STRING1, treasures.getAnyString1());
        values.put(DataBaseFactory.FIELD_STRING2, treasures.getAnyString2());
        values.put(DataBaseFactory.FIELD_STRING3, treasures.getAnyString3());
        values.put(DataBaseFactory.FIELD_STRING4, treasures.getAnyString4());
        values.put(DataBaseFactory.FIELD_STRING5, treasures.getAnyString5());
        values.put(DataBaseFactory.FIELD_DOUBLE1, treasures.getAnyDouble1());
        values.put(DataBaseFactory.FIELD_DOUBLE2, treasures.getAnyDouble2());
        values.put(DataBaseFactory.FIELD_DATE1, treasures.getAnyDate1());
        values.put(DataBaseFactory.FIELD_DATE2, treasures.getAnyDate2());
        values.put(DataBaseFactory.FIELD_DATA, dataJson);
        boolean isSuccess = SqlRunner.update(sql, DataBaseFactory.TABLE_NAME, values, DataBaseFactory.FIELD_ID
                + " = ?", new String[]{treasures.getId()});
        if (!isSuccess) {
            BusiException exception = new BusiException();
            exception.setMsg("update " + DataBaseFactory.TABLE_NAME + " failure");
            throw exception;
        }
    }

    /**
     * @param pageIndex 分页
     * @return
     * @method
     * @description 首页分页获取
     * @date: List<Treasures>
     * @author: WaveJuJu
     */
    public static List<Treasures> getPageTreasures(int pageIndex) throws BusiException {
        int starIndex = Constant.PAGE_COUNT * pageIndex;
        String sql = "select * from " +
                DataBaseFactory.TABLE_NAME + " where " +
                DataBaseFactory.FIELD_RECYCLE + " = ?" +
                " limit " + starIndex + "," + Constant.PAGE_COUNT;
        return SqlRunner.queryList(sql, new String[]{BusiConst.RecycleStatus.RECYCLE.toString()}, INSTANCE);
    }

    /**
     * 获取所有收藏
     * @return
     * @throws BusiException
     */
    public static List<Treasures> getCollectionTreasures() throws BusiException {
//        int starIndex = Constant.PAGE_COUNT * pageIndex;
        String sql = "select * from " +
                DataBaseFactory.TABLE_NAME + " where " +
                DataBaseFactory.FIELD_ENSHRINE + " = '1' and " +
                DataBaseFactory.FIELD_RECYCLE + " = ?";
//                + " limit " + starIndex + "," + Constant.PAGE_COUNT;
        return SqlRunner.queryList(sql, new String[]{BusiConst.RecycleStatus.RECYCLE.toString()}, INSTANCE);
    }

    /**
     * 获取所有 Treasures
     *
     * @return
     * @throws BusiException
     */
    public static List<Treasures> getAllTreasures() throws BusiException {
        String sql = "select * from " +
                DataBaseFactory.TABLE_NAME + " where " +
                DataBaseFactory.FIELD_RECYCLE + " = ?";
        return SqlRunner.queryList(sql, new String[]{BusiConst.RecycleStatus.RECYCLE.toString()}, INSTANCE);
    }

    /**
     * 获取所有 delete Treasures
     *
     * @return
     * @throws BusiException
     */
    public static List<Treasures> getAllDeleteTreasures(int pageIndex) throws BusiException {
        int starIndex = Constant.PAGE_COUNT * pageIndex;
        String sql = "select * from " +
                DataBaseFactory.TABLE_NAME + " where " +
                DataBaseFactory.FIELD_RECYCLE + " = ?";
//                + " limit " + starIndex + "," + Constant.PAGE_COUNT;
        return SqlRunner.queryList(sql, new String[]{BusiConst.RecycleStatus.DELETE.toString()}, INSTANCE);
    }

    /**
     * 清空回收站
     *
     * @return
     * @throws BusiException
     */
    public static boolean clearAllDeleteTreasure() throws BusiException {
        List<Treasures> deleteTreasures = getAllDeleteTreasures(0);
        boolean isSuccess = false;
        for (Treasures treasures : deleteTreasures) {
            String sql = "delete from " +
                    DataBaseFactory.TABLE_NAME + " where " +
                    DataBaseFactory.FIELD_ID + " = ?";
            isSuccess = SqlRunner.deleteTableData(sql, DataBaseFactory.TABLE_NAME
                    , DataBaseFactory.FIELD_ID + " = ?"
                    , new String[]{treasures.getId()});
            if (!isSuccess) {
                BusiException exception = new BusiException();
                exception.setMsg("delete " + DataBaseFactory.TABLE_NAME + " failure");
                throw exception;
            }
        }
        return isSuccess;
//        String sql = "delete * from " +
//                DataBaseFactory.TABLE_NAME + " where " +
//                DataBaseFactory.FIELD_RECYCLE + " = ?";
//        boolean isSuccess = SqlRunner.deleteTableData(sql, DataBaseFactory.TABLE_NAME
//                , DataBaseFactory.FIELD_RECYCLE + " = ?"
//                , new String[]{BusiConst.RecycleStatus.DELETE.toString()});
//        if (!isSuccess) {
//            BusiException exception = new BusiException();
//            exception.setMsg("delete " + DataBaseFactory.TABLE_NAME + " failure");
//            throw exception;
//        }
//        return isSuccess;
    }

    /**
     * 模糊搜索
     *
     * @return List<Treasures>
     */
    public static List<Treasures> fuzzySearchTreasures(FuzzySearch fuzzySearch, int pageIndex) throws BusiException {
        String andSql = "";
        if (!fuzzySearch.getSearchContent().isEmpty()) {
            andSql += " and " + DataBaseFactory.FIELD_TAG +
                    " like " + "'%" + fuzzySearch.getSearchContent() + "%' ";
        }

        if (!fuzzySearch.getKeyword().isEmpty()) {
            andSql += " and " + DataBaseFactory.FIELD_KEY +
                    " like " + "'%" + fuzzySearch.getKeyword() + "%' ";
        }
        if (fuzzySearch.getSoldType() > -2) {
            andSql += " and " + DataBaseFactory.FIELD_SOLD + " = " + fuzzySearch.getSoldType();
        }

        if (fuzzySearch.getCategoryTypeId() > 0) {
            andSql += " and " + DataBaseFactory.FIELD_CATEGORY_ID + " = " + fuzzySearch.getCategoryTypeId();
        }

        if (fuzzySearch.getYearStatus() >= 0) {
            andSql += " order by " + DataBaseFactory.FIELD_YEAR + (fuzzySearch.getYearStatus() == 0 ? "" : " desc ");
        } else if (fuzzySearch.getPriceStatus() >= 0) {
            andSql += " order by " + DataBaseFactory.FIELD_BUY_PRICE + (fuzzySearch.getPriceStatus() == 0 ? "" : " desc ");
        }

        if (TextUtils.isEmpty(andSql)) {
            andSql += " order by " + DataBaseFactory.FIELD_ID + "";
        }
        String sql = "select * from " +
                DataBaseFactory.TABLE_NAME + " where " +
                DataBaseFactory.FIELD_RECYCLE + " = ? " + andSql;
        return SqlRunner.queryList(sql, new String[]{BusiConst.RecycleStatus.RECYCLE.toString()}, INSTANCE);
    }
}
