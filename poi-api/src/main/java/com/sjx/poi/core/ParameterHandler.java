package com.sjx.poi.core;

import com.sjx.poi.listener.TransferListener;
import com.sjx.poi.util.ReflectUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * author： hanwang
 * time: 2020/12/1  19:07
 *
 * 参数处理器
 * 1.参数合法性校验 参数解析
 * 2.参数转化
 */
 interface  ParameterHandler<T> {


    /**
     * 校验注解参数类型
     *  @param type
     */
    void parseParameter(Type type);

//    /**
//     * 获取直接参数的相关类型信息
//     * @return
//     */
//    ParameterInfo generateParameterInfo();



    /**
     *
     * 参数类型转化
     * @param object
     * @return
     */
    void    apply(ExportParameter.Builder builder, Object object);



//     class ParameterInfo{
//        /**
//         *  原始类型 List<String>-->rawType=List.class
//         *  泛型参数类型 List<IcInfo>-->parameterType=IcInfo.class
//         */
//        public Class rawType;
//        public Class actualType;
//
//    }



    class PathParameterHandler implements ParameterHandler<String>{
        private Class rawType;
        @Override
        public   void parseParameter(Type type) {
            if (!(type instanceof Class)){
                throw new IllegalArgumentException("");
            }else {
                Class clazz= (Class) type;
                this.rawType=clazz;
                if (!CharSequence.class.isAssignableFrom(clazz)){
                    throw new IllegalArgumentException("@Path annotation must is applied a CharSequence  type");
                }
            }
        }

//        @Override
//        public ParameterInfo generateParameterInfo() {
//            ParameterInfo parameterInfo=new ParameterInfo();
//            parameterInfo.rawType=rawType;
//            return parameterInfo;
//        }

        @Override
        public void apply(ExportParameter.Builder builder, Object object) {
            ParameterInfo.PathParameterInfo parameterInfo=new ParameterInfo.PathParameterInfo();
            parameterInfo.setValue((String) object);
            parameterInfo.setType(rawType);
//            builder.setTransferListener(parameterInfo);
            builder.setTransferPath(parameterInfo);
        }

    }


    class ListenerParameterHandler implements ParameterHandler<TransferListener>{
        private Class rawType;

        @Override
        public void parseParameter(Type type) {
            if (!(type instanceof Class)){
                throw new IllegalArgumentException("");
            }else {
                Class clazz= (Class) type;
                this.rawType=clazz;
                if (!TransferListener.class.isAssignableFrom(clazz)){
                    throw new IllegalArgumentException("@Listener annotation must is applied a TransferListener  type");
                }
            }
        }

//        @Override
//        public  ParameterInfo generateParameterInfo() {
//            ParameterInfo parameterInfo=new ParameterInfo();
//            parameterInfo.rawType=rawType;
//
//            return parameterInfo;
//        }

        @Override
        public void apply(ExportParameter.Builder builder, Object object) {
            ParameterInfo.ListenerParameterInfo parameterInfo=new ParameterInfo.ListenerParameterInfo();
            parameterInfo.setValue((TransferListener) object);
            parameterInfo.setType(rawType);
            builder.setTransferListener(parameterInfo);
        }

    }


    class LazyParameterHandler implements ParameterHandler<Boolean>{
        private Class rawType;

        @Override
        public void parseParameter(Type type) {
            if (!(type instanceof Class)){
                throw new IllegalArgumentException("");
            }else {
                Class clazz= (Class) type;
                this.rawType=clazz;
                if (!ReflectUtil.isBoolean(clazz)){
                    throw new IllegalArgumentException("@Lazy annotation must is applied a boolean  type");
                }
            }
        }
        @Override
        public void apply(ExportParameter.Builder builder, Object object) {
            ParameterInfo.LazyParameterInfo parameterInfo=new ParameterInfo.LazyParameterInfo();
            parameterInfo.setValue((Boolean) object);
            parameterInfo.setType(rawType);
            builder.setTransferLazy(parameterInfo);
        }

    }
      class DataParameterHandler implements ParameterHandler<Collection>{

        private Class actualTypeArgument;

        private Class rawType;

        @Override
       public void parseParameter(Type type) {
            if (type instanceof ParameterizedType){
                ParameterizedType parameterizedType= (ParameterizedType) type;
                Type rawType= parameterizedType.getRawType();
                Type[] actualTypeArguments= parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length>1){

                }
                Type actualTypeArgument=actualTypeArguments[0];
                if (actualTypeArgument instanceof Class){
                    this.actualTypeArgument= (Class) actualTypeArgument;
                }

                if (rawType instanceof Class){
                    Class rawClazz= (Class) rawType;
                    this.rawType=rawClazz;
                    if (!Collection.class.isAssignableFrom(rawClazz)){
                        throw new IllegalArgumentException("@Data annotation must is applied a Collection  type");
                    }
                }
            }
        }

//        @Override
//        public  ParameterInfo generateParameterInfo() {
//            ParameterInfo parameterInfo=new ParameterInfo();
//            parameterInfo.rawType=rawType;
//            parameterInfo.actualType=actualTypeArgument;
//            return parameterInfo;
//        }

          @Override
          public void apply(ExportParameter.Builder builder, Object object) {
            ParameterInfo.DataParameterInfo parameterInfo=new ParameterInfo.DataParameterInfo();
            parameterInfo.setValue((Collection) object);
            parameterInfo.setType(rawType);
            parameterInfo.setActualType(actualTypeArgument);
              builder.setTransferData(parameterInfo);
          }
      }



    class TagParameterHandler implements ParameterHandler<String>{
        private Class rawType;
        @Override
        public   void parseParameter(Type type) {
            if (!(type instanceof Class)){
                throw new IllegalArgumentException("");
            }else {
                Class clazz= (Class) type;
                this.rawType=clazz;
                if (!CharSequence.class.isAssignableFrom(clazz)){
                    throw new IllegalArgumentException("@Tag annotation must is applied a CharSequence  type");
                }
            }
        }
        @Override
        public void apply(ExportParameter.Builder builder, Object object) {
            ParameterInfo.TagParameterInfo tagInfo=new ParameterInfo.TagParameterInfo();
            tagInfo.setValue((String) object);
            tagInfo.setType(rawType);
//            builder.setTransferListener(parameterInfo);
            builder.setTransferTag(tagInfo);
        }

    }



    class SheetParameterHandler implements ParameterHandler<String>{
        private Class rawType;
        @Override
        public   void parseParameter(Type type) {
            if (!(type instanceof Class)){
                throw new IllegalArgumentException("");
            }else {
                Class clazz= (Class) type;
                this.rawType=clazz;
                if (!CharSequence.class.isAssignableFrom(clazz)){
                    throw new IllegalArgumentException("@Sheet annotation must is applied a CharSequence  type");
                }
            }
        }

//        @Override
//        public ParameterInfo generateParameterInfo() {
//            ParameterInfo parameterInfo=new ParameterInfo();
//            parameterInfo.rawType=rawType;
//            return parameterInfo;
//        }

        @Override
        public void apply(ExportParameter.Builder builder, Object object) {
            ParameterInfo.SheetParameterInfo sheetParameterInfo=new ParameterInfo.SheetParameterInfo();
            sheetParameterInfo.setValue((String) object);
            sheetParameterInfo.setType(rawType);
            builder.setTransferSheet(sheetParameterInfo);
        }

    }



    class SkipRowParameterHandler implements ParameterHandler<Boolean>{
        private Class rawType;

        @Override
        public void parseParameter(Type type) {
            if (!(type instanceof Class)){
                throw new IllegalArgumentException("");
            }else {
                Class clazz= (Class) type;
                this.rawType=clazz;
                if (!ReflectUtil.isIntArray(clazz)){
                    throw new IllegalArgumentException("@SkipRow annotation must is applied a int[]  type");
                }
            }
        }
        @Override
        public void apply(ExportParameter.Builder builder, Object object) {
            ParameterInfo.SkipRowParameterInfo parameterInfo=new ParameterInfo.SkipRowParameterInfo();
            parameterInfo.setValue((int[]) object);
            parameterInfo.setType(rawType);
            builder.setTransferSkipRow(parameterInfo);
        }

    }
}