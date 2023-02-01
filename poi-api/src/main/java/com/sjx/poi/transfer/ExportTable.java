package com.sjx.poi.transfer;

import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.write.WriteException;

/**
 * author： hanwang
 * time: 2021/1/11  10:34
 */

public interface ExportTable<T> {

    /**
     * 创建Excel工作簿
     */
      void  createTable(String path) throws Throwable;

    /**
     * 创建Excel工作表
     * @param sheetName
     */
      void createSheet(String sheetName) ;


    /**
     * 创建单元格的格式
     * @param row
     * @param column
     * @return
     */
      T createCellStyle(int row,int column) throws Throwable;

    /**
     * 添加文本单元格
     * @param startRow     起始行
     * @param endRow       结束行
     * @param startColumn  起始列
     * @param endColumn   结束列
     * @param text
     */
      void addTextCell(int startRow,int endRow,int startColumn,int endColumn,String text) throws Throwable;

    /**
     * 添加图像单元格
     * @param startRow     起始行
     * @param endRow       结束行
     * @param startColumn  起始列
     * @param endColumn   结束列
     * @param image
     */
      void addImageCell(int startRow,int endRow,int startColumn,int endColumn,byte[] image) throws Throwable;

    /**
     * 写入到指定文件
     */
    void writeTable() throws Throwable;

    void  finish();



}
