package com.sjx.poi.convert.text;

import android.text.TextUtils;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月25日 16:25
 **/
public class DefaultTextConvert implements TextConvert<Object> {
    @Override
    public String exportConvert(Object from, String arguments) {
        if (from==null){
            return "";
        }else {
            return String.valueOf(from);
        }
    }

    @Override
    public Object importConvert(String from, String arguments) {
        return from;
    }
}