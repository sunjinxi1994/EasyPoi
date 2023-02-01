package com.sjx.poi.convert;

import com.sjx.poi.convert.image.ImageConvert;
import com.sjx.poi.convert.text.TextConvert;

import java.util.HashMap;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月11日 12:01
 **/
public class ConvertRegistry {

    private HashMap<String,Convert> converts=new HashMap<>();

    public void register(Convert convert){
        register(convert.getClass().getName(),convert);
    }

    public void register(String name,Convert convert){
        converts.put(name,convert);
    }

    public Convert findConvertByName(String name){
        return converts.get(name);
    }
    public <T> T findConvertByType(Class<T> type){
        return (T) converts.get(type.getName());
    }


    public <T> T findConvertByNameAndType(String name,Class<T> clazz){
          Convert convert=  findConvertByName(name);
          if (convert!=null&&clazz.isAssignableFrom(convert.getClass())){
              return (T) convert;
          }
          return null;
    }


}
