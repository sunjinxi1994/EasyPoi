package com.sjx.easypoi.example;

import com.sjx.annotation.convert.Convert;
import com.sjx.poi.convert.image.ImageConvert;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月14日 0:19
 **/

//@Convert(name = "image_encrypt")
@Convert
public class CustomConvert implements ImageConvert<String> {


    private String name;
    private int num;

    public CustomConvert(){

    }

    public CustomConvert(String name, int num) {
        this.name = name;
        this.num = num;
    }

    @Override
    public byte[] exportConvert(String from, String arguments) {
        return new byte[0];
    }

    @Override
    public String importConvert(byte[] from, String arguments) {
        return null;
    }
}
