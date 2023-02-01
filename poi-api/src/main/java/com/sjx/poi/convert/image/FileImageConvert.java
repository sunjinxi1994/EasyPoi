package com.sjx.poi.convert.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.sjx.poi.convert.text.PrimitiveStringConvert;
import com.sjx.poi.util.PoiLogger;
import com.sjx.poi.util.Preconditions;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月11日 11:02
 **/
public class FileImageConvert implements ImageConvert<String> {
    private static final String TAG="FileImageConvert";
    @Override
    public byte[] exportConvert(String from,String format) {
        //仅支持png/jpg 需要判断图片类型
        if (TextUtils.isEmpty(from)){
            return null;
        }
        File file=new File(from);
        if (!file.exists()){
            return null;
        }
        PoiLogger.e(TAG,"format:"+format+"from:"+from);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Bitmap bitmap= BitmapFactory.decodeFile(from);
        bitmap.compress(Bitmap.CompressFormat.JPEG,Integer.valueOf(format),byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String importConvert(byte[] from, String arguments) {
        String path= Environment.getExternalStorageDirectory().toString()+File.separator+"import_image";
        File fileDir=new File(path);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        String fileName= UUID.randomUUID().toString().replace("-", "")+".jpg";
        ByteArrayInputStream  byteArrayInputStream=null;
        BufferedOutputStream  bufferedOutputStream=null;
        File file=new File(path,fileName);
        try {
            byteArrayInputStream=  new ByteArrayInputStream(from);
            bufferedOutputStream=  new BufferedOutputStream(new FileOutputStream(file));
            int length=0;
            byte[] buffer=new byte[8192];
            while ((length=byteArrayInputStream.read(buffer))!=-1){
                 bufferedOutputStream.write(buffer,0,length);
            }
            bufferedOutputStream.flush();
        }catch (Exception e){
             PoiLogger.e(TAG,e.toString());
        }finally {
             if (bufferedOutputStream!=null){
                 try {
                     bufferedOutputStream.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
        }


        return file.getAbsolutePath();
    }
}
