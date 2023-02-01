package com.sjx.poi.core;

import com.sjx.annotation.poi.Data;
import com.sjx.annotation.poi.Lazy;
import com.sjx.annotation.poi.Listener;
import com.sjx.annotation.poi.Path;
import com.sjx.annotation.poi.Sheet;
import com.sjx.annotation.poi.SkipRow;
import com.sjx.annotation.poi.Tag;
import com.sjx.annotation.poi.Transfer;
import com.sjx.annotation.poi.TransferType;
import com.sjx.poi.request.TransferRequest;
import com.sjx.poi.request.TransferRequestManager;
import com.sjx.poi.util.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * author： hanwang
 * time: 2020/9/29  17:44
 * 复杂方法参数的解析以及参数的处理
 *
 */
public class ServiceMethod {


    private TransferType transferType;
//    private ConcurrentHashMap<Integer, ParameterHandler.ParameterInfo> parameterInfoMap=new ConcurrentHashMap<>();

//    private ConcurrentHashMap<Integer, ParameterHandler> parameterHandlers=new ConcurrentHashMap<>();

    //没必要使用 ConcurrentHashMap  因为put操作在解析的时候已经加锁 get操作没有数据竞争 不存在并发问题
    private HashMap<Integer, ParameterHandler> parameterHandlers=new HashMap<>();


    static final int TYPE_RETURN_VOID=0;
    static final int TYPE_RETURN_CHAR_SEQUENCE=1;
    static final int TYPE_RETURN_REQUEST=2;
    static final int TYPE_RETURN_REQUEST_MANAGER=3;

    private int returnType;

    ServiceMethod(){

    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void parse(Method method){
              parseMethodAnnotation(method);
              parseArgAnnotation(method);
              parseMethodReturnType(method);
     }

    private void parseMethodReturnType(Method method){
        Class<?> returnType = method.getReturnType();
        if (CharSequence.class.isAssignableFrom(returnType)){
            this.returnType=TYPE_RETURN_CHAR_SEQUENCE;
        }else if (TransferRequest.class.isAssignableFrom(returnType)){
            this.returnType=TYPE_RETURN_REQUEST;
        }else if (TransferRequestManager.class.isAssignableFrom(returnType)){
            this.returnType=TYPE_RETURN_REQUEST_MANAGER;
        }else if (ReflectUtil.isVoid(returnType)){
            this.returnType=TYPE_RETURN_VOID;
        }else {
            throw new IllegalArgumentException("return type must is one of void ,CharSequence,TransferRequest and TransferRequestManager");
        }
    }


    public int getReturnType() {
        return returnType;
    }

    /**
     * 解析方法注解
     */
    private void parseMethodAnnotation(Method method){
        Annotation[] annotations = method.getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation=annotations[i];
            if (annotation instanceof Transfer){
                Transfer transfer= (Transfer) annotation;
                this.transferType= transfer.type();
            }
//            if (annotation .getClass().isAssignableFrom(Transfer.class)){
//                Transfer transfer= (Transfer) annotation;
//                this.transferType= transfer.type();
//            }
        }
    }

    /**
     * 解析参数注解
     */
    private void parseArgAnnotation(Method method){
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i <parameterAnnotations.length ; i++) {
            Type  parameterType=genericParameterTypes[i];
            ParameterHandler parameterHandler=null;
            for (int j = 0; j <parameterAnnotations[i].length ; j++) {
               Annotation annotation=     parameterAnnotations[i][j];
               if (annotation instanceof Path){
                   parameterHandler=new ParameterHandler.PathParameterHandler();
               }else if (annotation instanceof Data){
                   parameterHandler=new ParameterHandler.DataParameterHandler();
               }else if (annotation instanceof Listener){
                   parameterHandler=new ParameterHandler.ListenerParameterHandler();
               }else if (annotation instanceof Lazy){
                   parameterHandler=new ParameterHandler.LazyParameterHandler();
               }else if (annotation instanceof Tag){
                   parameterHandler=new ParameterHandler.TagParameterHandler();
               }else if (annotation instanceof Sheet){
                   parameterHandler=new ParameterHandler.SheetParameterHandler();
               }else if (annotation instanceof SkipRow){
                   parameterHandler=new ParameterHandler.SkipRowParameterHandler();
               }
               parameterHandler.parseParameter(parameterType);
               parameterHandlers.put(i,parameterHandler);
//               ParameterHandler.ParameterInfo parameterInfo= parameterHandler.generateParameterInfo();
//               parameterInfoMap.put(i,parameterInfo);

            }
        }
    }


//    public ParameterHandler getParameterHandler(int index){
//           return parameterHandlers.get(index);
//    }

    public void apply(ExportParameter.Builder builder, Object[] value){
        for (int i=0;i<parameterHandlers.size();i++){
            ParameterHandler parameterHandler=parameterHandlers.get(i);
            parameterHandler.apply(builder,value[i]);
        }
    }






}