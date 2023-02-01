package com.sjx.poi.core;


import com.sjx.poi.request.RequestController;

/**
 * author： hanwang
 * time: 2020/9/30  10:44
 * 命令执行器
 *
 */
public interface TransferCommandExecutor extends RequestController{


      void setArguments(CommandArguments commandArguments);
      void  executeCommand(CommandArguments commandArguments) throws Throwable;
      CommandArguments getArguments();


}