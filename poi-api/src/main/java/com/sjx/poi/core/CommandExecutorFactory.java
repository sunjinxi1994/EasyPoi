package com.sjx.poi.core;

import com.sjx.annotation.poi.TransferType;


/**
 * author： hanwang
 * time: 2020/9/30  10:57
 */
 class CommandExecutorFactory {


    public static CommandExecutorFactory commandExecutorFactory =new CommandExecutorFactory();



    public TransferCommandExecutor createCommand(TransferType transferType){
        TransferCommandExecutor result=null;
        switch (transferType){
                case Export:
                    result=new ExportCommandExecutor();
                    break;
                case Import:
                    result=new ImportCommandExecutor();
                    break;
            }
            return result;
    }

}