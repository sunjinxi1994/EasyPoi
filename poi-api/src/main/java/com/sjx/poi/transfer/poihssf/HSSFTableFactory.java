package com.sjx.poi.transfer.poihssf;

import com.sjx.poi.transfer.TableFactory;

/**
 * @author : jingo
 * @Description: TODO
 * @date Date : 2021年01月11日 10:44
 **/
public class HSSFTableFactory implements TableFactory<HSSFExportTable, HSSFImportTable> {
    @Override
    public HSSFExportTable createExportTable() {
        return new HSSFExportTable();
    }

    @Override
    public HSSFImportTable createImportTable() {
        return new HSSFImportTable();
    }
}
