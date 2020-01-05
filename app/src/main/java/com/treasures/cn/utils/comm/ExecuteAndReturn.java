package com.treasures.cn.utils.comm;


import com.treasures.cn.utils.BusiException;


public abstract class ExecuteAndReturn<R> {
    private boolean success=true;
    protected abstract R execute() throws BusiException;
    protected void setSuccess(boolean success){
        this.success = success;
    }

    boolean isSuccess() {
        return success;
    }
}
