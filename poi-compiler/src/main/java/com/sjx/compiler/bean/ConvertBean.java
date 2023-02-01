package com.sjx.compiler.bean;

import com.sjx.annotation.convert.Convert;

import javax.lang.model.element.TypeElement;
import javax.xml.bind.Element;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月16日 22:37
 **/
public class ConvertBean {

    private TypeElement element;

    private Convert convert;

    public TypeElement getElement() {
        return element;
    }

    public void setElement(TypeElement element) {
        this.element = element;
    }

    public Convert getConvert() {
        return convert;
    }

    public void setConvert(Convert convert) {
        this.convert = convert;
    }
}