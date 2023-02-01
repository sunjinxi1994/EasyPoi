package com.sjx.poi.convert.date;

import com.sjx.poi.convert.Convert;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月13日 22:40
 **/
public class TimestampConvert extends CacheDateConvert<Timestamp> {

    public TimestampConvert(){
    }

    @Override
    public String exportConvert(Timestamp from,String format) throws Throwable {
        return getDateFormat(format).format(from);
    }

    @Override
    public Timestamp importConvert(String from, String arguments) throws Throwable{
        Timestamp timestamp=new Timestamp(getDateFormat(arguments).parse(from).getTime());
        return timestamp;
    }
}
