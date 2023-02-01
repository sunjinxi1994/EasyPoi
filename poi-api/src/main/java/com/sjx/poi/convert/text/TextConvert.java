package com.sjx.poi.convert.text;

import com.sjx.poi.convert.Convert;
import com.sjx.poi.convert.ParameterConvert;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月11日 15:47
 * 文本转换器接口
 **/
public interface TextConvert<F> extends ParameterConvert<F,String> {
    @Override
    String exportConvert(F from,String arguments) throws Throwable;

    @Override
    F importConvert(String from, String arguments) throws Throwable;
}
