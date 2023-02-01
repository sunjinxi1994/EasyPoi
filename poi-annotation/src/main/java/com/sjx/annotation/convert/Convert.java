package com.sjx.annotation.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author： hanwang
 * time: 2020/9/27  17:30
 * 自定义转化器
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Convert {

       /**
        * 转化器的名称
        * @return
        */
       String name() default "";

}
