package com.sjx.poi.core;

import com.sjx.poi.core.EasyPoi;
import com.sjx.poi.core.ServiceMethod;
import com.sjx.poi.listener.TransferObservable;

/**
 * author： hanwang
 * time: 2020/9/30  11:17
 */
 class CommandArguments {

    private ExportParameter exportParameter;
    private EasyPoi easyPoi;
    private    Object[] args;
    private ServiceMethod serviceMethod;
    private TransferObservable transferObservable;

    public void setExportParameter(ExportParameter exportParameter) {
        this.exportParameter = exportParameter;
    }

    public ExportParameter getExportParameter() {
        return exportParameter;
    }

    public void setTransferObservable(TransferObservable transferObservable) {
        this.transferObservable = transferObservable;
    }

    public TransferObservable getTransferObservable() {
        return transferObservable;
    }

    public void setEasyPoi(EasyPoi easyPoi) {
        this.easyPoi = easyPoi;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public EasyPoi getEasyPoi() {
        return easyPoi;
    }

    public ServiceMethod getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(ServiceMethod serviceMethod) {
        this.serviceMethod = serviceMethod;
    }
}