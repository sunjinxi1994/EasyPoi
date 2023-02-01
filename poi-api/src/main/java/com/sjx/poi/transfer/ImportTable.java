package com.sjx.poi.transfer;

import java.io.IOException;

/**
 * author： hanwang
 * time: 2021/1/11  10:37
 */

public  interface ImportTable {


     void createTable(String path) throws Throwable;

     int getSheetNum();

     int  getRowNum(int sheetIndex);


     String getTextValue(int sheetIndex,int row,int column);
     byte[] getImageValue(int sheetIndex,int row,int column);

     void finish() throws Throwable;


}
