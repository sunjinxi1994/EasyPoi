package com.sjx.poi.executor;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月30日 16:55
 **/
public interface ExecutorListener {

    /**
     * task before execute
     * @param tag
     */
    void beforeExecute(String tag);
    /**
     * task after execute
     * @param tag
     */
    void  afterExecute(String tag,Throwable throwable);
    /**
     * task reject execute
     * @param tag
     */
    void  onReject(String tag);
    /**
     * task add queued
     * @param tag
     */
    void  onQueued(String tag);
}
