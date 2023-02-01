package com.sjx.poi.request;

import com.sjx.poi.executor.ExecutorListener;
import com.sjx.poi.executor.TransferPoolExecutor;
import com.sjx.poi.util.PoiLogger;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月29日 10:36
 **/
public class TransferRequestManager implements RequestManagerController, ExecutorListener {

//   private ConcurrentLinkedQueue requestQueue=new ConcurrentLinkedQueue();
//   private LinkedList linkedList=new LinkedList();
//   private Queue queue=new ArrayDeque(10);

    private TransferPoolExecutor transferPoolExecutor;


    /**
     * 已经提交的请求
     */
    private ConcurrentHashMap<String,TransferRequest> submitRequest=new ConcurrentHashMap<>();

    /**
     * 正在执行的请求
     */
    private ConcurrentHashMap<String,TransferRequest> runningRequest=new ConcurrentHashMap<>();

    /**
     * 被拒绝 在等待的请求
     */
    private ConcurrentHashMap<String,TransferRequest> waitForRejectRequest=new ConcurrentHashMap<>();

    /**
     * 被添加到线程池队列 在等待的请求
     */
    private ConcurrentHashMap<String,TransferRequest> waitForQueuedRequest=new ConcurrentHashMap<>();

    public TransferRequestManager(TransferPoolExecutor executor){
        this.transferPoolExecutor=executor;
        transferPoolExecutor.setExecutorListener(this);
    }

    public void addRequest(TransferRequest transferRequest){
        transferPoolExecutor.execute(transferRequest.getCommandExecutor());
        submitRequest.put(transferRequest.getTag(),transferRequest);

    }




    @Override
    public void start() {

    }

    @Override
    public void pause() {
        Collection<TransferRequest> values = runningRequest.values();
        Iterator<TransferRequest> iterator = values.iterator();
        while (iterator.hasNext()){
            iterator.next().pause();
        }
    }

    @Override
    public void resume() {
        Collection<TransferRequest> values = runningRequest.values();
        Iterator<TransferRequest> iterator = values.iterator();
        while (iterator.hasNext()){
            iterator.next().resume();
        }
    }

    @Override
    public void stop() {
        Collection<TransferRequest> values = runningRequest.values();
        Iterator<TransferRequest> iterator = values.iterator();
        while (iterator.hasNext()){
            iterator.next().stop();
        }
    }

    @Override
    public void await() {

    }

    @Override
    public void start(String tag) {

    }

    @Override
    public void pause(String tag) {
        TransferRequest transferRequest= runningRequest.get(tag);
        if (transferRequest!=null){
            transferRequest.pause();
        }
    }

    @Override
    public void resume(String tag) {
        TransferRequest transferRequest= runningRequest.get(tag);
        if (transferRequest!=null){
            transferRequest.resume();
        }
    }

    @Override
    public void stop(String tag) {
        TransferRequest transferRequest= runningRequest.get(tag);
        if (transferRequest!=null){
            transferRequest.stop();
        }

    }

    @Override
    public void beforeExecute(String tag) {
        //提交了 立即执行
        TransferRequest transferRequest = submitRequest.get(tag);
        if (transferRequest!=null){
            submitRequest.remove(tag);
            runningRequest.put(tag,transferRequest);
        }else {
            //从队列中取出执行的任务
            transferRequest=waitForQueuedRequest.get(tag);
            if(transferRequest!=null){
                waitForQueuedRequest.remove(tag);
                runningRequest.put(tag,transferRequest);
            }
        }
        if (transferRequest!=null){
            transferRequest.onBeforeExecute();
            transferRequest.updateState(State.RUNNING);
        }

    }

    @Override
    public void afterExecute(String tag,Throwable throwable) {
        TransferRequest transferRequest = runningRequest.get(tag);
        if (transferRequest!=null){
            transferRequest.onAfterExecute(throwable);
            transferRequest.updateState(State.FINISHED);
        }
        runningRequest.remove(tag);
        if (!waitForRejectRequest.isEmpty()){
            Collection<TransferRequest> values = waitForRejectRequest.values();
            Iterator<TransferRequest> iterator = values.iterator();
            TransferRequest next = iterator.next();
            if (next!=null){
                addRequest(next);
            }
        }


    }

    @Override
    public void onReject(String tag) {
        TransferRequest transferRequest = submitRequest.get(tag);
        if (transferRequest!=null){
            submitRequest.remove(tag);
            waitForRejectRequest.put(tag,transferRequest);
            transferRequest.updateState(State.WAITED_REJECTED);

        }
    }

    @Override
    public void onQueued(String tag) {
        PoiLogger.e("queued:",tag);
        TransferRequest transferRequest = submitRequest.get(tag);
        if (transferRequest!=null){
            submitRequest.remove(tag);
            waitForQueuedRequest.put(tag,transferRequest);
            transferRequest.updateState(State.WAITED_QUEUED);

        }
    }

    public TransferRequest getRequestByTag(String tag){
        TransferRequest result= runningRequest.get(tag);
        if (result!=null){
            return result;
        }
        result= waitForQueuedRequest.get(tag);
        if (result!=null){
            return result;
        }
        result= waitForRejectRequest.get(tag);
        if (result!=null){
            return result;
        }
        result= submitRequest.get(tag);
        if (result!=null){
            return result;
        }
        return result;
    }
}
