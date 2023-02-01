package com.sjx.poi.dataprovider;

/**
 * author： hanwang
 * time: 2020/9/29  10:40
 */
public interface DataProviderFactory<D extends DataProvider> {



     D createDataProvider();


}