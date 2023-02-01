package com.sjx.annotation.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author： hanwang
 * time: 2021/1/11  16:24
 * 标题单元格 TitleCell
 *
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface TitleCell {

    /**
     * 列索引
     * @return
     */
    int index() default -1;

    /**
     * 标题名称
     * @return
     */
    String name();


    /**
     * 合并的单元格列数量
     * @return
     */
    int mergeColumnNum() default 0;

    /**
     * 合并的单元格行数量
     * @return
     */
    int mergeRowNum() default 0;


}
