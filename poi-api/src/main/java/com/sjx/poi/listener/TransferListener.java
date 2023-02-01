package com.sjx.poi.listener;

/**
 * author： hanwang
 * time: 2020/9/30  11:21
 */
public interface TransferListener<D> {


       /**
        * 执行前调用 在onStart之前
        */
       void onBeforeExecute();
       /**
        * 导出导入开始
        */
       void onStart();

       /**
        * 导入导出进度
        * @param num      总数
        * @param progress 当前进度
        * @param data     数据对象
        */
       void onProgressUpdate(int num,int progress,D data);

       /**
        * 导出导入结束，正常执行完成后调用，执行异常是会调用 onAfterExecute
        */
       void onEnd();

       /**
        * 执行结束的回调
        * @param throwable  执行中捕获的异常 可能为空
        */
       void onAfterExecute(Throwable throwable);
}