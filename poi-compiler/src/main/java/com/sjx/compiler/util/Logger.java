package com.sjx.compiler.util;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * author： hanwang
 * time: 2020/12/15  10:04
 */
public class Logger {

      private Messager messager;

      public Logger(Messager messager){
          this.messager=messager;
      }


      public void error(String message){
          messager.printMessage(Diagnostic.Kind.ERROR,message);
      }


    public void debug(String message){
        messager.printMessage(Diagnostic.Kind.NOTE,message);
    }

    public void warning(String message){
        messager.printMessage(Diagnostic.Kind.WARNING,message);
    }

    public void other(String message){
        messager.printMessage(Diagnostic.Kind.OTHER,message);
    }
}