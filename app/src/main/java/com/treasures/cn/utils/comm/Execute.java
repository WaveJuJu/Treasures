package com.treasures.cn.utils.comm;


import com.treasures.cn.utils.BusiException;

public abstract class Execute extends ExecuteAndReturn<Void>{
    public Void execute() throws BusiException {
        run();
        return null;
    }
    public abstract void run() throws BusiException;
}