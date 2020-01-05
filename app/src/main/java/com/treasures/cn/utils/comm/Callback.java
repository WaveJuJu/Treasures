package com.treasures.cn.utils.comm;


import com.treasures.cn.utils.BusiException;


public interface Callback<T> {
    void callBack(boolean success, BusiException error, T data);
}
