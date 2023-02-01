package com.sjx.compiler.bean;

import com.sjx.annotation.poi.DataCell;

import javax.lang.model.element.Element;

/**
 * author： hanwang
 * time: 2020/12/15  18:27
 */

@Deprecated
public class ColumnBean {
    private Element element;
    private DataCell dataCell;
    private String columnDefinitionName;

    public void setColumnDefinitionName(String columnDefinitionName) {
        this.columnDefinitionName = columnDefinitionName;
    }

    public String getColumnDefinitionName() {
        return columnDefinitionName;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    public void setDataCell(DataCell dataCell) {
        this.dataCell = dataCell;
    }

    public DataCell getDataCell() {
        return dataCell;
    }
}