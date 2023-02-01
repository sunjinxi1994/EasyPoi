package com.sjx.annotation.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author： hanwang
 * time: 2021/1/11  16:24
 * TitleRow 标题行
 *
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface TitleRow {


    /**
     * 行高
     * @return
     */
    int height() ;

    /**
     * 列索引
     * @return
     */
    TitleCell[] titles();

    /**
     * 排序策略
     * @return
     */
    SortStrategy sortStrategy() default SortStrategy.SORT_AUTO;

}
