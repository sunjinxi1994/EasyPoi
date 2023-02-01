package com.sjx.poi.request;

import android.text.TextUtils;

import com.sjx.poi.core.TransferCommandExecutor;
import com.sjx.poi.executor.CommandExecutorRunnableAdapter;
import com.sjx.poi.listener.TransferObservable;
import com.sjx.poi.listener.TransferListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月29日 10:36
 **/
public class TransferRequest implements RequestController ,TransferListener {



 private String tag;
 private Object waitLock=new Object();


 private State state;

 private TransferObservable transferObservable;

 private CommandExecutorRunnableAdapter commandExecutorAdapter;
 public TransferRequest(TransferCommandExecutor commandExecutor,TransferObservable transferObservable,String tag){
  this.transferObservable=transferObservable;
  this.commandExecutorAdapter=new CommandExecutorRunnableAdapter(commandExecutor);
  this.state=State.INITIALIZED;
  this.tag=tag;
  commandExecutorAdapter.setTag(tag);
 }

// public void setTag(String tag) {
//  commandExecutorAdapter.setTag(tag);
//  this.tag = tag;
// }

 public String getTag() {
//  if (TextUtils.isEmpty(tag)){
//      tag="transfer_request_"+atomicInteger.getAndIncrement();
//  }
  return tag;
 }



 public CommandExecutorRunnableAdapter getCommandExecutor() {
  return commandExecutorAdapter;
 }

 /**
  * 添加监听器
  * @param transferListener
  */
 public void addListener(TransferListener transferListener){
  transferObservable.addListener(transferListener);
 }

 /**
  * 移除监听器
  * @param transferListener
  */
 public  void removeListener(TransferListener transferListener){
  transferObservable.removeListener(transferListener);
 }

 @Override
 public void start() {
  commandExecutorAdapter.start();
 }

 @Override
 public void pause() {
  commandExecutorAdapter.pause();
  state=State.PAUSED;
 }

 @Override
 public void resume() {
  commandExecutorAdapter.resume();
  state=State.RUNNING;
 }

 @Override
 public void stop() {
  commandExecutorAdapter.stop();
 }





 @Override
 public void await() {
  synchronized (waitLock){
   try {
    waitLock.wait();
   } catch (InterruptedException e) {
    e.printStackTrace();
   }
  }
 }


 @Override
 public void onBeforeExecute() {
  transferObservable.onBeforeExecute();
 }

 @Override
 public final void onStart() {
  transferObservable.onStart();
 }

 @Override
 public final void onProgressUpdate(int num, int progress,Object data) {
  transferObservable.onProgressUpdate(num,progress,data);
 }

 @Override
 public final  void onEnd() {
  transferObservable.onEnd();
 }

 @Override
 public void onAfterExecute(Throwable throwable) {
  transferObservable.onAfterExecute(throwable);
 }


 void updateState(State state){
  this.state=state;
 }

 /**
  * 获取当前的执行状态
  * @return
  */
 public State getState() {
  return state;
 }
}
