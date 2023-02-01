package com.sjx.compiler;

import com.sjx.annotation.convert.Convert;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sjx.compiler.bean.ConvertBean;
import com.sjx.compiler.config.Constant;
import com.sjx.compiler.util.TextUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月16日 20:34
 **/
public class ConvertProcessor extends BaseProcessor {

   private List<ConvertBean> convertBeans=new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        logger.debug("init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        logger.debug("size process");
        super.process(set, roundEnvironment);
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(Convert.class);
        logger.debug("size:"+elementsAnnotatedWith.size());
        Iterator<? extends Element> iterator = elementsAnnotatedWith.iterator();
        while (iterator.hasNext()){
            Element element= iterator.next();
            //Convert注解必须作用在Class上
            if (!element.getKind().isClass()){
                continue;
            }
            //获取元素上的注解
            Convert convert= element.getAnnotation(Convert.class);
            if (convert==null){
                continue;
            }
            ConvertBean convertBean=new ConvertBean();
            convertBean.setConvert(convert);
            convertBean.setElement((TypeElement) element);
            convertBeans.add(convertBean);
        }

      TypeSpec convertProviderTypeSpec=  generateConvertProvider();
       TypeSpec convertProviderFactoryTypeSpec= generateConvertProviderFactory(convertProviderTypeSpec);
       writeFile(convertProviderFactoryTypeSpec);
        return true;
    }


    private TypeSpec generateConvertProviderFactory(TypeSpec typeSpec){
        MethodSpec methodSpec=    MethodSpec.methodBuilder(Constant.ConvertProviderFactoryInfo.METHOD_NAME_CREATE)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                     .returns(TypeName.get(typeMirrors.convertProviderTypeMirror))
                    .addStatement("return new $L()",typeSpec.name).build();
        return TypeSpec.classBuilder(Constant.ConvertProviderFactoryInfo.CLASS_NAME_GENERATE)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(
                        ParameterizedTypeName.get(
                               ClassName.get((TypeElement) typeHelper.getTypes().asElement( typeHelper.getTypeMirrors().convertFactoryProviderTypeMirror)),
                                TypeName.get( typeHelper.getTypeMirrors().convertProviderTypeMirror)
                                )
                ).addMethod(methodSpec)

                .addType(typeSpec)
                .build();


    }

    /**
     *
     *   class AptConvertProvider implements ConvertProvider{
     *
     *         @Override
     *         public void register(ConvertRegistry convertRegistry) {
     *               convertRegistry.register(MyConvert.class.getName(),new MyConvert());
     *         }
     *     }
     *
     *
     */
    private TypeSpec generateConvertProvider(){
       ParameterSpec parameterSpec=  ParameterSpec.builder(TypeName.get(typeHelper.getTypeMirrors().convertRegistryTypeMirror),Constant.ConvertProviderInfo.PARAMETER_NAME_REGISTRY).build();
        // convertRegistry.register(MyConvert.class.getName(),new MyConvert());
        List<CodeBlock> codeBlocks=new ArrayList<>();
        for (ConvertBean convertBean: convertBeans) {
            Convert convert = convertBean.getConvert();
            TypeElement typeElement=convertBean.getElement();
            String name=null;
            if (!TextUtil.isEmpty(convert.name())){
                name=convert.name();
            }else {
                name=typeElement.getQualifiedName().toString();
            }

          CodeBlock codeBlock=  CodeBlock.of("$N.register($S,new $T());",parameterSpec,name,TypeName.get(typeElement.asType()));
            codeBlocks.add(codeBlock);
        }
        MethodSpec methodSpec=MethodSpec.methodBuilder(Constant.ConvertProviderInfo.METHOD_NAME_REGISTRY)
                .addAnnotation(ClassName.get(Override.class))
                .addParameter(
                        ParameterSpec.builder(TypeName.get(typeHelper.getTypeMirrors().convertRegistryTypeMirror),Constant.ConvertProviderInfo.PARAMETER_NAME_REGISTRY).build()
                )
                .addModifiers(Modifier.PUBLIC)
                .addCode(CodeBlock.join(codeBlocks,";"))
                .build();

       TypeSpec typeSpec= TypeSpec.classBuilder(Constant.ConvertProviderInfo.CLASS_NAME_GENERATE)
                .addMethod(methodSpec)
                .addSuperinterface(TypeName.get(typeHelper.getTypeMirrors().convertProviderTypeMirror)).build();
       return typeSpec;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet hashSet=new HashSet();
        hashSet.add(Convert.class.getName());
        return hashSet;
    }


}