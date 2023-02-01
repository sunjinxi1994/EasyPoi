package com.sjx.poi.config;


/**
 * author： hanwang
 * time: 2020/9/29  15:09
 *
 * 通用单元格定义
 *
 */
class CellDefinition implements Comparable<CellDefinition> {

     int cellIndex;
     int  mergeColumnNum;
    int  mergeRowNum;

    public int getCellIndex() {
        return cellIndex;
    }

    public int getMergeColumnNum() {
        return mergeColumnNum;
    }

    public int getMergeRowNum() {
        return mergeRowNum;
    }

    @Override
    public int compareTo(CellDefinition cellDefinition) {
        if(cellIndex==cellDefinition.getCellIndex()){
            return 0;
        }else if (cellIndex>cellDefinition.getCellIndex()){
            return 1;
        }else {
            return -1;
        }
    }





}