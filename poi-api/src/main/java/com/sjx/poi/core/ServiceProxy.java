package com.sjx.poi.core;


import android.text.TextUtils;

import com.sjx.annotation.poi.Transfer;
import com.sjx.poi.listener.TransferObservable;
import com.sjx.poi.request.TransferRequest;
import com.sjx.poi.request.TransferRequestManager;
import com.sjx.poi.util.PoiLogger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author： hanwang
 * time: 2020/9/29  17:40
 * 基于命令模式的导入导出
 * TransferCommand-->抽象命令类
 * ImportCommand -->导入命令执行类
 * ExportCommand--->导出命令执行类
 * CommandFactory-->简单命令工程类
 *
 * 确定参数列表的类型
 * Object[] args
 * 根据注解类型确定参数索引 根据参数索引从数组获取响应的数据
 *
 * @Path 0  args[0]
 *
 */
public class ServiceProxy implements InvocationHandler {

    private static final String TAG="ServiceProxy";

    private static HashMap<Method, ServiceMethod> serviceMethodCache=new HashMap<>();

    private EasyPoi easyPoi;
    private static AtomicInteger idGenerator=new AtomicInteger();

    ServiceProxy(EasyPoi easyPoi){
        this.easyPoi=easyPoi;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ServiceMethod serviceMethod = serviceMethodCache.get(method);
        //存在多线程访问的情况
        if (serviceMethod==null){
            synchronized (ServiceProxy.class){
                if (serviceMethod==null){
                    serviceMethod=new ServiceMethod();
                    serviceMethod.parse(method);
                    serviceMethodCache.put(method,serviceMethod);
                }
            }
        }
        //Object中的方法交给当前对象执行，代理类中这些方法被代理，如果交给代理类 会出现死循环
        Class declaredClazz= method.getDeclaringClass();
        if (declaredClazz.equals(Object.class)){
            return method.invoke(this,args);
        }
        //只处理添加了指定注解的方法
        Transfer transfer= method.getAnnotation(Transfer.class);
        PoiLogger.e(TAG,declaredClazz.toString());
        if (transfer!=null){
            ExportParameter.Builder builder=new ExportParameter.Builder();
            serviceMethod.apply(builder,args);
            ExportParameter exportParameter= builder.build();
            CommandArguments commandArguments=new CommandArguments();
            commandArguments.setServiceMethod(serviceMethod);
            commandArguments.setArgs(args);
            commandArguments.setEasyPoi(easyPoi);
            commandArguments.setExportParameter(exportParameter);

            TransferObservable transferObservable=new TransferObservable(easyPoi.getCallbackExecutor());
            commandArguments.setTransferObservable(transferObservable);
            TransferCommandExecutor transferCommandExecutor = CommandExecutorFactory.commandExecutorFactory.createCommand(serviceMethod.getTransferType());
            transferCommandExecutor.setArguments(commandArguments);
            //交给线程池执行
            String tag=null;
            if (exportParameter.getTag()==null||TextUtils.isEmpty( exportParameter.getTag().getValue())){
                //生成一个简单的tag
                String interfaceName= proxy.getClass().getInterfaces()[0].getName();
                String methodName=method.getName();
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append("tag_")
                        .append(interfaceName)
                        .append("_")
                        .append(methodName)
                        .append("_")
                        .append(idGenerator.incrementAndGet());
                tag=stringBuilder.toString();
            }else {
                tag=exportParameter.getTag().getValue();
            }

            TransferRequest transferRequest=new TransferRequest(transferCommandExecutor,transferObservable,tag);

            TransferRequestManager transferRequestManager = easyPoi.getTransferRequestManager();
            transferRequestManager.addRequest(transferRequest);
//          transferCommandExecutor.executeCommand(commandArguments);
            Object result=null;
            switch (serviceMethod.getReturnType()){
                case ServiceMethod.TYPE_RETURN_CHAR_SEQUENCE:
                    result=tag;
                    break;
                case ServiceMethod.TYPE_RETURN_REQUEST:
                    result=transferRequest;
                    break;
                case ServiceMethod.TYPE_RETURN_REQUEST_MANAGER:
                    result=transferRequestManager;
                    break;
            }

            return result;
        }else {
            return null;
        }

    }
}