package com.sjx.poi.convert.date;

import com.sjx.poi.convert.Convert;
import com.sjx.poi.convert.text.TextConvert;

import java.text.SimpleDateFormat;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月16日 19:07
 **/
public class CacheDateConvert<F> implements TextConvert<F> {





    protected final SimpleDateFormat getDateFormat(String format){
        SimpleDateFormat simpleDateFormat = LruDateFormatCache.instance.get(format);
        if (simpleDateFormat==null){
            simpleDateFormat=new SimpleDateFormat(format);
            LruDateFormatCache.instance.put(format,simpleDateFormat);
        }
        return  simpleDateFormat;
    }

    @Override
    public String exportConvert(F from, String arguments) throws Throwable {
        return null;
    }

    @Override
    public F importConvert(String from, String arguments) throws Throwable {
        return null;
    }
}