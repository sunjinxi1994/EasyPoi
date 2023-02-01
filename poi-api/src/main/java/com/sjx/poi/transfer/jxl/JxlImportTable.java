package com.sjx.poi.transfer.jxl;

import com.sjx.poi.transfer.ImportTable;
import com.sjx.poi.transfer.Key;


import java.io.File;
import java.util.HashMap;

import jxl.Image;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableImage;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月15日 19:20
 **/
public class JxlImportTable implements ImportTable {
    private Workbook workbook;
    private HashMap<Key, Image> pictureDataMap;

    @Override
    public void createTable(String path) throws Throwable {
        workbook=  Workbook.getWorkbook(new File(path));
    }

    @Override
    public int getSheetNum() {
        return workbook.getNumberOfSheets();
    }

    @Override
    public int getRowNum(int sheetIndex) {
        return workbook.getSheet(sheetIndex).getRows();
    }

    @Override
    public String getTextValue(int sheetIndex, int row, int column) {
        return workbook.getSheet(sheetIndex).getRow(row)[column].getContents();
    }

    @Override
    public byte[] getImageValue(int sheetIndex, int row, int column) {
        if (pictureDataMap==null){
            loadImage();
        }
        Image writableImage= pictureDataMap.get(new Key(sheetIndex,row,column));
        if (writableImage!=null){
            return  writableImage.getImageData();
        }
        return null;
    }

    private void loadImage(){
        pictureDataMap=new HashMap<>();
        for (int i = 0; i < getSheetNum(); i++) {
            Sheet sheet = workbook.getSheet(i);
            int numberOfImages = sheet.getNumberOfImages();
            for (int j = 0; j <numberOfImages ; j++) {
                Image image =  sheet.getDrawing(j);
                Key key=new Key(i,(int)image.getRow(),(int)image.getColumn());
                pictureDataMap.put(key,image);
            }
        }
    }

    @Override
    public void finish() throws Throwable {
        workbook.close();
    }
}
