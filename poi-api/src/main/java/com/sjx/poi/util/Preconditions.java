package com.sjx.poi.util;

import android.text.TextUtils;

import java.io.File;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月14日 12:42
 **/
public class Preconditions {


    public static <T> T checkNotNull(T target,String message){
         if (target==null){
             throw new NullPointerException(message);
         }
         return target;
    }

    public static File checkFile(String path,String message){
        if (TextUtils.isEmpty(path)){
            throw new IllegalArgumentException(message);
        }
        File file=new File(path);
        if (!file.exists()){
            throw new IllegalArgumentException(message);
        }
        return file;
    }

    public static boolean isEmpty(byte[] data){
       if (data==null||data.length==0){
           return true;
       }else {
           return false;
       }
    }
}
