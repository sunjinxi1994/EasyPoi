package com.sjx.compiler.util;

import com.sjx.compiler.config.Constant;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * author： hanwang
 * time: 2020/12/16  18:55
 */
public class TypeHelper {

    private Filer filer;
    private Types types;
    private Elements elements;

    //存放方法名称和参数信息的映射关系
    private Map<ExecutableElement,List<ParameterInfo>> methodInfoMap;

    private Map<String,String> parameterName=new HashMap<>();




    private TypeMirrors typeMirrors;

    private Logger logger;

    {
        parameterName.put("arg0","registerName");
        parameterName.put("arg1","configuration");
    }

    public TypeHelper(Logger logger){
        this.logger=logger;
    }

    public void initType(ProcessingEnvironment processingEnvironment){
        filer=   processingEnvironment.getFiler();
        types= processingEnvironment.getTypeUtils();
        elements= processingEnvironment.getElementUtils();
        typeMirrors=new TypeMirrors();
        Elements elements=  processingEnvironment.getElementUtils();
        initTypeMirrors();
        initDataProvider(typeMirrors. dataProviderTypeMirror);


    }

    private TypeMirror handleTypeMirror(TypeMirror typeMirror){
        logger.debug("kind:"+typeMirror.getKind().toString());
        TypeMirror actualTypeMirror=null;
        switch (typeMirror.getKind()){
            case TYPEVAR:
                TypeVariable typeVariable= (TypeVariable) typeMirror;
                logger.debug("upper:"+ typeVariable.getUpperBound().toString());
                actualTypeMirror=typeVariable.getUpperBound();
                break;
            case DECLARED:
                //Class 或者接口类型
//                 ParameterizedType
                DeclaredType declaredType= (DeclaredType) typeMirror;
                //CompositeDataProvider<D extends DataProvider>
                //types.directSupertypes--->获取到DataProvider
                //getTypeArguments--->获取参数 D
                //types.erasure---->CompositeDataProvider 擦除泛型
                List<? extends  TypeMirror> typeMirrors= types.directSupertypes(declaredType);
                logger.debug("actualType:"+ declaredType.getEnclosingType().toString());
                logger.debug("argument:"+ declaredType.getTypeArguments().toString());
                logger.debug("argument:"+typeMirrors.toString());
                actualTypeMirror= types.erasure(declaredType);

                break;
        }
        return actualTypeMirror;
    }

    private void initTypeMirrors(){
        //data provider relative
        typeMirrors. dataProviderFactoryTypeMirror= elements.getTypeElement(Constant.DataProviderFactoryInfo.CLASS_NAME_FULL).asType();
        typeMirrors. dataProviderTypeMirror= elements.getTypeElement(Constant.DataProviderInfo.CLASS_NAME_FULL).asType();
//        typeMirrors. dataProviderAdapterTypeMirror= elements.getTypeElement(Constant.DataProviderInfo.CLASS_NAME_FULL_ADAPTER).asType();

        typeMirrors. compositeDataProviderTypeMirror= elements.getTypeElement(Constant.DataProviderInfo.CLASS_NAME_FULL_COMPOSITE).asType();
        //擦除泛型
        typeMirrors. compositeDataProviderTypeMirror= handleTypeMirror(typeMirrors.compositeDataProviderTypeMirror);
        typeMirrors. defaultCompositeDataProviderTypeMirror= elements.getTypeElement(Constant.DataProviderInfo.CLASS_NAME_FULL_COMPOSITE_DEFAULT).asType();

        //table definition relative
        typeMirrors.titleCellDefinitionTypeMirror= elements.getTypeElement(Constant.TitleDefinitionInfo.CLASS_NAME_FULL_TITLE_CELL_DEFINITION).asType();
        typeMirrors.titleCellDefinitionBuilderTypeMirror= elements.getTypeElement(Constant.TitleDefinitionInfo.CLASS_NAME_FULL_TITLE_CELL_DEFINITION_BUILDER).asType();
        typeMirrors.titleRowDefinitionTypeMirror= elements.getTypeElement(Constant.TitleDefinitionInfo.CLASS_NAME_FULL_TITLE_CELL_DEFINITION_ROW).asType();
        typeMirrors.dataCellDefinitionTypeMirror= elements.getTypeElement(Constant.DataDefinitionInfo.CLASS_NAME_FULL_DATA_CELL_DEFINITION).asType();
        typeMirrors.dataCellDefinitionBuilderTypeMirror= elements.getTypeElement(Constant.DataDefinitionInfo.CLASS_NAME_FULL_DATA_CELL_DEFINITION_BUILDER).asType();
        typeMirrors.dataRowDefinitionTypeMirror= elements.getTypeElement(Constant.DataDefinitionInfo.CLASS_NAME_FULL_DATA_CELL_DEFINITION_ROW).asType();
        typeMirrors.tableDefinitionRegistryTypeMirror= elements.getTypeElement(Constant.TableDefinitionInfo.CLASS_NAME_FULL_TABLE_DEFINITION_REGISTRY).asType();

        //擦除泛型
//        typeMirrors. columnDefinitionBuilderTypeMirror= handleTypeMirror(typeMirrors.columnDefinitionBuilderTypeMirror);

        typeMirrors. cellTypeTypeMirror= elements.getTypeElement(Constant.CellType.CLASS_NAME_FULL_CELL_TYPE).asType();
        typeMirrors. cellScopeTypeMirror= elements.getTypeElement(Constant.CellScope.CLASS_NAME_FULL_CELL_TYPE).asType();

        typeMirrors. tableDefinitionTypeMirror= elements.getTypeElement(Constant.TableDefinitionInfo.CLASS_NAME_FULL_TABLE_DEFINITION).asType();

        //convert relative
        typeMirrors. convertRegistryTypeMirror= elements.getTypeElement(Constant.ConvertProviderInfo.CLASS_NAME_CONVERT_REGISTRY_FULL).asType();
        typeMirrors. convertProviderTypeMirror= elements.getTypeElement(Constant.ConvertProviderInfo.CLASS_NAME_CONVERT_PROVIDER_FULL).asType();
        typeMirrors. convertFactoryProviderTypeMirror= elements.getTypeElement(Constant.ConvertProviderFactoryInfo.CLASS_NAME_CONVERT_PROVIDER_FACTORY_FULL).asType();
        //annotation relative
        typeMirrors. convertClassAnnotationTypeMirror= elements.getTypeElement(Constant.AnnotationInfo.ANNOTATION_NAME_CONVERT_CLASS).asType();

        typeMirrors. sortStrategyTypeMirror= elements.getTypeElement(Constant.DataDefinitionInfo.CLASS_NAME_FULL_DATA_CELL_DEFINITION_ROW_SORT_STRATEGY).asType();

    }


    public TypeMirrors getTypeMirrors() {
        return typeMirrors;
    }

    public Types getTypes() {
        return types;
    }

    public Filer getFiler() {
        return filer;
    }

    private void initDataProvider(TypeMirror dataProviderTypeMirror){
        methodInfoMap=new HashMap<>();
        Element dataProviderElement= types.asElement(dataProviderTypeMirror);
        List<? extends Element> enclosedElements = dataProviderElement.getEnclosedElements();
        for (Element element :enclosedElements) {
            if (!element.getKind().equals(ElementKind.METHOD)){
                continue;
            }
            //重载方法名称是相同的 需要使用Element 作为key
            ExecutableElement executableElement= (ExecutableElement) element;
            String methodName= executableElement.getSimpleName().toString();
            logger.debug("methodName:"+methodName);
            List<ParameterInfo> parameterInfos=methodInfoMap.get(executableElement);
            if (parameterInfos==null){
                parameterInfos=new ArrayList<>();
                methodInfoMap.put(executableElement,parameterInfos);
            }
            List<? extends VariableElement> parameters = executableElement.getParameters();
            for (VariableElement variableElement : parameters) {
                ParameterInfo parameterInfo=new ParameterInfo();
                parameterInfo.parameterName=variableElement.getSimpleName().toString();
                parameterInfo.typeMirror=variableElement.asType();
                parameterInfo.parameterName=parameterName.get(parameterInfo.parameterName);
                parameterInfos.add(parameterInfo);
                logger.debug("parameterName:"+parameterInfo.parameterName);
                logger.debug("parameterName:"+parameterInfo.typeMirror.toString());

            }
        }
    }



    public List<List<ParameterInfo>> findParameterInfos(String method){
        List<List<ParameterInfo>> parameterInfos=new ArrayList<>();
        methodInfoMap.forEach(new BiConsumer<ExecutableElement, List<ParameterInfo>>() {
            @Override
            public void accept(ExecutableElement executableElement, List<ParameterInfo> parameterInfo) {
                if (executableElement.getSimpleName().toString().equalsIgnoreCase(method)){
                    parameterInfos.add(parameterInfo);
                }
            }
        });
        return parameterInfos;
    }

    public ParameterInfo findParameterInfo(String method,TypeMirror typeMirror){
        ParameterInfo result=null;
        List<ParameterInfo> parameterInfos = methodInfoMap.get(method);
        if (parameterInfos!=null&&!parameterInfos.isEmpty()){
            for (ParameterInfo parameterInfo:parameterInfos) {
                boolean isSameType= types.isSameType(typeMirror,parameterInfo.typeMirror);
                if (isSameType){
                    result=parameterInfo;
                    break;
                }
            }
        }
        return result;
    }


    public static class ParameterInfo{
        public String parameterName;
        public TypeMirror typeMirror;
    }

    public static class TypeMirrors{
        //data provider relative
        public TypeMirror defaultCompositeDataProviderTypeMirror;
        public TypeMirror compositeDataProviderTypeMirror;
//        public TypeMirror dataProviderAdapterTypeMirror;
        public TypeMirror dataProviderTypeMirror;
        public TypeMirror dataProviderFactoryTypeMirror;

        //table definition relative
        public TypeMirror dataCellDefinitionTypeMirror;
        public TypeMirror dataCellDefinitionBuilderTypeMirror;
        public TypeMirror titleCellDefinitionTypeMirror;
        public TypeMirror titleCellDefinitionBuilderTypeMirror;
        public TypeMirror titleRowDefinitionTypeMirror;
        public TypeMirror dataRowDefinitionTypeMirror;
        public TypeMirror tableDefinitionTypeMirror;
        public TypeMirror cellTypeTypeMirror;
        public TypeMirror cellScopeTypeMirror;

        public TypeMirror tableDefinitionRegistryTypeMirror;


        //convert relative
        public TypeMirror convertProviderTypeMirror;
        public TypeMirror convertFactoryProviderTypeMirror;
        public TypeMirror convertRegistryTypeMirror;

        //annotation relative
        public TypeMirror convertClassAnnotationTypeMirror;

        public TypeMirror sortStrategyTypeMirror;

    }

}