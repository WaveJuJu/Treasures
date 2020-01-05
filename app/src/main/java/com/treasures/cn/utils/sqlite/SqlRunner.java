package com.treasures.cn.utils.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.treasures.cn.utils.BusiException;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

public class SqlRunner {
    //查询Bool
    public static Boolean queryBool(String sql, String[] values) throws BusiException {
        return queryLite(sql, values, new ResultSetHandler<Boolean>() {
            @Override
            public Boolean handle(Cursor cursor) {
                return cursor.getInt(0) == 1;
            }
        });
    }

    //查询Int
    public static Integer queryInt(String sql, String[] values) throws BusiException {
        return queryLite(sql, values, new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(Cursor cursor) {
                return cursor.getInt(0);
            }
        });
    }

    //查询Double
    public static Double queryDouble(String sql, String[] values) throws BusiException {
        return queryLite(sql, values, new ResultSetHandler<Double>() {
            @Override
            public Double handle(Cursor cursor) {
                return cursor.getDouble(0);
            }
        });
    }

    //查询String
    public static String queryString(String sql, String[] values) throws BusiException {
        return queryLite(sql, values, new ResultSetHandler<String>() {
            @Override
            public String handle(Cursor cursor) {
                return cursor.getString(0);
            }
        });
    }

    //查询一个对象
    private static <T> T queryLite(String sql, String[] values, ResultSetHandler<T> set) throws BusiException {
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        Log.v("SQLite", sql);
        Cursor cursor = db.rawQuery(sql, values);
        try {
            if (cursor.moveToNext()) {
                return set.handle(cursor);
            }
        } finally {
            DataBaseFactory.close(cursor);
        }
        return null;
    }

    //查询一个对象
    public static <T> T query(String sql, String[] values, ResultSetHandler<T> set) throws BusiException {
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        Log.v("SQLite", sql);
        Cursor cursor = db.rawQuery(sql, values);
        try {
            if (cursor.moveToNext()) {
                return set.handle(cursor);
            }
        } finally {
            DataBaseFactory.close(cursor);
        }
        return null;
    }

    //查询对象列表
    public static <T> ArrayList<T> queryList(String sql, String[] values, ResultSetHandler<T> set) throws BusiException {
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        ArrayList<T> data = new ArrayList<>();
        Log.v("SQLite", sql);
        Cursor cursor = db.rawQuery(sql, values);
        try {
            while (cursor.moveToNext()) {
                T t = set.handle(cursor);
                data.add(t);
            }
        } finally {
            DataBaseFactory.close(cursor);
        }
        if (data.isEmpty()) {
            return null;
        }
        return data;
    }

    //更新
    public static boolean update(String sql, String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        Log.v("SQLite", sql);
        return db.update(table, values, whereClause, whereArgs) >= 1;
    }

    //检查和插入记录
    public static long checkInsert(String checkSql, String insertSql, String table, String[] checkValues, ContentValues values) throws SQLException {
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        Log.v("SQLite", checkSql);
        long id = -1;
        Cursor cursor = db.rawQuery(checkSql, checkValues);
        try {
            if (cursor.moveToNext()) {
                id = cursor.getLong(0);
            }
            if (id != -1) {
                Log.v("SQLite", "records exist");
                return id;
            }
        } finally {
            DataBaseFactory.close(cursor);
        }
        Log.v("SQLite", insertSql);
        id = db.insertOrThrow(table, null, values);
        return id;
    }

    //插入返回主键ID
    public static long insert(String insertSql, String table, ContentValues values) throws SQLException {
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        Log.v("SQLite", insertSql);
        return db.insertOrThrow(table, null, values);
    }

    // 批处理更新
    public static void batchUpdate(String sql, String table, ContentValues[] valuesArr, String whereClause, String[][] whereArgsArr) {
        if (whereArgsArr.length == 0) {
            return;
        }
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        db.beginTransaction();
        try {
            for (int i = 0; i < whereArgsArr.length; i++) {
                ContentValues val = valuesArr != null ? valuesArr[i] : null;
                String[] whereArgs = whereArgsArr[i];
                Log.v("SQLite", sql);
                db.update(table, val, whereClause, whereArgs);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // 更新簇集
    public static void updateCluster(String[] sqls, String[] tableArr, ContentValues[] valuesArr
            , String[] whereClauseArr, String[][] whereArgsArr) {
        if (sqls.length == 0 || whereArgsArr.length == 0) {
            return;
        }
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        db.beginTransaction();
        try {
            for (int i = 0; i < sqls.length; i++) {
                String sql = sqls[i];
                String table = tableArr[i];
                ContentValues values = valuesArr[i];
                String whereClause = whereClauseArr[i];
                String[] whereArgs = whereArgsArr[i];
                Log.v("SQLite", sql);
                db.update(table, values, whereClause, whereArgs);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // 更新簇集
    public static long updateClusterAndInsert(String[] sqls, String[] tableArr, ContentValues[] valuesArr
            , String[] whereClauseArr, String[][] whereArgsArr) throws SQLException {
        long id = -1;
        if (sqls.length == 0 || whereArgsArr.length == 0) {// 参数是空
            return id;
        }
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        db.beginTransaction();
        try {
            for (int i = 0; i < sqls.length - 1; i++) {
                String sql = sqls[i];
                String table = tableArr[i];
                ContentValues values = valuesArr[i];
                String whereClause = whereClauseArr[i];
                String[] whereArgs = whereArgsArr[i];
                Log.v("SQLite", sql);
                db.update(table, values, whereClause, whereArgs);
            }
            Log.v("SQLite", sqls[sqls.length - 1]);
            id = db.insertOrThrow(tableArr[sqls.length - 1], null, valuesArr[sqls.length - 1]);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return id;
    }

    // 批处理检查更新
    public static long[] batchCheckInsert(String checkSql, String insertSql, String table
            , String[][] checkValuesArr, ContentValues[] valuesArr) throws SQLException {
        if (valuesArr.length == 0 || checkValuesArr.length != valuesArr.length) {
            return null;
        }
        long[] ids = new long[checkValuesArr.length];
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        db.beginTransaction();
        try {
            for (int i = 0; i < checkValuesArr.length; i++) {
                Log.v("SQLite", checkSql);
                long id = -1;
                Cursor cursor = db.rawQuery(checkSql, checkValuesArr[i]);
                try {
                    if (cursor.moveToNext()) {
                        id = cursor.getLong(0);
                    }
                    if (id != -1) {
                        Log.v("SQLite", "records exist");
                        ids[i] = id;
                        continue;
                    }
                } finally {
                    DataBaseFactory.close(cursor);
                }
                Log.v("SQLite", insertSql);
                id = db.insertOrThrow(table, null, valuesArr[i]);
                ids[i] = id;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return ids;
    }

    // 批处理检查更新
    public static long[] batchCheckUpdateAndInsert(String checkUpdateSql, String updateSql,
                                                   String checkInsertSql, String insertSql,
                                                   String table, String[][] checkUpdateValuesArr,
                                                   ContentValues[] updateValuesArr, String whereClause,
                                                   String[][] updateWhereArgsArr, String[][] checkInsertValuesArr,
                                                   ContentValues[] insertValuesArr) throws SQLException {
        if (updateWhereArgsArr.length == 0 || updateValuesArr != null && updateValuesArr.length != updateWhereArgsArr.length ||
                insertValuesArr.length == 0 || checkInsertValuesArr.length != insertValuesArr.length) {
            return null;
        }
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        db.beginTransaction();
        try {
            //batch check update
            for (int i = 0; i < checkUpdateValuesArr.length; i++) {
                Log.v("SQLite", checkUpdateSql);
                Cursor cursor = db.rawQuery(checkUpdateSql, checkUpdateValuesArr[i]);
                try {
                    long id = -1;
                    if (cursor.moveToNext()) {
                        id = cursor.getLong(0);
                    }
                    if (id == -1) {
                        Log.v("SQLite", "records not exist");
                        return null;
                    }
                } finally {
                    DataBaseFactory.close(cursor);
                }
                Log.v("SQLite", updateSql);
                ContentValues updateValues = updateValuesArr != null ? updateValuesArr[i] : null;
                db.update(table, updateValues, whereClause, updateWhereArgsArr[i]);
            }
            //batch check insert
            long[] ids = new long[checkInsertValuesArr.length];
            for (int i = 0; i < checkInsertValuesArr.length; i++) {
                Log.v("SQLite", checkInsertSql);
                long id = -1;
                Cursor cursor = db.rawQuery(checkInsertSql, checkInsertValuesArr[i]);
                try {
                    if (cursor.moveToNext()) {
                        id = cursor.getLong(0);
                    }
                    if (id != -1) {
                        Log.v("SQLite", "records exist");
                        ids[i] = id;
                        continue;
                    }
                } finally {
                    DataBaseFactory.close(cursor);
                }
                Log.v("SQLite", insertSql);
                id = db.insertOrThrow(table, null, insertValuesArr[i]);
                ids[i] = id;
            }
            db.setTransactionSuccessful();
            return ids;
        } finally {
            db.endTransaction();
        }
    }

    // 批处理检查更新簇集
    public static long[] checkInsertCluster(String[] checkSqls, String[] insertSqls, String[] tables
            , String[][] checkValuesArr, ContentValues[] valuesArr) throws SQLException {
        if (valuesArr.length == 0 || checkValuesArr.length != valuesArr.length) {
            return null;
        }
        long[] ids = new long[checkValuesArr.length];
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        db.beginTransaction();
        try {
            for (int i = 0; i < checkValuesArr.length; i++) {
                String checkSql = checkSqls[i];
                Log.v("SQLite", checkSql);
                long id = -1;
                Cursor cursor = db.rawQuery(checkSql, checkValuesArr[i]);
                try {
                    if (cursor.moveToNext()) {
                        id = cursor.getLong(0);
                    }
                    if (id != -1) {
                        Log.v("SQLite", "records exist");
                        ids[i] = id;
                        continue;
                    }
                } finally {
                    DataBaseFactory.close(cursor);
                }
                String insertSql = insertSqls[i];
                Log.v("SQLite", insertSql);
                id = db.insertOrThrow(tables[i], null, valuesArr[i]);
                ids[i] = id;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return ids;
    }

    //刷新表数据
    public static long[] flushTable(String deleteSql, String insertSql, String table, String delWhereClause
            , String[] delWhereArgs, ContentValues[] valuesArr) throws SQLException {
        if (valuesArr.length == 0) {
            return null;
        }
        long[] ids = new long[valuesArr.length];
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        db.beginTransaction();
        try {
            Log.v("SQLite", deleteSql);
            db.delete(table, delWhereClause, delWhereArgs);
            for (int i = 0; i < valuesArr.length; i++) {
                ContentValues values = valuesArr[i];
                Log.v("SQLite", insertSql);
                ids[i] = db.insertOrThrow(table, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return ids;
    }

    public static boolean deleteTableData(String deleteSql, String table, String delWhereClause, String[] delWhereArgs) throws SQLException {
        SQLiteDatabase db = DataBaseFactory.DATA_BASE;
        db.beginTransaction();

        try {
            Log.v("SQLite", deleteSql);
            int count = db.delete(table, delWhereClause, delWhereArgs);
            db.setTransactionSuccessful();
            return count > 0;
        } finally {
            db.endTransaction();
        }
    }
}
