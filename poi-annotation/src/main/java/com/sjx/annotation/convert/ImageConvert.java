package com.sjx.annotation.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author： hanwang
 * time: 2020/9/27  17:30
 * 时间数据类型转换器
 */
@Deprecated
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ImageConvert {

       /**
        * 转化器的类型
        * @return
        */
       ConvertType type()default ConvertType.IMAGE;

       /**
        *
        * 压缩质量
        * @return
        */
       int quality();

       /**
        * 具体实现类
        * @return
        */
       Class convertClass();

}
