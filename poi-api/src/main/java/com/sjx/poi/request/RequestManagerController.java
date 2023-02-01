package com.sjx.poi.request;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月29日 10:46
 **/
public interface RequestManagerController extends RequestController{

    /**
     * start transfer
     */
    void start(String tag);

    /**
     * stop transfer
     */
    void pause(String tag);

    /**
     * resume transfer
     */
    void resume(String tag);

    /**
     * stop transfer
     */
    void stop(String tag);

}
