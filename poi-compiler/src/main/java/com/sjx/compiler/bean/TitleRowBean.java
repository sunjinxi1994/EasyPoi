package com.sjx.compiler.bean;

import com.sjx.annotation.poi.DataRow;
import com.sjx.annotation.poi.SortStrategy;
import com.sjx.annotation.poi.TitleCell;
import com.sjx.annotation.poi.TitleRow;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月17日 11:23
 **/
public class TitleRowBean {

    private TitleRow titleRow;

    private ExecutableElement typeElement;

    /**
     * TitleRowDefinition titleRowDefinition=new TitleRowDefinition();
     * 变量名称
     */
    private String titleRowDefinitionName;

    public void setTitleRowDefinitionName(String titleRowDefinitionName) {
        this.titleRowDefinitionName = titleRowDefinitionName;
    }

    public String getTitleRowDefinitionName() {
        return titleRowDefinitionName;
    }

    public int getRowHeight(){
        return   titleRow.height();
    }

    public SortStrategy getSortStrategy(){
       return   titleRow.sortStrategy();
    }



    public TitleCell[] getTitles(){
       return titleRow.titles();
    }


    public TitleRow getTitleRow() {
        return titleRow;
    }

    public void setTitleRow(TitleRow titleRow) {
        this.titleRow = titleRow;
    }

    public ExecutableElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(ExecutableElement typeElement) {
        this.typeElement = typeElement;
    }
}