package com.sjx.poi.convert;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月25日 0:06
 **/
public interface ConvertFactory {

    /**
     * 导出数据的Convert
     * @return
     */
    Convert createExportConvert();

    /**
     * 导入数据的Convert
     * @return
     */
    Convert createImportConvert();

}