package com.sjx.poi.config;


/**
 * author： hanwang
 * time: 2020/9/29  15:09
 *
 * 标题单元格定义
 *
 */
public class TitleCellDefinition  extends CellDefinition {

    private String cellValue;


    private TitleCellDefinition(Builder builder){
        this.cellIndex=builder.cellIndex;
        this.cellValue=builder.cellValue;
        this.mergeColumnNum=builder.mergeColumnNum;
        this.mergeRowNum=builder.mergeRowNum;
    }

    public String getCellValue() {
        return cellValue;
    }


    public static class Builder{

        private int mergeRowNum;
        private int mergeColumnNum;
        private int cellIndex;
        private String cellValue;

        public Builder setCellIndex(int cellIndex) {
            this.cellIndex = cellIndex;
            return this;
        }

        public Builder setMergeColumnNum(int mergeColumnNum) {
            this.mergeColumnNum = mergeColumnNum;
            return this;
        }

        public Builder setMergeRowNum(int mergeRowNum) {
            this.mergeRowNum = mergeRowNum;
            return this;
        }


        public Builder setCellValue(String cellValue) {
            this.cellValue = cellValue;
            return this;
        }

        public TitleCellDefinition build(){
            TitleCellDefinition dataCellDefinition =new TitleCellDefinition(this);
            return dataCellDefinition;
        }
    }
}