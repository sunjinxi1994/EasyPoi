package com.sjx.poi.dataprovider;

import com.sjx.annotation.poi.Data;
import com.sjx.poi.config.Configuration;

/**
 * author： hanwang
 * time: 2020/12/16  12:40
 */

public interface CompositeDataProvider<D extends DataProvider>  extends DataProvider {

      /**
       * 注册一个DataProvider
       * @param d
       */
      void registerProvider(D d);





}
