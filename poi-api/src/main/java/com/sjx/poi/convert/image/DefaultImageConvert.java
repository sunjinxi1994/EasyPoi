package com.sjx.poi.convert.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sjx.poi.convert.ParameterConvert;

import java.io.ByteArrayOutputStream;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月26日 8:48
 **/
public class DefaultImageConvert implements ImageConvert<byte[]> {

    @Override
    public byte[] exportConvert(byte[] from, String arguments) {
        return new byte[0];
    }

    @Override
    public byte[] importConvert(byte[] from, String arguments) {
        if (from==null){
            return null;
        }
        byte[] data=new byte[from.length];
        System.arraycopy(from,0,data,0,from.length);
        return data;
    }
}