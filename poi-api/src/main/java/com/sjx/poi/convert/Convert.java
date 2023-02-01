package com.sjx.poi.convert;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月11日 10:58
 **/
public interface Convert<F,T,A> {

        T exportConvert(F from,A arguments) throws Throwable;
        F importConvert(T from,A arguments) throws Throwable;
}
