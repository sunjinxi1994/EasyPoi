package com.sjx.poi.convert;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月26日 9:04
 * 带参数的转换器 参数类型只能是string
 **/
public interface ParameterConvert<F,T>  extends Convert<F,T,String>{
    @Override
    T exportConvert(F from, String arguments) throws Throwable;

    @Override
    F importConvert(T from, String arguments) throws Throwable;
}
