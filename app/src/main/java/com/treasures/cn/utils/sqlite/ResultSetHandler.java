package com.treasures.cn.utils.sqlite;
import android.database.Cursor;
import com.treasures.cn.utils.BusiException;

public interface ResultSetHandler<T>{
    T handle(Cursor cursor) throws BusiException;
}
