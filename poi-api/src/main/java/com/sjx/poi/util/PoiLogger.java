package com.sjx.poi.util;

import android.util.Log;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年02月06日 9:59
 **/
public class PoiLogger {

    private static boolean isEnable;


    public static void setEnable(boolean enable) {
        isEnable = enable;
    }

    public static boolean isEnable() {
        return isEnable;
    }
    public static void v(String tag,String message){
        if (isEnable()){
            Log.v(tag,message);
        }
    }

    public static void d(String tag,String message){
        if (isEnable()){
            Log.d(tag,message);
        }
    }
    public static void i(String tag,String message){
        if (isEnable()){
            Log.i(tag,message);
        }
    }

    public static void w(String tag,String message){
        if (isEnable()){
            Log.w(tag,message);
        }
    }
    public static void e(String tag,String message){
        if (isEnable()){
            Log.e(tag,message);
        }
    }
}
