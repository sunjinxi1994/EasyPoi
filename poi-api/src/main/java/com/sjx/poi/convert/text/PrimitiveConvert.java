package com.sjx.poi.convert.text;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月16日 20:22
 **/
public class PrimitiveConvert implements TextConvert<String> {
    @Override
    public String exportConvert(String from, String arguments) {
        return String.valueOf(from);
    }

    @Override
    public String importConvert(String from, String arguments) {
        return null;
    }
}