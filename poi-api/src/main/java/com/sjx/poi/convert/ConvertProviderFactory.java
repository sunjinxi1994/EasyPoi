package com.sjx.poi.convert;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月16日 16:08
 **/
public interface ConvertProviderFactory <C extends ConvertProvider>{

    /**
     * 创建ConvertProvider
     * @return
     */
    C createConvertProvider();
}