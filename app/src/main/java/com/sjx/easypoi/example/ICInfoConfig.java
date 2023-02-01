package com.sjx.easypoi.example;

import com.sjx.annotation.poi.SortStrategy;
import com.sjx.annotation.poi.TitleCell;
import com.sjx.annotation.poi.TitleRow;
import com.sjx.annotation.poi.TableConfig;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月11日 16:29
 **/
public class ICInfoConfig {
    @TitleRow(
            height = 200,
            sortStrategy = SortStrategy.SORT_INDEX,
            titles = {
                    @TitleCell(index = 0,name = "姓名"),
                    @TitleCell(index = 1,name = "身份证号"),
                    @TitleCell(index = 2,name = "人员类型"),
                    @TitleCell(index = 4,name = "ID号"),
                    @TitleCell(index = 5,name = "IC卡号"),
                    @TitleCell(index = 6,name = "打卡时间"),
                    @TitleCell(index = 7,name = "设备名称"),
                    @TitleCell(index = 8,name = "识别方式"),
                    @TitleCell(index = 9,name = "对比得分"),
                    @TitleCell(index = 10,name = "是否通过"),
                    @TitleCell(index = 11,name = "是否上传"),
                    @TitleCell(index = 12,name = "人体温度"),
                    @TitleCell(index = 13,name = "口罩佩戴"),
                    @TitleCell(index = 14,name = "登记照片",mergeColumnNum = 2),
                    @TitleCell(index = 15,name = "实时照片",mergeColumnNum = 2),
            }
    )
    public void configTitleRow(){

    }

}
