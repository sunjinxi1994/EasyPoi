package com.sjx.compiler.bean;

import com.sjx.annotation.poi.DataRow;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月17日 11:21
 **/
public class DataRowBean {

    private DataRow dataRow;

    private List<DataCellBean> dataCellBeanList;

    private ExecutableElement typeElement;

    private String dataRowDefinitionName;

    public DataRowBean(){
        dataCellBeanList=new ArrayList<>();
    }

    public void setDataRowDefinitionName(String dataRowDefinitionName) {
        this.dataRowDefinitionName = dataRowDefinitionName;
    }

    public String getDataRowDefinitionName() {
        return dataRowDefinitionName;
    }

    public List<DataCellBean> getDataCellBeanList() {
        return dataCellBeanList;
    }

    public void addDataCell(DataCellBean dataCellBean){
        dataCellBeanList.add(dataCellBean);
    }
    public void addDataAll(List<DataCellBean> dataCellBeans){
        dataCellBeanList.addAll(dataCellBeans);
    }
    public DataRow getDataRow() {
        return dataRow;
    }

    public void setDataRow(DataRow dataRow) {
        this.dataRow = dataRow;
    }

    public ExecutableElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(ExecutableElement typeElement) {
        this.typeElement = typeElement;
    }
}