package com.sjx.poi.convert.date;

import com.sjx.poi.convert.Convert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月14日 0:02
 **/
public class CalendarConvert extends CacheDateConvert<Calendar> {


    public CalendarConvert(){
    }

    @Override
    public String exportConvert(Calendar from,String format) throws Throwable {
        return getDateFormat(format).format(from.getTime());
    }

    @Override
    public Calendar importConvert(String from, String arguments) throws Throwable {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(getDateFormat(arguments).parse(from));
        return  calendar;
    }
}
