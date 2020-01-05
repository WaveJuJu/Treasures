package com.treasures.cn.utils.comm;

import android.annotation.SuppressLint;

/**
 * 用于打破原有流程的异常，不是一个好的实现。
 */
public class InterruptException extends RuntimeException {
    private final Callback<?> callback;
    private final ExecuteAndReturn<?> executeAndReturn;

    @SuppressLint("NewApi")
    public <T> InterruptException(Callback<T> callback, ExecuteAndReturn<T> executeAndReturn){
        //这是一个用于业务处理的异常
        //不记录调用栈信息
        super(null,null,false,false);

        this.callback = callback;
        this.executeAndReturn = executeAndReturn;
    }

    public Callback<?> getCallback() {
        return callback;
    }

    public ExecuteAndReturn<?> getExecuteAndReturn() {
        return executeAndReturn;
    }
}
