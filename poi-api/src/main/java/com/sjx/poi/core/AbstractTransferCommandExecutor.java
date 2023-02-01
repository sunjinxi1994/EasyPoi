package com.sjx.poi.core;

import com.sjx.poi.executor.NamedRunnable;
import com.sjx.poi.listener.TransferObservable;
import com.sjx.poi.request.RequestController;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月30日 17:14
 * 抽象命令执行器 扩展请求控制 tag设置功能
 **/
public abstract class AbstractTransferCommandExecutor  implements TransferCommandExecutor {

    private final Object lock=new Object();
    private final Object lazyLock=new Object();

    static final int STATE_PAUSE=1;
    static final int STATE_RESUME=2;
    static final int STATE_STOP=3;
    static final int STATE_WAIT_LAZY=4;
     volatile int state;


    CommandArguments commandArguments;

    AbstractTransferCommandExecutor(){
    }

    @Override
    public void start() {
                notifyForLazy();
    }

    @Override
    public void pause() {
        state=STATE_PAUSE;
    }

    @Override
    public void resume() {
        state=STATE_RESUME;
        verifyState();
    }

    @Override
    public void stop() {
        state=STATE_STOP;
    }

    @Override
    public void await() {

    }

    void pauseInner(){
        synchronized (lock){
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    void resumeInner(){
        synchronized (lock){
            lock.notifyAll();
        }
    }

    /**
     * 懒加载 需要暂停执行
     */
    void waitForLazy(){
         synchronized (lazyLock){
             try {
                 state=STATE_WAIT_LAZY;
                 lazyLock.wait();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
    }
    /**
     * 懒加载 需要暂停执行
     */
    void  notifyForLazy(){
        if (state==STATE_WAIT_LAZY){
            synchronized (lazyLock){
                lazyLock.notifyAll();
            }
        }
    }



    void verifyState(){
        if (state==STATE_PAUSE){
            pauseInner();
        }else if (state==STATE_RESUME){
            resumeInner();
        }
    }

    boolean isStop(){
        return state==STATE_STOP;
    }

    @Override
    public void setArguments(CommandArguments commandArguments) {
         this.commandArguments=commandArguments;
    }

    @Override
    public CommandArguments getArguments() {
        return commandArguments;
    }
}
