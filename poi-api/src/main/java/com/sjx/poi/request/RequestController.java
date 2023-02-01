package com.sjx.poi.request;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月29日 10:32
 **/
public interface RequestController {
    /**
     * start transfer
     */
    void start();

    /**
     * stop transfer
     */
    void pause();

    /**
     * resume transfer
     */
    void resume();

    /**
     * stop transfer
     */
    void stop();


    /**
     *  wait async execute finish
     */
    void await();

}
