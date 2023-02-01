package com.sjx.poi.convert.date;

import android.util.LruCache;

import java.text.SimpleDateFormat;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月16日 19:13
 **/
public class LruDateFormatCache {


    public static LruDateFormatCache instance=new LruDateFormatCache();

    private LruCache<String, SimpleDateFormat> cache;

    private LruDateFormatCache(){
        cache=new LruCache<>(10);
    }


    public SimpleDateFormat get(String format){
         return cache.get(format);
    }

    public void put(String format,SimpleDateFormat simpleDateFormat){
         cache.put(format,simpleDateFormat);
    }

}