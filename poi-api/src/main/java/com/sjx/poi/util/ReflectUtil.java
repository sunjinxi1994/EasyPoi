package com.sjx.poi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月24日 22:07
 **/
public class ReflectUtil {


    public static Object getMemberField(Object  instance,String name){
        Object result=null;
        try {
            Class clazz=instance.getClass();
            Field field= clazz.getDeclaredField(name);
            field.setAccessible(true);
            result=   field.get(instance);
        } catch (NoSuchFieldException e) {

        }catch (IllegalAccessException e){

        }
        return result;
    }

    public static void setFieldValue(Object  instance,String filedName,Object value) throws  Exception{
        String methodName= "set"+filedName.substring(0,1).toUpperCase()+filedName.substring(1);
        Class clazz=instance.getClass();
        Field field= clazz.getDeclaredField(filedName);
        Method method= clazz.getDeclaredMethod(methodName,new Class[]{field.getType()});
        if (method!=null){
            method.invoke(instance,value);
        }else {
            field.setAccessible(true);
            field.set(instance,value);
        }
    }

    public static boolean isBoolean(Class clazz){
        return boolean.class.isAssignableFrom(clazz)||Boolean.class.isAssignableFrom(clazz);
    }
    public static boolean isVoid(Class clazz){
        return void.class.isAssignableFrom(clazz)||Void.class.isAssignableFrom(clazz);
    }


}