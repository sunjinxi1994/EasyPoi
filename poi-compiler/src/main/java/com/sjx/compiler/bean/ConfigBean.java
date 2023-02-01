package com.sjx.compiler.bean;

import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * author： hanwang
 * time: 2020/12/15  18:28
 */
public class ConfigBean {


    /**
     * 节点对应的root element
     */
    private TypeElement element;

    /**
     * Class对应的ClassName$$DataProvider
     */
    private TypeSpec typeSpec;

    /**
     * TitleRowBean
     */
   private List<TitleRowBean> titleRowBeans;
    /**
     * DataRowBean
     */
   private List<DataRowBean> dataRowBeans;

    public ConfigBean(){
        this.titleRowBeans=new ArrayList<>();
        this.dataRowBeans=new ArrayList<>();
    }


    public List<DataRowBean> getDataRowBeans() {
        return dataRowBeans;
    }

    public List<TitleRowBean> getTitleRowBeans() {
        return titleRowBeans;
    }

    public void addTitleRowBean(TitleRowBean titleRowBean){
        titleRowBeans.add(titleRowBean);
    }
    public void addDataRowBean(DataRowBean dataRowBean){
        dataRowBeans.add(dataRowBean);
    }

    public void setTypeSpec(TypeSpec typeSpec) {
        this.typeSpec = typeSpec;
    }

    public TypeSpec getTypeSpec() {
        return typeSpec;
    }

    public void setElement(TypeElement element) {
        this.element = element;
    }

    public TypeElement getElement() {
        return element;
    }

    // Row cell row     Row Rwo row cell row
//    public DataRowBean obtainDataRowBean(){
//        DataRowBean dataRowBean=null;
//        if (this.getDataRowBeans().size()>0){
//            dataRowBean= this.getDataRowBeans().get(0);
//                logger.error("a config class can contain a @DataRow annotation only");
//        }else {
//            dataRowBean=new DataRowBean();
//            this.addDataRowBean(dataRowBean);
//        }
//        return dataRowBean;
//    }



}