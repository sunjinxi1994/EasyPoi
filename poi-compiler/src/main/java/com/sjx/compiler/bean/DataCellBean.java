package com.sjx.compiler.bean;

import com.sjx.annotation.convert.ConvertClass;
import com.sjx.annotation.convert.ConvertName;
import com.sjx.annotation.convert.DateConvert;
import com.sjx.annotation.convert.ImageConvert;
import com.sjx.annotation.poi.DataCell;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月17日 11:30
 **/
public class DataCellBean {

    private VariableElement variableElement;

    private DataCell dataCell;
    private ConvertClass convertClass;
    private ConvertName convertName;
    private ImageConvert imageConvert;
    private DateConvert dateConvert;




    public VariableElement getVariableElement() {
        return variableElement;
    }

    public void setVariableElement(VariableElement variableElement) {
        this.variableElement = variableElement;
    }

    public DataCell getDataCell() {
        return dataCell;
    }

    public void setDataCell(DataCell dataCell) {
        this.dataCell = dataCell;
    }

    public ConvertClass getConvertClass() {
        return convertClass;
    }

    public void setConvertClass(ConvertClass convertClass) {
        this.convertClass = convertClass;
    }

    public ConvertName getConvertName() {
        return convertName;
    }

    public void setConvertName(ConvertName convertName) {
        this.convertName = convertName;
    }

    public ImageConvert getImageConvert() {
        return imageConvert;
    }

    public void setImageConvert(ImageConvert imageConvert) {
        this.imageConvert = imageConvert;
    }

    public DateConvert getDateConvert() {
        return dateConvert;
    }

    public void setDateConvert(DateConvert dateConvert) {
        this.dateConvert = dateConvert;
    }
}