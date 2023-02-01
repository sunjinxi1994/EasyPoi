package com.sjx.poi.config;


import com.sjx.annotation.poi.CellType;
import com.sjx.annotation.poi.CellScope;
import com.sjx.poi.convert.Convert;

/**
 * author： hanwang
 * time: 2020/9/29  15:09
 *
 * 数据单元格定义
 *
 */
public class DataCellDefinition extends CellDefinition{

    private int    columnIndex;
    private CellType cellType;
    private String javaFieldName;
    private String convertName;
    private String convertSimpleArg;
    private CellScope cellScope;

    private DataCellDefinition(Builder builder){
        this.columnIndex=builder.columnIndex;
        this.cellType=builder.cellType;
        this.cellScope=builder.cellScope;
        this.javaFieldName=builder.javaFieldName;
        this.convertName=builder.convertName;
        this.convertSimpleArg=builder.convertSimpleArg;
        this.mergeColumnNum=builder.mergeColumnNum;
        this.mergeRowNum=builder.mergeRowNum;

    }

    public CellScope getCellScope() {
        return cellScope;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public CellType getCellType() {
        return cellType;
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }


    public String getConvertName() {
        return convertName;
    }

    public String getConvertSimpleArg() {
        return convertSimpleArg;
    }

    public static class Builder{

        private int    columnIndex;
        private CellType cellType;
        private CellScope cellScope;
        private String javaFieldName;
        private String convertName;
        private String convertSimpleArg;
        private int mergeRowNum;
        private int mergeColumnNum;
        private Convert convert;
        private Class   convertClass;

        public Builder setConvertClass(Class convertClass) {
            this.convertClass = convertClass;
            return this;
        }

        public Builder setConvert(Convert convert) {
            this.convert = convert;
            return this;
        }

        public Builder setConvertName(String convertName) {
            this.convertName = convertName;
            return this;
        }
        public Builder setConvertSimpleArg(String convertSimpleArg) {
            this.convertSimpleArg = convertSimpleArg;
            return this;
        }

        public Builder setCellType(CellType cellType) {
            this.cellType = cellType;
            return this;
        }

        public Builder setCellScope(CellScope cellScope) {
            this.cellScope = cellScope;
            return this;
        }

        public Builder setJavaFieldName(String javaFieldName) {
            this.javaFieldName = javaFieldName;
            return this;
        }

        public Builder setColumnIndex(int columnIndex) {
            this.columnIndex = columnIndex;
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

        public DataCellDefinition build(){

            DataCellDefinition dataCellDefinition =new DataCellDefinition(this);
            return dataCellDefinition;
        }
    }
}