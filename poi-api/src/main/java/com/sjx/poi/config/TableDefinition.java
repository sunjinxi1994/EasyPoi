package com.sjx.poi.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author： hanwang
 * time: 2020/9/29  15:36
 * Excel 表格定义
 */
public class TableDefinition {


    private List<TitleRowDefinition> titleRowDefinitions;
    private List<DataRowDefinition> dataRowDefinitions;
    public TableDefinition(){
        titleRowDefinitions= Collections.synchronizedList(new ArrayList<TitleRowDefinition>());
        dataRowDefinitions= Collections.synchronizedList(new ArrayList<DataRowDefinition>());
    }


    public void addTitleRowDefinition(TitleRowDefinition titleRowDefinition){
        titleRowDefinitions.add(titleRowDefinition);
    }
    public void addDataRowDefinition(DataRowDefinition dataRowDefinition){
        dataRowDefinitions.add(dataRowDefinition);
    }


    public List<DataRowDefinition> getDataRowDefinitions() {
        return dataRowDefinitions;
    }

    public List<TitleRowDefinition> getTitleRowDefinitions() {
        return titleRowDefinitions;
    }
}