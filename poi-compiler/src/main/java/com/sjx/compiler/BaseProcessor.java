package com.sjx.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.sjx.compiler.bean.ConfigBean;
import com.sjx.compiler.config.Constant;
import com.sjx.compiler.util.Logger;
import com.sjx.compiler.util.TypeHelper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月16日 20:34
 **/
public class BaseProcessor extends AbstractProcessor {

     Logger logger;
     Map<String, ConfigBean> classBeanMap;
     TypeHelper typeHelper;
    TypeHelper.TypeMirrors typeMirrors;

    String generatePath=null;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Messager messager= processingEnv.getMessager();
        logger=new Logger(messager);
//        filer=   processingEnv.getFiler();
//        types= processingEnv.getTypeUtils();
//        elements= processingEnv.getElementUtils();
        Map<String,String> options=  processingEnv.getOptions();
        logger.debug("options:"+options.toString());

        String moduleName= options.get(Constant.ProcessorOptionKey.KEY_MODULE_NAME);
        generatePath=options.get(Constant.ProcessorOptionKey.KEY_PACKAGE_POI_GENERATE);
        logger.debug("generatePath:"+generatePath);

        logger.debug("moduleName:"+moduleName);
        typeHelper=new TypeHelper(logger);
        typeHelper.initType(processingEnv);
        typeMirrors=typeHelper.getTypeMirrors();
//        test(processingEnv);
        logger.debug("init poi processor");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()){
            return false;
        }
        return true;
    }


      final void  writeFile(TypeSpec typeSpec){
        deleteFile(generatePath);
        JavaFile javaFile= JavaFile.builder(generatePath,typeSpec).build();
        try {
            javaFile.writeTo(typeHelper.getFiler());
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }



    private void deleteFile(String path){
        if (path==null||path.length()==0){
            return;
        }
        logger.debug(path);
        File file=new File(path);
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File childFile:files) {
                 deleteFile(childFile.getAbsolutePath());
            }
        }else {
            file.delete();
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
}