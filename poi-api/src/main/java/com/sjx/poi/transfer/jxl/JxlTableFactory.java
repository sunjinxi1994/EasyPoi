package com.sjx.poi.transfer.jxl;

import com.sjx.poi.transfer.TableFactory;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月15日 19:20
 **/
public class JxlTableFactory implements TableFactory<JxlExportTable,JxlImportTable> {
    @Override
    public JxlExportTable createExportTable() {
        return new JxlExportTable();
    }

    @Override
    public JxlImportTable createImportTable() {
        return new JxlImportTable();
    }
}
