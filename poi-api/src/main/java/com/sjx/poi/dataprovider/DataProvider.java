package com.sjx.poi.dataprovider;

import com.sjx.poi.config.Configuration;
import com.sjx.poi.config.TableDefinitionRegistry;

/**
 * author： hanwang
 * time: 2020/9/29  10:39
 * 数据获取的接口
 *
 * Configuration
 *
 * Class-->ColumnDefinitionRegistry--->ColumnDefinition
 *
 *
 */

public interface DataProvider {

        void register( TableDefinitionRegistry tableDefinitionRegistry);
}
