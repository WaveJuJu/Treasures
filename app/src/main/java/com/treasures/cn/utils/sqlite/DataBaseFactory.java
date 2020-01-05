package com.treasures.cn.utils.sqlite;

import android.content.Context;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class DataBaseFactory extends SQLiteOpenHelper {
    private static DataBaseFactory INSTANCE;
    static SQLiteDatabase DATA_BASE;
    //数据库名字
    private static final String DB_NAME = "treasures_db";
    //数据库密码
    static final String DB_PASSWORD = "treasures";
    //数据库版本
    private static final int DB_VERSION = 1;
    //数据库表名称
    static final String TABLE_NAME = "treasures_data"; // 表名

    static final String FIELD_ID = "f_id";//数据id时间戳
    static final String FIELD_TITLE = "f_title";//标题name
    static final String FIELD_TIME = "f_create_time";//创建时间
    static final String FIELD_DATA = "f_data";//对象json
    static final String FIELD_TAG = "f_tag";//快速搜索标签
    static final String FIELD_KEY = "f_key";//关键词
    static final String FIELD_YEAR = "f_year";//藏品年份
    static final String FIELD_SIZE = "f_size";//藏品尺寸
    static final String FIELD_PRICE = "f_price";//藏品购买
    static final String FIELD_BUY_PRICE = "f_buy_price";//藏品购买价格
    static final String FIELD_BUY_TIME = "f_buy_time";//藏品购买时间
    static final String FIELD_CATEGORY_ID = "f_gategory_id";//藏品类目id
    static final String FIELD_SOLD = "f_sold";//已售
    static final String FIELD_RECYCLE = "f_recycle";//回收状态
    static final String FIELD_ENSHRINE = "f_enshrine";//收藏状态
    static final String FIELD_UPLOAD = "f_upload";//是否已上传云端
    static final String FIELD_BOOL1 = "f_bool1";
    static final String FIELD_BOOL2 = "f_bool2";
    static final String FIELD_BOOL3 = "f_bool3";
    static final String FIELD_BOOL4 = "f_bool4";
    static final String FIELD_BOOL5 = "f_bool5";
    static final String FIELD_STRING1 = "f_string1";
    static final String FIELD_STRING2 = "f_string2";
    static final String FIELD_STRING3 = "f_string3";
    static final String FIELD_STRING4 = "f_string4";
    static final String FIELD_STRING5 = "f_string5";
    static final String FIELD_DOUBLE1 = "f_double1";
    static final String FIELD_DOUBLE2 = "f_double2";
    static final String FIELD_DATE1 = "f_date1";
    static final String FIELD_DATE2 = "f_date2";

    public static void init(Context context) {
        INSTANCE = new DataBaseFactory(context);
        DATA_BASE = INSTANCE.getWritableDatabase(DB_PASSWORD);
    }

    private DataBaseFactory(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    private DataBaseFactory(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        SQLiteDatabase.loadLibs(context);
    }

    public static void close(Cursor cursor) {
        if (cursor == null) {
            return;
        }
        cursor.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表
        createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        createTable(sqLiteDatabase);
    }

    /**
     * create table
     */
    private void createTable(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE_NAME + " ("
                + FIELD_ID + " integer primary key autoincrement,"
                + FIELD_TITLE + " text not null,"
                + FIELD_TIME + " text not null,"
                + FIELD_DATA + " text not null,"
                + FIELD_TAG + " text,"
                + FIELD_KEY + " text,"
                + FIELD_YEAR + " text,"
                + FIELD_SIZE + " text,"
                + FIELD_PRICE + " double,"
                + FIELD_BUY_PRICE + " double,"
                + FIELD_BUY_TIME + " text,"
                + FIELD_CATEGORY_ID + " integer not null,"
                + FIELD_SOLD + " text,"
                + FIELD_RECYCLE + " text,"
                + FIELD_ENSHRINE + " boolean,"
                + FIELD_UPLOAD + " boolean,"
                + FIELD_BOOL1 + " boolean,"
                + FIELD_BOOL2 + " boolean,"
                + FIELD_BOOL3 + " boolean,"
                + FIELD_BOOL4 + " boolean,"
                + FIELD_BOOL5 + " boolean,"
                + FIELD_STRING1 + " text,"
                + FIELD_STRING2 + " text,"
                + FIELD_STRING3 + " text,"
                + FIELD_STRING4 + " text,"
                + FIELD_STRING5 + " text,"
                + FIELD_DOUBLE1 + " double,"
                + FIELD_DOUBLE2 + " double,"
                + FIELD_DATE1 + " text,"
                + FIELD_DATE2 + " text"
                + ");";
        sqLiteDatabase.execSQL(sql);
    }
}
