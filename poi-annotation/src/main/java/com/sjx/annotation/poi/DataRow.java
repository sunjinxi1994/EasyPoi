package com.sjx.annotation.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author： hanwang
 * time: 2020/9/27  17:30
 * 标识一个实体类
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface DataRow {

    /**
     * 行高
     * @return
     */
    int height();

    /**
     * 配置的名称 默认采用全类名
     * @return
     */
    String name()default "";

    /**
     * 排序策略
     * @return
     */
    SortStrategy sortStrategy() default SortStrategy.SORT_AUTO;


}
