package com.sjx.poi.transfer.poihssf;

import android.util.Log;

import com.sjx.poi.transfer.ExportTable;
import com.sjx.poi.util.PoiLogger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jxl.format.CellFormat;

/**
 * @author : jingo
 * @Description: TODO
 * @date Date : 2021年01月11日 10:45
 **/
public class HSSFExportTable implements ExportTable<HSSFCellStyle> {

    private static final String TAG="HSSFExportTable";

    private    HSSFWorkbook  hssfWorkbook;
    protected   HSSFCellStyle hssfCellStyle;
    private   HSSFSheet hssfSheet;
    private FileOutputStream fileOutputStream;
    private String path;
    @Override
    public void createTable(String path) {
        this.path=path;
        hssfWorkbook=new HSSFWorkbook();
        hssfCellStyle = hssfWorkbook.createCellStyle();
        hssfCellStyle.setFillForegroundColor(HSSFCellStyle.THIN_BACKWARD_DIAG);
        hssfCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        hssfCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        hssfCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        hssfCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        hssfCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        hssfCellStyle.setWrapText(true);
    }

    @Override
    public void createSheet(String sheetName) {
        HSSFSheet hssfSheet=hssfWorkbook.getSheet(sheetName);
        if (hssfSheet==null){
            this.hssfSheet=hssfWorkbook.createSheet(sheetName);
//            this.hssfSheet.setDefaultRowHeight((short) 400);
            //height=110*20
            this.hssfSheet.setDefaultRowHeightInPoints(110);
            //height=110
//            this.hssfSheet.setDefaultRowHeight((short) 110);
            this.hssfSheet.setDefaultColumnWidth( 20);
        }
    }

    @Override
    public HSSFCellStyle createCellStyle(int row, int column) {
        return hssfCellStyle;
    }

    /*
     * setHeightInPoints 以像素点为单位设置高度
     * setHeight 设置像素点的1/20
     *
     * */
    @Override
    public void addTextCell(int startRow,int endRow, int startColumn,int endColumn,String text) {
        PoiLogger.e(TAG,text+"-startRow:"+startRow+"-endRow:"+endRow+"-startColumn:"+startColumn+"-endColumn:"+endColumn);
        if (endColumn-startColumn>=1){
            hssfSheet.addMergedRegion(new CellRangeAddress(startRow,endRow,startColumn,endColumn));
        }
        HSSFRow hssfRow= hssfSheet.getRow(startRow);
        if (hssfRow==null){
            hssfRow=hssfSheet.createRow(startRow);
        }
//        if (row%2==0){
//            hssfRow.setHeightInPoints((short) 110);
//        }else{
//            hssfRow.setHeightInPoints(110);
//        }
//          hssfRow.setHeight((short) 200);
        HSSFCell hssfCell= hssfRow.getCell(startColumn);
        if (hssfCell==null){
            hssfCell=hssfRow.createCell(startColumn);
            hssfCell.setCellStyle(createCellStyle(startRow,startColumn));
        }
        hssfCell.setCellValue(text);
    }



    @Override
    public void addImageCell(int startRow,int endRow,int startColumn,int endColumn, byte[] image) {
        PoiLogger.e(TAG,"-startRow:"+startRow+"-endRow:"+endRow+"-startColumn:"+startColumn+"-endColumn:"+endColumn);
        if (endColumn-startColumn>=1){
            hssfSheet.addMergedRegion(new CellRangeAddress(startRow,endRow,startColumn,endColumn));
        }
        HSSFPatriarch drawingPatriarch = hssfSheet.createDrawingPatriarch();
        //创建一个锚点 用来添加图片
        ClientAnchor clientAnchor=new HSSFClientAnchor(0,0,0,0,(short) startColumn,startRow,(short) (endColumn+1),endRow+1);
        clientAnchor.setAnchorType(ClientAnchor.DONT_MOVE_AND_RESIZE);
        //支持的图片类型  JPG/PNG
        int pictureIndex= hssfWorkbook.addPicture(image,HSSFWorkbook.PICTURE_TYPE_JPEG);
        drawingPatriarch.createPicture(clientAnchor,pictureIndex);
    }


    @Override
    public void writeTable() throws IOException {
        File file=new File(path);
        fileOutputStream=new FileOutputStream(file);
        hssfWorkbook.write(fileOutputStream);
    }

    @Override
    public void finish() {

        if (fileOutputStream!=null){
            try {
                fileOutputStream.flush();
                fileOutputStream.getFD().sync();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
