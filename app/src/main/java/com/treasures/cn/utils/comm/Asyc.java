package com.treasures.cn.utils.comm;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.treasures.cn.utils.BusiException;

public class Asyc {
    private final String LOG_TAG;

    private ExceptionHandler exceptionHandler;

    public Asyc(String tag ,ExceptionHandler handler){
        this.LOG_TAG = tag;
        exceptionHandler = handler;
    }
    public Asyc(Class<?> tagClass,ExceptionHandler handler){
        this(tagClass.getSimpleName(),handler);
    }
    public Asyc(Class<?> tagClass){
        this(tagClass,null);
    }

    private final static String UNKNOWN_ERR_MSG = "程序员哥哥又有得忙了";

    public void asyncRun(final Callback<Void> callback, final Execute executor){
        asyncTask(callback,executor);
    }
    public void asyncRun( final Execute executor){
        asyncTask(NCB,executor);
    }

    @SuppressLint("StaticFieldLeak")
    public <R> void asyncTask(final Callback<R> callback, final ExecuteAndReturn<R> executor){
        new AsyncTask<Void,Void,Void>(){

            private InterruptException interruptException = null;

            @Override
            protected Void doInBackground(Void... params) {
                try{
                    if(exceptionHandler == null){
                        result = executor.execute();
                    }else{
                        try{
                            result = executor.execute();
                        }catch (Exception e){
                            exceptionHandler.handler(e);
                        }
                    }
                } catch (BusiException e) {
//                    if(Log.isLoggable(LOG_TAG,Log.DEBUG)){
                    Log.v(LOG_TAG,"",e);
//                    }
                    exception = e;
                } catch (InterruptException e){
                    interruptException = e;
                } catch (Exception e){
                    log(UNKNOWN_ERR_MSG,e);
                    exception = new BusiException(UNKNOWN_ERR_MSG, e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(interruptException != null){
                    Callback<?> callback1 =  interruptException.getCallback();
                    if(callback1== null){
                        callback1 = callback;
                    }
                    asyncTask((Callback<R>)callback1,(ExecuteAndReturn<R>) interruptException.getExecuteAndReturn());
                }
                callback.callBack(exception==null && executor.isSuccess(), exception, result);
            }

            private BusiException exception;
            private R result;

        }.execute();
    }

    public void log(String msg,Throwable e){
        Log.v(LOG_TAG,msg,e);
    }
    public void log(String msg){
        Log.v(LOG_TAG,msg);
    }

    public static interface ExceptionHandler{

        void handler(Exception e) throws BusiException;
    }

    public <T> void interrupt(Callback<T> callback, ExecuteAndReturn<T> executeAndReturn){
        throw new InterruptException(callback,executeAndReturn);
    }
    public static <T> void interrupt(ExecuteAndReturn<T> executeAndReturn){
        throw new InterruptException(null, executeAndReturn);
    }


    private final Callback<Void> NCB = new Callback<Void>() {
        @Override
        public void callBack(boolean success, BusiException error, Void data) {
            if(!success){
                log("执行错误",error);
            }
        }
    };
}
