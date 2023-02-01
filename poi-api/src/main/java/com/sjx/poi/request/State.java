package com.sjx.poi.request;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年02月01日 16:26
 * 请求的状态枚举
 **/
public enum  State {

    /**
     * 初始状态
     */
    INITIALIZED,

    /**
     * 等待执行，被线程池拒绝
     */
    WAITED_REJECTED,

    /**
     * 等待执行，在线程池队列中
     */
    WAITED_QUEUED,

    /**
     * 执行状态
     */
    RUNNING,
    /**
     * 暂停状态
     */
    PAUSED,
    /**
     * 执行完成状态
     */
    FINISHED,


}
