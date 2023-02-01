package com.sjx.poi.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年02月03日 11:15
 * 平台适配接口
 * 主要提供执行线程池和回调接口
 **/
public interface PlatformFactory {



    Platform createPlatform();

     interface Platform{
         /**
          * 监听回调执行器
          * @return
          */
         Executor callbackExecutor();
         /**
          * 运行执行器
          * @return
          */
         TransferPoolExecutor transferExecutor();
     }

     class DefaultPlatformFactory implements PlatformFactory{

         @Override
         public Platform createPlatform() {
             String vmName=  System.getProperty("java.vm.name");
             if (vmName.equals("Dalvik")){
                 return new AndroidPlatform();
             }else {
                 return new JavaPlatform();
             }
         }
     }

     class AndroidPlatform implements Platform{

         @Override
         public Executor callbackExecutor() {
             return new AndroidCallbackExecutor();
         }

         @Override
         public TransferPoolExecutor transferExecutor() {
             return TransferPoolExecutor.newAndroidTransferExecutor();
         }
     }

     class JavaPlatform implements Platform{

         @Override
         public Executor callbackExecutor() {
             return new JavaCallbackExecutor();
         }

         @Override
         public TransferPoolExecutor transferExecutor() {
             return TransferPoolExecutor.newJavaTransferExecutor();
         }
     }

     class JavaCallbackExecutor implements Executor{

         @Override
         public void execute(Runnable command) {
             command.run();
         }
     }

     class AndroidCallbackExecutor implements Executor{

         private Handler handler=new Handler(Looper.getMainLooper());

         @Override
         public void execute(Runnable command) {
              handler.post(command);
         }
     }

}
