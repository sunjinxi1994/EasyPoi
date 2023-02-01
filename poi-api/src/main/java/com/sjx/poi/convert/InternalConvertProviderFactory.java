package com.sjx.poi.convert;

import com.sjx.poi.convert.date.CalendarConvert;
import com.sjx.poi.convert.date.DateConvert;
import com.sjx.poi.convert.date.TimestampConvert;
import com.sjx.poi.convert.image.DefaultImageConvert;
import com.sjx.poi.convert.image.FileImageConvert;
import com.sjx.poi.convert.text.DefaultTextConvert;
import com.sjx.poi.convert.text.PrimitiveStringConvert;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月25日 18:19
 * 注册内部Convert  不对外提供
 **/

public   class InternalConvertProviderFactory implements ConvertProviderFactory<ConvertProvider> {
    @Override
    public ConvertProvider createConvertProvider() {
        return new InternalConvertProvider();
    }

    class InternalConvertProvider implements ConvertProvider {
        @Override
        public void register(ConvertRegistry convertRegistry) {
            convertRegistry.register(new CalendarConvert());
            convertRegistry.register(new DateConvert());
            convertRegistry.register(new TimestampConvert());
            convertRegistry.register(new FileImageConvert());
            convertRegistry.register(new DefaultTextConvert());
            convertRegistry.register(new DefaultImageConvert());

            //register primitive type convert
            convertRegistry.register("boolean_string_convert",new PrimitiveStringConvert.BooleanStringConvert());
            convertRegistry.register("byte_string_convert",new PrimitiveStringConvert.ByteStringConvert());
            convertRegistry.register("short_string_convert",new PrimitiveStringConvert.ShortStringConvert());
            convertRegistry.register("integer_string_convert",new PrimitiveStringConvert.IntegerStringConvert());
            convertRegistry.register("float_string_convert",new PrimitiveStringConvert.FloatStringConvert());
            convertRegistry.register("double_string_convert",new PrimitiveStringConvert.DoubleStringConvert());
            convertRegistry.register("long_string_convert",new PrimitiveStringConvert.LongStringConvert());
            convertRegistry.register("character_string_convert",new PrimitiveStringConvert.CharacterStringConvert());
            convertRegistry.register("string_string_convert",new PrimitiveStringConvert.StringConvert());

        }
    }
}