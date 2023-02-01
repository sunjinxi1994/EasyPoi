package com.sjx.poi.convert.text;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月16日 20:20
 * convert from string to primitive type
 **/
public class PrimitiveStringConvert<F> implements TextConvert<F> {


    @Override
    public String exportConvert(F from, String arguments) {
        return String.valueOf(from);
    }

    @Override
    public F importConvert(String from, String arguments) {
        return null;
    }


    public static class BooleanStringConvert extends PrimitiveStringConvert<Boolean>{
        @Override
        public Boolean importConvert(String from, String arguments) {
            return Boolean.valueOf(from);
        }
    }
    public static class ByteStringConvert extends PrimitiveStringConvert<Byte>{
        @Override
        public Byte importConvert(String from, String arguments) {
            return Byte.valueOf(from);
        }
    }

    public static class ShortStringConvert extends PrimitiveStringConvert<Short>{
        @Override
        public Short importConvert(String from, String arguments) {
            return Short.valueOf(from);
        }
    }

    public static class IntegerStringConvert extends PrimitiveStringConvert<Integer>{
        @Override
        public Integer importConvert(String from, String arguments) {
            return Integer.valueOf(from);
        }
    }

    public static class LongStringConvert extends PrimitiveStringConvert<Long>{
        @Override
        public Long importConvert(String from, String arguments) {
            return Long.valueOf(from);
        }
    }

    public static class FloatStringConvert extends PrimitiveStringConvert<Float>{
        @Override
        public Float importConvert(String from, String arguments) {
            return Float.valueOf(from);
        }
    }

    public static class DoubleStringConvert extends PrimitiveStringConvert<Double>{
        @Override
        public Double importConvert(String from, String arguments) {
            return Double.valueOf(from);
        }
    }

    public static class CharacterStringConvert extends PrimitiveStringConvert<Character>{
        @Override
        public Character importConvert(String from, String arguments) {
            return Character.valueOf(from.charAt(0));
        }
    }
    public static class StringConvert extends PrimitiveStringConvert<String>{
        @Override
        public String importConvert(String from, String arguments) {
            return from;
        }
    }
}