package com.sjx.poi.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月15日 17:29
 **/
public class TableDefinitionRegistry {

    private ConcurrentHashMap<Class, TableDefinition> tableDefinitionConcurrentHashMap;

    public TableDefinitionRegistry(){
        this.tableDefinitionConcurrentHashMap=new ConcurrentHashMap<>();
    }

    public void registerTableDefinition(Class configClass, TableDefinition tableDefinition){
        tableDefinitionConcurrentHashMap.put(configClass, tableDefinition);
    }


    public TableDefinition retrieveTableDefinition(Class configClass){
        return tableDefinitionConcurrentHashMap.get(configClass);
    }

}
