package com.sjx.poi.dataprovider;

import com.sjx.poi.config.Configuration;
import com.sjx.poi.config.TableDefinitionRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;

/**
 * author： hanwang
 * time: 2020/12/16  12:43
 *
 * 组合的DataProvider  将配置的注册 分离为 一个DataProvider-->Class
 *
 */
public class DefaultCompositeDataProvider implements CompositeDataProvider<DataProvider> {

    private List<DataProvider> dataProviders;
    public DefaultCompositeDataProvider(){
        dataProviders=new ArrayList<>();
    }

    @Override
    public void registerProvider(DataProvider dataProvider) {
        dataProviders.add(dataProvider);
    }



    @Override
    public void register(TableDefinitionRegistry tableDefinitionRegistry) {
        for (DataProvider dataProvider :dataProviders) {
            dataProvider.register(tableDefinitionRegistry);
        }
    }


}