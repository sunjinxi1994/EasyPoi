package com.sjx.poi.transfer.jxl;

import com.sjx.poi.transfer.ExportTable;


import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Orientation;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月15日 19:20
 **/
public class JxlExportTable implements ExportTable<CellFormat> {
    private WritableWorkbook writableWorkbook;
    private WritableSheet writableSheet;
    private WritableCellFormat cellFormat;
    @Override
    public void createTable(String path) throws Throwable{
        writableWorkbook= Workbook.createWorkbook(new File(path));
    }

    @Override
    public void createSheet(String sheetName)  {
        writableSheet=  writableWorkbook.createSheet(sheetName,0);
        writableSheet.getSettings().setDefaultColumnWidth(20);
    }

    @Override
    public CellFormat createCellStyle(int row, int column) throws Throwable{
        if (cellFormat==null){
            cellFormat=new WritableCellFormat();
            //设置居中对齐方式
            cellFormat.setAlignment(Alignment.CENTRE);
            //设置垂直居中
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            //设置是否自动换行
            cellFormat.setWrap(true);
            //设置边框
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            //设置文字方向
            cellFormat.setOrientation(Orientation.HORIZONTAL);
        }
        return cellFormat;
    }

    @Override
    public void addTextCell(int startRow,int endRow, int startColumn, int endColumn, String text)  throws Throwable {
        if (endColumn-startColumn>=1) {
            writableSheet.mergeCells(startColumn, startRow, endColumn, endRow);
        }
        writableSheet.setRowView(startRow,110*20);
        writableSheet.setColumnView(startColumn,20);
        Label label=new Label(startColumn,startRow,text);
        label.setCellFormat(createCellStyle(startRow,startColumn));
        writableSheet.addCell(label);
    }


    @Override
    public void addImageCell(int startRow,int endRow, int startColumn, int endColumn, byte[] image) throws Throwable{
        if (endColumn-startColumn>=1) {
            writableSheet.mergeCells(startColumn, startRow, endColumn, endRow);
        }
        WritableImage writableImage=new WritableImage(startColumn,startRow,endColumn-startColumn+1,endRow-startRow+1,image);
        writableSheet.addImage(writableImage);


    }

    @Override
    public void writeTable() throws Throwable {
            writableWorkbook.write();
            writableWorkbook.close();
    }

    @Override
    public void finish() {

    }
}
