package com.sjx.poi.executor;

import android.text.TextUtils;
import android.util.Log;

import com.sjx.poi.util.PoiLogger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月30日 16:52
 * 定制线程池 实现任务监听和管理
 **/
public class TransferPoolExecutor extends ThreadPoolExecutor {

    private static final String TAG="TransferPoolExecutor";
    private ExecutorListener executorListener;
    private static final int CPU_COUNT=Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE=CPU_COUNT+1;
    private static final int MAXIMUM_POOL_SIZE=CPU_COUNT*2+1;
    private static final int TIME_OUT_MILLS=30*1000;



    private TransferPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                 RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,threadFactory,handler);
    }

    public void setExecutorListener(ExecutorListener executorListener) {
        this.executorListener = executorListener;
    }

    @Override
    public void execute(Runnable command) {
        super.execute(command);
        Log.e(TAG,"execute:"+command.getClass().toString());
        PoiLogger.e(TAG,this.toString());
        boolean isInQueue= getQueue().contains(command);
        if (isInQueue){
            if (NamedRunnable.class.isAssignableFrom(command.getClass())){
                NamedRunnable namedRunnable= (NamedRunnable) command;
                executorListener.onQueued(namedRunnable.getTag());
            }
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (NamedRunnable.class.isAssignableFrom(r.getClass())){

            NamedRunnable namedRunnable= (NamedRunnable) r;
            executorListener.afterExecute(namedRunnable.getTag(),namedRunnable.getThrowable());
        }
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        Log.e(TAG,"onBeforeExecute");
        if (NamedRunnable.class.isAssignableFrom(r.getClass())){
            if (Log.isLoggable(TAG,Log.ERROR)){
                Log.e(TAG,"onBeforeExecute");
            }
            NamedRunnable namedRunnable= (NamedRunnable) r;
            executorListener.beforeExecute(namedRunnable.getTag());
        }
    }

    public ExecutorListener getExecutorListener() {
        return executorListener;
    }

    private   static   final   class DefaultRejectHandler implements RejectedExecutionHandler {
        DefaultRejectHandler(){
        }
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (NamedRunnable.class.isAssignableFrom(r.getClass())){
                NamedRunnable namedRunnable= (NamedRunnable) r;
                if (TransferPoolExecutor.class.isAssignableFrom(executor.getClass())){
                    TransferPoolExecutor transferPoolExecutor= (TransferPoolExecutor) executor;
                    transferPoolExecutor.getExecutorListener().onReject(namedRunnable.getTag());
                }

            }
        }
    }

    /**
     * java 上采用多线程线程池运行
     * @return
     */
    public static TransferPoolExecutor newJavaTransferExecutor(){
        TransferPoolExecutor transferPoolExecutor =new TransferPoolExecutor.Builder()
                .setCorePoolSize(CORE_POOL_SIZE)
                .setMaximumPoolSize(MAXIMUM_POOL_SIZE)
                .setTimeoutMills(TIME_OUT_MILLS)
                .setBlockQueue(new LinkedBlockingQueue(16))
                .setName("java")
                .build();
        return transferPoolExecutor;
    }
    /**
     * android 上采用单线程线程池运行
     * @return
     */
    public static TransferPoolExecutor newAndroidTransferExecutor(){
         TransferPoolExecutor transferPoolExecutor =new TransferPoolExecutor.Builder()
                 .setCorePoolSize(1)
                 .setMaximumPoolSize(1)
                 .setBlockQueue(new LinkedBlockingQueue(4))
                 .setName("android")
                 .build();
         return transferPoolExecutor;
    }

   private   static final class DefaultThreadFactory implements ThreadFactory {

        private AtomicInteger atomicInteger=new AtomicInteger(0);
        private String name;
         DefaultThreadFactory(String name){
            this.name=name;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread=new Thread(r);
            thread.setName("transfer_"+name+"_thread_"+atomicInteger.getAndIncrement());
            return thread;
        }
    }


    public static class Builder{
        private int corePoolSize;
        private int maximumPoolSize;
        private String name;
        private BlockingQueue blockQueue;
        private long timeoutMills;
        private RejectedExecutionHandler rejectedExecutionHandler;
        public Builder  setCorePoolSize(int corePoolSize){
            this.corePoolSize=corePoolSize;
            return this;
        }

        public Builder  setMaximumPoolSize(int maximumPoolSize){
            this.maximumPoolSize=maximumPoolSize;
            return this;
        }
        public Builder  setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler){
            this.rejectedExecutionHandler=rejectedExecutionHandler;
            return this;
        }
        public Builder  setBlockQueue(BlockingQueue blockQueue){
            this.blockQueue=blockQueue;
            return this;
        }

        public Builder  setName(String name){
            this.name=name;
            return this;
        }
        public Builder  setTimeoutMills(long timeoutMills){
            this.timeoutMills=timeoutMills;
            return this;
        }
        public TransferPoolExecutor build(){
            if (TextUtils.isEmpty(name)){
                throw new IllegalArgumentException("name must be not-null and not-empty bug given:"+name);
            }
            if (rejectedExecutionHandler==null){
                rejectedExecutionHandler=new DefaultRejectHandler();
            }
            TransferPoolExecutor transferPoolExecutor=new TransferPoolExecutor(corePoolSize,maximumPoolSize,timeoutMills,TimeUnit.MILLISECONDS,blockQueue,new DefaultThreadFactory(name), rejectedExecutionHandler);
           return transferPoolExecutor;
        }
    }

}
