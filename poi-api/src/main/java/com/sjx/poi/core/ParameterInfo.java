package com.sjx.poi.core;

import com.sjx.poi.listener.TransferListener;

import java.util.Collection;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月24日 0:47
 **/
 class ParameterInfo<T> {

     Class type;
     T value;


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static class ListenerParameterInfo extends ParameterInfo<TransferListener>{

    }

    public static class PathParameterInfo extends ParameterInfo<String>{

    }
    public static class TagParameterInfo extends ParameterInfo<String>{

    }
    public static class LazyParameterInfo extends ParameterInfo<Boolean>{

    }
    public static class SheetParameterInfo extends ParameterInfo<String>{

    }
    public static class DataParameterInfo extends ParameterInfo<Collection>{
         Class actualType;

        public Class getActualType() {
            return actualType;
        }

        public void setActualType(Class actualType) {
            this.actualType = actualType;
        }
    }
}