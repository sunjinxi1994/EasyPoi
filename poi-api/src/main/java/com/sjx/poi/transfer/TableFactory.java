package com.sjx.poi.transfer;

/**
 * @author : jingo
 * @Description: TODO
 * @date Date : 2021年01月11日 10:39
 **/
public interface TableFactory<E extends ExportTable,I extends ImportTable> {


       E createExportTable();
       I createImportTable();

}
