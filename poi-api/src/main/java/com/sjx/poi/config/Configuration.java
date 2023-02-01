package com.sjx.poi.config;

import com.sjx.poi.convert.Convert;
import com.sjx.poi.convert.ConvertRegistry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * author： hanwang
 * time: 2020/9/29  15:38
 */

public class Configuration {



    private ConvertRegistry convertRegistry;
    private TableDefinitionRegistry tableDefinitionRegistry;
    public Configuration(){
        convertRegistry=new ConvertRegistry();
        tableDefinitionRegistry=new TableDefinitionRegistry();
    }

    public ConvertRegistry getConvertRegistry() {
        return convertRegistry;
    }

    public TableDefinitionRegistry getTableDefinitionRegistry() {
        return tableDefinitionRegistry;
    }

    public Configuration registerTableDefinition(Class configClass, TableDefinition tableDefinition){
        tableDefinitionRegistry.registerTableDefinition(configClass, tableDefinition);
        return this;
    }


    public TableDefinition retrieveTableDefinition(Class configClass){
       return tableDefinitionRegistry.retrieveTableDefinition(configClass);
    }

    public Configuration register(Class type, Convert convert){
        convertRegistry.register(convert);
        return this;
    }

    public Configuration register(String name,Convert convert){
        convertRegistry.register(name,convert);
        return this;
    }

    public Convert retrieveConvert(String name){
        return convertRegistry.findConvertByName(name);
    }
    public <T> T retrieveConvert(Class<T> type){
        return convertRegistry.findConvertByType(type);
    }
}