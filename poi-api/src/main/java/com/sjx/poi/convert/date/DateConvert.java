package com.sjx.poi.convert.date;

import com.sjx.poi.convert.Convert;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月13日 22:40
 **/
public class DateConvert extends CacheDateConvert<Date> {


    public DateConvert(){
    }

    @Override
    public String exportConvert(Date from,String format) throws Throwable {
        return getDateFormat(format).format(from);
    }

    @Override
    public Date importConvert(String from, String arguments) throws Throwable{
        return getDateFormat(arguments).parse(from);
    }
}
