package com.sjx.annotation.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author： hanwang
 * time: 2020/9/27  17:16
 * DataCell 数据单元格
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public  @interface DataCell {


    /**
     * 列索引
     * @return
     */
    int index() default -1;


    /**
     * 数据列 类型
     * @return
     */
    CellType type() default CellType.Text;


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

    /**
     * 单元格配置的作用域
     * @return
     */
    CellScope scope() default CellScope.ALL;

}
