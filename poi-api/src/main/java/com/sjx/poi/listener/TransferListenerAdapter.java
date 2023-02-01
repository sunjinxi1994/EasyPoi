package com.sjx.poi.listener;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年02月02日 14:21
 * call back adapter
 **/
public class TransferListenerAdapter<T> implements TransferListener<T> {
    @Override
    public void onBeforeExecute() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onProgressUpdate(int num, int progress,T data) {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onAfterExecute(Throwable throwable) {

    }
}
