package com.sjx.easypoi.example;

import android.os.Environment;

import com.sjx.poi.util.PoiLogger;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年04月21日 17:39
 **/
public class FileUtil {


    public static void saveImage(byte[] data,String path,String name){
        File fileDir=new File(path);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        ByteArrayInputStream byteArrayInputStream=null;
        BufferedOutputStream bufferedOutputStream=null;
        File file=new File(path,name);
        try {
            byteArrayInputStream=  new ByteArrayInputStream(data);
            bufferedOutputStream=  new BufferedOutputStream(new FileOutputStream(file));
            int length=0;
            byte[] buffer=new byte[8192];
            while ((length=byteArrayInputStream.read(buffer))!=-1){
                bufferedOutputStream.write(buffer,0,length);
            }
            bufferedOutputStream.flush();
        }catch (Exception e){
        }finally {
            if (bufferedOutputStream!=null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
