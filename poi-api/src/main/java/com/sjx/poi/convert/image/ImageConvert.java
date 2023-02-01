package com.sjx.poi.convert.image;

import com.sjx.poi.convert.Convert;
import com.sjx.poi.convert.ParameterConvert;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月11日 11:01
 **/
public interface ImageConvert<F> extends ParameterConvert<F,byte[]> {

       @Override
       byte[] exportConvert(F from,String arguments);

       @Override
       F importConvert(byte[] from, String arguments);
}
