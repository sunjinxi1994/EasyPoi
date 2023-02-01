package com.sjx.poi.convert;

import com.sjx.poi.config.Configuration;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月14日 8:10
 **/
public interface ConvertProvider  {

    /**
     * 注册Convert
     * @param convertRegistry
     */
    void register(ConvertRegistry convertRegistry);

}
