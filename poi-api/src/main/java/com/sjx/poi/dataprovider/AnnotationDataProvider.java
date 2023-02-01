package com.sjx.poi.dataprovider;

import com.sjx.annotation.poi.DataCell;
import com.sjx.annotation.poi.DataRow;
import com.sjx.poi.config.Configuration;
import com.sjx.poi.config.TableDefinitionRegistry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * author： hanwang
 * time: 2020/9/29  10:40
 * 注解数据源获取
 *
 */
public class AnnotationDataProvider implements DataProvider {

    private Class registerClass;
    public AnnotationDataProvider(Class registerClass){
        this.registerClass=registerClass;
    }
    @Override
    public void register( TableDefinitionRegistry tableDefinitionRegistry) {
        Annotation annotation = registerClass.getAnnotation(DataRow.class);
        if (annotation==null){
            return;
        }
        //getFields 递归获取父类中的public的属性
        //getDeclaredFields 获取当前类中的所有修饰符的属性
        Field[] declaredFields = registerClass.getDeclaredFields();
        for (int i = 0; i <declaredFields.length; i++) {
             Field field= declaredFields[i];
            Annotation[] annotations = field.getAnnotations();
            for (int j = 0; j < annotations.length; j++) {
                Annotation fieldAnnotation=annotations[j];
                Class annotationType= fieldAnnotation.annotationType();
                if (annotationType.isAssignableFrom(DataCell.class)){
                    DataCell dataCell = (DataCell) fieldAnnotation;

                }
            }
        }
    }

}