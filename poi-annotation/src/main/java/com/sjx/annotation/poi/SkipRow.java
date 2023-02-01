package com.sjx.annotation.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author： hanwang
 * time: 2020/9/27  17:30
 * 导入时指定跳过的行 可以用来跳过处理标题
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)

public @interface SkipRow {

    /**
     * @return
     *
     *
     */
    int[] rowNum() default {0};
}
