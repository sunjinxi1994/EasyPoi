package com.sjx.poi.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年02月01日 15:06
 *
 * 监听器观察者
 *
 **/
public class TransferObservable<T> implements TransferListener<T>{



    private List<TransferListener> transferListeners=new ArrayList<>();

    private Executor executor;
    public TransferObservable(Executor executor){
        this.executor=executor;
    }

    /**
     * 添加监听器
     * @param transferListener
     */
    public synchronized void addListener(TransferListener transferListener){
        transferListeners.add(transferListener);
    }

    /**
     * 移除监听器
     * @param transferListener
     */
    public synchronized void removeListener(TransferListener transferListener){
        transferListeners.remove(transferListener);
    }

    @Override
    public void onBeforeExecute() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (TransferListener transferListener:transferListeners) {
                    transferListener.onBeforeExecute();
                }
            }
        });

    }

    @Override
    public void onStart() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (TransferListener transferListener:transferListeners) {
                    transferListener.onStart();
                }
            }
        });

    }

    @Override
    public void onProgressUpdate(final  int num,final int progress,final  T t) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (TransferListener transferListener:transferListeners) {
                    transferListener.onProgressUpdate(num,progress,t);
                }
            }
        });

    }

    @Override
    public void onEnd() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (TransferListener transferListener:transferListeners) {
                    transferListener.onEnd();
                }
            }
        });

    }

    @Override
    public void onAfterExecute(final Throwable throwable) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (TransferListener transferListener:transferListeners) {
                    transferListener.onAfterExecute(throwable);
                }
            }
        });
    }
}
