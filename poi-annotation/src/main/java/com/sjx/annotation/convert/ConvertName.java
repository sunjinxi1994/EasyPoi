package com.sjx.annotation.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * author： hanwang
 * time: 2020/9/27  17:30
 * 通过Class指定转化器
 */
@Deprecated
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ConvertName {


       String simpleArg() default "";


       /**
        * 转化器的name
        * @return
        */
       String convertName() ;

}
