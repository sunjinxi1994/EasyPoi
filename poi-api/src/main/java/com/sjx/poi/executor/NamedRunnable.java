package com.sjx.poi.executor;

import com.sjx.poi.util.PoiLogger;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月30日 14:53
 **/
public abstract class NamedRunnable implements Runnable,Named {


     String tag;
    private Throwable throwable;

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public void setTag(String tag) {
            this.tag=tag;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void run() {
        PoiLogger.e("test","run");
        try {
            execute();
        } catch (Throwable throwable) {
          this.throwable=throwable;
        }

    }


   protected abstract void execute() throws Throwable;

}
