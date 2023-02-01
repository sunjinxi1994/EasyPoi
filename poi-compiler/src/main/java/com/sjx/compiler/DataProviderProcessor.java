package com.sjx.compiler;

import com.sjx.annotation.convert.Convert;
import com.sjx.annotation.convert.ConvertClass;
import com.sjx.annotation.convert.ConvertName;
import com.sjx.annotation.convert.DateConvert;
import com.sjx.annotation.convert.ImageConvert;
import com.sjx.annotation.poi.CellType;
import com.sjx.annotation.poi.TableConfig;
import com.sjx.annotation.poi.TitleCell;
import com.sjx.annotation.poi.TitleRow;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sjx.annotation.poi.DataCell;
import com.sjx.annotation.poi.DataRow;
import com.sjx.compiler.bean.ConfigBean;
import com.sjx.compiler.bean.ColumnBean;
import com.sjx.compiler.bean.DataCellBean;
import com.sjx.compiler.bean.DataRowBean;
import com.sjx.compiler.bean.TitleRowBean;
import com.sjx.compiler.config.Constant;
import com.sjx.compiler.util.TypeHelper;

import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.omg.CORBA.portable.ValueInputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;
import javax.lang.model.util.Types;

import jdk.nashorn.internal.ir.Block;

/**
 * author： hanwang
 * time: 2020/12/14  9:58
 */
public class DataProviderProcessor extends BaseProcessor {

    //    private Messager messager;


    private void test(ProcessingEnvironment processingEnvironment){
        Elements elements= processingEnvironment.getElementUtils();
        Types types= processingEnvironment.getTypeUtils();

        PackageElement packageElement= elements.getPackageElement("com.sjx.ffmpeg.bean");
        //获取包的简单名称
        String packageName= packageElement.getSimpleName().toString();
        //获取包的全类名
        String totalName=packageElement.getQualifiedName().toString();
        //返回当前元素的父元素
        Element parentElement= packageElement.getEnclosingElement();
        logger.debug("element:"+parentElement);

        logger.debug("totalName:"+totalName);
        logger.debug("packageName:"+packageName);
        //获取包下的顶层的类和接口元素,不包含子包
        List<? extends Element> enclosedElements = packageElement.getEnclosedElements();
        logger.debug("size:"+enclosedElements.size());
        for (int i = 0; i <enclosedElements.size() ; i++) {
            Element element=  enclosedElements.get(i);
            if (!element.getKind().isClass()){
                continue;
            }
            String name=   element.getSimpleName().toString();
            TypeMirror typeMirror=    element.asType();
            List<? extends AnnotationMirror> annotationMirrors = typeMirror.getAnnotationMirrors();
            logger.debug("annotationMirrors:"+annotationMirrors.size());
            for (AnnotationMirror annotationMirror:
                    annotationMirrors) {
            }

            DataRow dataRow =   typeMirror.getAnnotation(DataRow.class);
            logger.debug("transfer:"+ dataRow);

            TypeMirror stringTypeMirror= elements.getTypeElement("java.lang.String").asType();


            //获取一个类下的所有子元素 包括 Field Method Construct static{}
            List<? extends Element> childElements = element.getEnclosedElements();
            for (Element element1:
                    childElements) {
                ElementKind elementKind= element1.getKind();
                if (!elementKind.equals(ElementKind.FIELD)){
                    continue;
                }
                TypeMirror typeMirror1= element1.asType();
                //获取变量的具体类型
                TypeKind typeKind= typeMirror1.getKind();
                logger.debug("type_kind:"+typeKind.toString());

//                String type_element= element1.toString();
//                logger.debug("type_element:"+type_element.toString());
                //不是基础类型 判断是否是自定义类型
                if (!typeKind.isPrimitive()){
                    boolean isStringType= types.isSameType(typeMirror1,stringTypeMirror);
                    logger.debug("is_string:"+isStringType);
                }
                switch (typeKind){
                    case INT:
                        break;
                }
                DataCell dataCell = typeMirror1.getAnnotation(DataCell.class);
                dataCell =element.getAnnotation(DataCell.class);
//                logger.debug("column:"+ dataCell.name());
                logger.debug("child_name:"+element1.getSimpleName());
            }

            if (dataRow ==null){
                continue;
            }

            logger.debug("name:"+name);
//           TypeMirror typeMirror= element.asType();


        }
    }

    //如果没有找到注解 改方法不会被调用
    //annotations  找到的注解类型的集合
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        logger.debug("process:");

        logger.debug("size:"+annotations.size());

        if (annotations.isEmpty()){
            return false;
        }

        classBeanMap=new HashMap<>();
        Set<? extends Element> tableConfigElements = roundEnv.getElementsAnnotatedWith(TableConfig.class);
        logger.debug("size:"+tableConfigElements.size());
        //1.Class ICInfo 作用有TableConfig的类型信息
        for (Element tableConfigElement: tableConfigElements) {
            String name= tableConfigElement.getSimpleName().toString();
            logger.debug("name:"+name);
            if (!tableConfigElement.getKind().isClass()){
                continue;
            }
            TypeElement typeElement= (TypeElement) tableConfigElement;
            ConfigBean configBean = classBeanMap.get(name);
            if (configBean ==null){
                configBean =new ConfigBean();
                configBean.setElement(typeElement);
                classBeanMap.put(name, configBean);
            }
            //获取ICInfo内部的相关元素 ，解析变量信息
            List<DataCellBean> dataCellBeans=new ArrayList<>();
            List<? extends Element> childElements = typeElement.getEnclosedElements();
            for (Element childElement: childElements ) {
                ElementKind kind = childElement.getKind();
                if (!kind.equals(ElementKind.FIELD)&&!kind.equals(ElementKind.METHOD)){
                    continue;
                }

                if (kind.equals(ElementKind.FIELD)){
                    VariableElement typeElement1= (VariableElement) childElement;
                    DataCell dataCell = childElement.getAnnotation(DataCell.class);
                    ConvertName convertName= childElement.getAnnotation(ConvertName.class);
                    ConvertClass convertClass= childElement.getAnnotation(ConvertClass.class);
                    ImageConvert imageConvert= childElement.getAnnotation(ImageConvert.class);
                    DateConvert dateConvert= childElement.getAnnotation(DateConvert.class);
                    DataCellBean dataCellBean=null;
                    if (dataCell !=null){
                        dataCellBean=new DataCellBean();
                        dataCellBean.setDataCell(dataCell);
                        dataCellBean.setConvertClass(convertClass);
                        dataCellBean.setConvertName(convertName);
                        dataCellBean.setImageConvert(imageConvert);
                        dataCellBean.setDateConvert(dateConvert);
                        dataCellBean.setVariableElement(typeElement1);
//                        String columnDefinitionName="columnDefinition_"+childElement.getSimpleName().toString();
//                        columnBean.setColumnDefinitionName(columnDefinitionName);
//                        String columnName= dataCell.name();
//                        int columnIndex= dataCell.index();
//                        CellType cellType = dataCell.type();
//                        logger.debug("name:"+columnName+"-"+columnIndex+"-"+ cellType);
                    }

                    if (dataCellBean!=null){
                        dataCellBeans.add(dataCellBean);
                    }
                }else if (kind.equals(ElementKind.METHOD)){
                    ExecutableElement executableElement= (ExecutableElement) childElement;
                    TitleRow titleRow = childElement.getAnnotation(TitleRow.class);
                    DataRow dataRow= childElement.getAnnotation(DataRow.class);
                    if (titleRow!=null&&dataRow!=null){
                        logger.error("@TitleRow and @DataRow can't add a same method in the same");
                    }
                    if (titleRow==null&&dataRow==null){
                        continue;
                    }
                    if (titleRow!=null){
                        TitleRowBean titleRowBean=new TitleRowBean();
                        titleRowBean.setTypeElement(executableElement);
                        titleRowBean.setTitleRow(titleRow);
                        configBean.addTitleRowBean(titleRowBean);
                    }

                    if (dataRow!=null){
                        DataRowBean dataRowBean=null;
                        if (configBean.getDataRowBeans().size()>0){
                            logger.error("a config class can contain a @DataRow annotation only");
                        }else {
                            dataRowBean=new DataRowBean();
                            configBean.addDataRowBean(dataRowBean);
                        }
                        dataRowBean.setTypeElement(executableElement);
                        dataRowBean.setDataRow(dataRow);

                    }

                }


                logger.debug("className:"+childElement.getSimpleName().toString());


            }
            List<DataRowBean> dataRowBeans = configBean.getDataRowBeans();
            DataRowBean dataRowBean=null;
            if (dataRowBeans.isEmpty()){
                dataRowBean=new DataRowBean();
                dataRowBeans.add(dataRowBean);
            }else {
                dataRowBean=dataRowBeans.get(0);
            }
            dataRowBean.addDataAll(dataCellBeans);
        }

        //2.生成ICInfo$$DataProvider 内部类
        List<TypeSpec> dataProviderTypeSpecs=new ArrayList<>();
        classBeanMap.forEach(new BiConsumer<String, ConfigBean>() {
            @Override
            public void accept(String s, ConfigBean configBean) {
                TypeSpec typeSpec=   generateDataProviderClass(configBean);
                configBean.setTypeSpec(typeSpec);
                dataProviderTypeSpecs.add(typeSpec);
            }
        });

        //2.MyDataProviderFactory 外部工厂类
        List<CodeBlock> codeBlocks=new ArrayList<>();
        codeBlocks.add(CodeBlock.of("$T compositeDataProvider=new $T()",typeMirrors.compositeDataProviderTypeMirror,typeMirrors.defaultCompositeDataProviderTypeMirror));
        for (ConfigBean configBean :classBeanMap.values()){
            if (configBean.getTypeSpec()==null){
                continue;
            }
            codeBlocks.add(CodeBlock.of("compositeDataProvider.registerProvider(new $L())",  configBean.getTypeSpec().name));
        }
        codeBlocks.add(CodeBlock.of(" return compositeDataProvider;"));

        //生成AptDataProviderFactory类型
        MethodSpec methodSpec=MethodSpec.methodBuilder("createDataProvider")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(typeMirrors.compositeDataProviderTypeMirror))
                .addCode(CodeBlock.join(codeBlocks,";\n"))
                .build();
        Types types = typeHelper.getTypes();
        TypeSpec typeSpec=TypeSpec.classBuilder(Constant.DataProviderFactoryInfo.CLASS_NAME_GENERATE)
                .addSuperinterface(
                        ParameterizedTypeName.get(
                                ClassName.get((TypeElement) types.asElement(typeMirrors.dataProviderFactoryTypeMirror)),
                                TypeName.get(typeMirrors.compositeDataProviderTypeMirror)
                        )
                )
                .addMethod(methodSpec)
                .addTypes(dataProviderTypeSpecs)
                .addModifiers(Modifier.PUBLIC).build();
//        JavaFile javaFile= JavaFile.builder("com.sjx.ffmpeg.generated",typeSpec).build();
//        try {
//            javaFile.writeTo(typeHelper.getFiler());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        writeFile(typeSpec);
        return true;
    }


    /**
     *  //生成 ICInfo$$DataProvider类型
     *   class MyDataProvider implements DataProvider {
     *
     *        public void register(TableDefinitionRegistry tableDefinitionRegistry) {
     *
     *            TitleCellDefinition titleCellDefinition=new TitleCellDefinition.Builder()
     *                    .setCellValue("姓名")
     *                    .setCellIndex(0).build();
     *
     *
     *            DataCellDefinition dataCellDefinition =new DataCellDefinition.Builder()
     *                    .setJavaFieldName("name")
     *                    .setCellType(CellType.Text)
     *                    .setColumnIndex(1)
     *                    .build();
     *            TitleRowDefinition titleRowDefinition=new TitleRowDefinition();
     *            titleRowDefinition.registerCellDefinition(titleCellDefinition);
     *            DataRowDefinition dataRowDefinition=new DataRowDefinition();
     *            dataRowDefinition.registerCellDefinition(dataCellDefinition);
     *
     *            TableDefinition tableDefinition =new TableDefinition();
     *            tableDefinition.addTitleRowDefinition(titleRowDefinition);
     *            tableDefinition.addDataRowDefinition(dataRowDefinition);
     *            tableDefinitionRegistry.registerTableDefinition(Test.class,tableDefinition);
     *        }
     *    }
     *
     * @param configBean
     * @return
     */
    private TypeSpec generateDataProviderClass(ConfigBean configBean){
        StringBuilder classNameBuilder=new StringBuilder();

        //1.生成方法参数信息
//        List<TypeHelper.ParameterInfo> parameterInfos= typeHelper.findParameterInfos(Constant.DataProviderInfo.METHOD_NAME_REGISTER).get(1);
//        logger.debug("size:"+parameterInfos.size());
//        List<ParameterSpec> parameterSpecs=new ArrayList<>(parameterInfos.size());
//        for (TypeHelper.ParameterInfo parameterInfo: parameterInfos) {
////            ParameterSpec.builder(TypeName.get(Class.class), typeHelper.findParameterInfo())
//            ParameterSpec parameterSpec= ParameterSpec.builder(ClassName.get(parameterInfo.typeMirror), parameterInfo.parameterName).build();
//            parameterSpecs.add(parameterSpec);
//        }

        ParameterSpec tableDefinitionRegistryParameterSpec= ParameterSpec.builder(ClassName.get(typeMirrors.tableDefinitionRegistryTypeMirror), Constant.ConvertProviderInfo.METHOD_NAME_REGISTRY).build();
        //2.创建并注册TitleCellDefinition
        List<CodeBlock> codeBlocks=new ArrayList<>();
        List<TitleRowBean> titleRowBeans = configBean.getTitleRowBeans();
        CodeBlock titleRowBeanBlock=null;
        for (int i = 0; i <titleRowBeans.size() ; i++) {
            TitleRowBean titleRowBean=titleRowBeans.get(i);
            titleRowBeanBlock=generateTitleDefinitionRegisterBlock(titleRowBean,i);
        }

        //2.创建并注册DataCellDefinition
        List<DataRowBean> dataRowBeans = configBean.getDataRowBeans();
        CodeBlock dataRowBeanBlock=null;
        for (int i = 0; i <dataRowBeans.size() ; i++) {
            DataRowBean dataRowBean=dataRowBeans.get(i);
            logger.debug("DataCellDefinition pre");
            dataRowBeanBlock=generateDataDefinitionRegisterBlock(dataRowBean,i);
            logger.debug("DataCellDefinition post");

        }
        logger.debug("DataCellDefinition");
        codeBlocks.add(titleRowBeanBlock);
        codeBlocks.add(dataRowBeanBlock);

        //3.创建TableDefinition，注册TitleRowDefinition和DataRowDefinition
        List<CodeBlock> tableDefinitionCodeBlocks=new ArrayList<>();
        tableDefinitionCodeBlocks.add(CodeBlock.of("$1T tableDefinition =new $1T()",typeMirrors.tableDefinitionTypeMirror));
        for (int i = 0; i <titleRowBeans.size() ; i++) {
            TitleRowBean titleRowBean=titleRowBeans.get(i);
            tableDefinitionCodeBlocks.add(CodeBlock.of("tableDefinition.addTitleRowDefinition($L)",titleRowBean.getTitleRowDefinitionName()));
        }
        for (int i = 0; i <dataRowBeans.size() ; i++) {
            DataRowBean dataRowBean=dataRowBeans.get(i);
            tableDefinitionCodeBlocks.add(CodeBlock.of("tableDefinition.addDataRowDefinition($L)",dataRowBean.getDataRowDefinitionName()));
        }
        //4.注册TableDefinition
        tableDefinitionCodeBlocks.add(CodeBlock.of("$N.registerTableDefinition($T.class,tableDefinition);",tableDefinitionRegistryParameterSpec,TypeName.get(configBean.getElement().asType())));
        codeBlocks.add(CodeBlock.join(tableDefinitionCodeBlocks,";\n"));
        MethodSpec methodSpec=MethodSpec.methodBuilder(Constant.DataProviderInfo.METHOD_NAME_REGISTER)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addAnnotation(Override.class)
                .addParameter(tableDefinitionRegistryParameterSpec)
                .addCode(CodeBlock.join(codeBlocks,"\n"))
                .build();
        //5.构建DataProvider Class 类
        Element element = configBean.getElement();
        classNameBuilder.append(element.getSimpleName());
        classNameBuilder.append(Constant.DataProviderInfo.CLASS_NAME_SPLIT);
        classNameBuilder.append(Constant.DataProviderInfo.CLASS_NAME_SUFFIX);
        //1.ICInfo$$DataProvider
        TypeSpec typeSpec= TypeSpec.classBuilder(classNameBuilder.toString())
                .addSuperinterface(typeHelper.getTypeMirrors().dataProviderTypeMirror)
                .addMethod(methodSpec).build();
//        typeBuilder.superclass(typeHelper.getTypeMirrors().dataProviderTypeMirror);
//        typeBuilder.addMethod(methodSpec);
//
//
//
//        //5.构建register 方法
//
//        //2.生成ColumnDefinition 信息
//        List<ColumnBean> columnBeans = configBean.getColumnBeans();
//        List<CodeBlock> codeBlocks=new ArrayList<>();
//        for (ColumnBean columnBean : columnBeans) {
//            CodeBlock codeBlock=  generateColumnCodeBlock(columnBean);
//            codeBlocks.add(codeBlock);
//        }
//        //2.注册ColumnDefinition 信息
//        //    ColumnDefinitionRegistry columnDefinitionRegistry=new ColumnDefinitionRegistry();
////            columnDefinitionRegistry.register(0,columnDefinition);
////            configuration.registerClass(registerClass,columnDefinitionRegistry);
//        codeBlocks.add(CodeBlock.of("$1T columnDefinitionRegistry=new $1T()",TypeName.get(typeHelper.getTypeMirrors().columnDefinitionRegistryMirror)));
////        List<CodeBlock> registerCodeBlocks=new ArrayList<>(columnBeans.size());
//        for (ColumnBean columnBean : columnBeans) {
//            CodeBlock codeBlock= CodeBlock.of("columnDefinitionRegistry.register($L,$L)",
//                    columnBean.getDataCell().index(),
//                    columnBean.getColumnDefinitionName()
//            );
//            codeBlocks.add(codeBlock);
//        }
////      CodeBlock registerCodeBlock=  CodeBlock.join(registerCodeBlocks,";");
//        //3.注册ColumnDefinitionRegistry 信息
//        codeBlocks.add( CodeBlock.of("$2N.registerClass($1N,columnDefinitionRegistry);",
//                parameterSpecs.get(0),parameterSpecs.get(1)));
//
//        MethodSpec methodSpec=   MethodSpec.methodBuilder(Constant.DataProviderInfo.METHOD_NAME_REGISTER)
//                .addAnnotation(Override.class)
//                .addModifiers(Modifier.PUBLIC)
//                .returns(TypeName.VOID)
//                .addParameters(parameterSpecs)
//                .addCode(CodeBlock.join(codeBlocks,";\n")).build();
//
//        //组装类
//        Element element = configBean.getElement();
//        classNameBuilder.append(element.getSimpleName());
//        classNameBuilder.append(Constant.DataProviderInfo.CLASS_NAME_SPLIT);
//        classNameBuilder.append(Constant.DataProviderInfo.CLASS_NAME_SUFFIX);
//        //1.ICInfo$$DataProvider
//        TypeSpec.Builder typeBuilder= TypeSpec.classBuilder(classNameBuilder.toString());
////        typeBuilder.addModifiers(Modifier.STATIC);
//        typeBuilder.superclass(typeHelper.getTypeMirrors().dataProviderAdapterTypeMirror);
//        typeBuilder.addMethod(methodSpec);

        return typeSpec;
    }


    /**
     *
     * TitleCellDefinition titleCellDefinition=new TitleCellDefinition.Builder()
     *                        .setCellValue("姓名")
     *                        .setCellIndex(0)
     *                        .setMergeCellNum(2).build();
     *        TitleRowDefinition titleRowDefinition=new TitleRowDefinition();
     *            titleRowDefinition.registerCellDefinition(titleCellDefinition);
     * @param titleRowBean
     * @param index
     * @return
     */
    private CodeBlock generateTitleDefinitionRegisterBlock(TitleRowBean titleRowBean,int index){
        TitleCell[] titles = titleRowBean.getTitles();
        List<CodeBlock> allCodeBlocks=new ArrayList<>();
        List<CodeBlock> titleCellDefinitionBlocks=new ArrayList<>();
        String[] titleCellDefinitionName=new String[titles.length];
        for (int i = 0; i <titles.length ; i++) {
            List<CodeBlock> codeBlocks=new ArrayList<>();
            TitleCell titleCell=titles[i];
            //"titleCellDefinition_"+index+"_"+i name
            StringBuilder nameBuilder=new StringBuilder();
            nameBuilder.append("titleCellDefinition_")
                    .append(index)
                    .append("_")
                    .append(i);
            titleCellDefinitionName[i]=nameBuilder.toString();
            codeBlocks.add(CodeBlock.of("$T $L=new $T()",typeMirrors.titleCellDefinitionTypeMirror,titleCellDefinitionName[i],typeMirrors.titleCellDefinitionBuilderTypeMirror));
            codeBlocks.add(CodeBlock.of(".setCellValue($S)",titleCell.name()));
            codeBlocks.add(CodeBlock.of(".setCellIndex($L)",titleCell.index()));
            codeBlocks.add(CodeBlock.of(".setMergeColumnNum($L)",titleCell.mergeColumnNum()));
            codeBlocks.add(CodeBlock.of(".setMergeRowNum($L)",titleCell.mergeRowNum()));
            codeBlocks.add(CodeBlock.of(".build();"));
            CodeBlock titleCellDefinitionCodeBlock= CodeBlock.join(codeBlocks,"\n");
            titleCellDefinitionBlocks.add(titleCellDefinitionCodeBlock);
        }
        StringBuilder rowDefinitionNameBuilder=new StringBuilder();
        rowDefinitionNameBuilder.append("titleRowDefinition_")
                .append(index);
        List<CodeBlock> rowDefinitionCodeBlocks=new ArrayList<>();
        rowDefinitionCodeBlocks.add(CodeBlock.of("$1T $2L=new $1T();",typeMirrors.titleRowDefinitionTypeMirror,rowDefinitionNameBuilder.toString()));
        for (int i = 0; i <titles.length ; i++) {
            rowDefinitionCodeBlocks.add(CodeBlock.of("$N.registerCellDefinition($N);",rowDefinitionNameBuilder.toString(),titleCellDefinitionName[i]));
        }
        rowDefinitionCodeBlocks.add(CodeBlock.of("$N.setHeight($L);",rowDefinitionNameBuilder.toString(),titleRowBean.getTitleRow().height()));
        rowDefinitionCodeBlocks.add(CodeBlock.of("$N.setSortStrategy($T.$L);",rowDefinitionNameBuilder.toString(),typeMirrors.sortStrategyTypeMirror,titleRowBean.getTitleRow().sortStrategy().toString()));
        titleRowBean.setTitleRowDefinitionName(rowDefinitionNameBuilder.toString());
        allCodeBlocks.add(CodeBlock.join(titleCellDefinitionBlocks,"\n"));

        allCodeBlocks.add(CodeBlock.join(rowDefinitionCodeBlocks,"\n"));

        return CodeBlock.join(allCodeBlocks,"\n");
    }


    /**
     * 基于访问者模式的 @ConvertClass(convertClass =  FileImageConvert.class,simpleArg ="50") 注解访问
     * 普通的注解可以直接获取注解类 包含FileImageConvert.class
     * 注解支持的数据类型
     * 1.byte short int boolean float double long char
     * 2.String
     * 3.Annotation
     * 4.Enum
     * 5.Class
     */
    class ConvertClassAnnotationVisitor extends SimpleAnnotationValueVisitor8<TypeMirror,Void>{
        @Override
        public TypeMirror visitType(TypeMirror typeMirror, Void aVoid) {
            logger.debug("MyAnnotationVisitor:"+typeMirror.toString());
            return typeMirror;
        }
    }


    /**
     *
     *    DataCellDefinition dataCellDefinition =new DataCellDefinition.Builder()
     *                          .setJavaFieldName("name")
     *                          .setCellType(CellType.Text)
     *                          .setColumnIndex(1)
     *                          .setConvertSimpleArg("HH:mm:ss")
     *                          .setConvertName("com.sjx.poi.convert.image.FileImageConvert")
     *                          .build();
     *    DataRowDefinition dataRowDefinition=new DataRowDefinition();
     *    dataRowDefinition.registerCellDefinition(dataCellDefinition);
     * @param dataRowBean
     * @param index
     * @return
     */
    private CodeBlock generateDataDefinitionRegisterBlock(DataRowBean dataRowBean,int index){
        List<DataCellBean> dataCellBeans = dataRowBean.getDataCellBeanList();
        List<CodeBlock> allCodeBlocks=new ArrayList<>();
        String[] dataCellDefinitionName=new String[dataCellBeans.size()];

        List<CodeBlock> dataCellDefinitionCodeBlocks=new ArrayList<>();
        for (int i = 0; i <dataCellBeans.size() ; i++) {
            List<CodeBlock> codeBlocks=new ArrayList<>();
            DataCellBean dataCellBean=dataCellBeans.get(i);
            VariableElement variableElement=  dataCellBean.getVariableElement();
            logger.debug("type:"+variableElement);
            VariableElement cellElement = dataCellBean.getVariableElement();
//            List<? extends AnnotationMirror> annotationMirrors = variableElement1.getAnnotation();
//            for (AnnotationMirror annotationMirror: annotationMirrors) {
//              logger.debug("annotationMirror:"+  annotationMirror.toString());
//                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
//                elementValues.forEach(new BiConsumer<ExecutableElement, AnnotationValue>() {
//                    @Override
//                    public void accept(ExecutableElement executableElement, AnnotationValue annotationValue) {
//                        //注解方法   convertClass()
//                        logger.debug("exe:"+executableElement.toString());
//                        logger.debug("value:"+annotationValue.toString());
//                        ConvertClassAnnotationVisitor valueVisitor8=new ConvertClassAnnotationVisitor();
//                        annotationValue.accept(valueVisitor8,null);
//
//
//                    }
//                });
//
//            }
            DataCell dataCell = dataCellBean.getDataCell();

            ConvertClass convertClass = dataCellBean.getConvertClass();
            ConvertName convertName = dataCellBean.getConvertName();
//            DateConvert dateConvert = dataCellBean.getDateConvert();
//            ImageConvert imageConvert = dataCellBean.getImageConvert();


            //"dataCellDefinition_"+index+"_"+i name
            StringBuilder nameBuilder=new StringBuilder();
            nameBuilder.append("dataCellDefinition_")
                    .append(index)
                    .append("_")
                    .append(i);
            dataCellDefinitionName[i]=nameBuilder.toString();
            codeBlocks.add(CodeBlock.of("$T $L=new $T()",typeMirrors.dataCellDefinitionTypeMirror,dataCellDefinitionName[i],typeMirrors.dataCellDefinitionBuilderTypeMirror));
            codeBlocks.add(CodeBlock.of(".setJavaFieldName($S)",variableElement.getSimpleName().toString()));
            codeBlocks.add(CodeBlock.of(".setCellType($T.$L)",typeMirrors.cellTypeTypeMirror,dataCell.type().toString()));
            codeBlocks.add(CodeBlock.of(".setCellScope($T.$L)",typeMirrors.cellScopeTypeMirror,dataCell.scope().toString()));
            codeBlocks.add(CodeBlock.of(".setColumnIndex($L)",dataCell.index()));
            codeBlocks.add(CodeBlock.of(".setMergeColumnNum($L)",dataCell.mergeColumnNum()));
            codeBlocks.add(CodeBlock.of(".setMergeRowNum($L)",dataCell.mergeRowNum()));

            String convertRegistryName=null;
            String simpleArg=null;
            if (convertClass!=null){
                logger.debug("simpleArg:"+convertClass.simpleArg());
//                logger.debug("simpleArg:"+convertClass.convertClass());
               TypeMirror typeMirror= getConvertClassType(variableElement);
                convertRegistryName=typeMirror.toString();
                simpleArg=convertClass.simpleArg();
                logger.debug("simpleArg:"+convertClass.simpleArg());
                logger.debug("convertName:"+convertRegistryName);
            }else if (convertName!=null){
                logger.debug("simpleArg:"+convertName.simpleArg());
                logger.debug("simpleArg:"+convertName.convertName());
                convertRegistryName= convertName.convertName();
                simpleArg=convertName.simpleArg();
            }else {
               TypeKind typeKind= variableElement.asType().getKind();
                logger.debug("typeKindCustom:"+variableElement.asType().toString());

                switch (typeKind){
                   case BOOLEAN:
                       convertRegistryName=Constant.PrimitiveConvertName.BOOLEAN_STRING_CONVERT;
                       break;
                   case BYTE:
                       convertRegistryName=Constant.PrimitiveConvertName.BYTE_STRING_CONVERT;
                       break;
                   case SHORT:
                       convertRegistryName=Constant.PrimitiveConvertName.SHORT_STRING_CONVERT;
                       break;
                   case INT:
                       convertRegistryName=Constant.PrimitiveConvertName.INTEGER_STRING_CONVERT;
                       break;
                   case FLOAT:
                       convertRegistryName=Constant.PrimitiveConvertName.FLOAT_STRING_CONVERT;
                       break;
                   case DOUBLE:
                       convertRegistryName=Constant.PrimitiveConvertName.DOUBLE_STRING_CONVERT;
                       break;
                   case LONG:
                       convertRegistryName=Constant.PrimitiveConvertName.LONG_STRING_CONVERT;
                       break;
                   case CHAR:
                       convertRegistryName=Constant.PrimitiveConvertName.CHARACTER_STRING_CONVERT;
                   break;
                    default:
                        if (variableElement.asType().toString().equals("java.lang.String")){
                            convertRegistryName=Constant.PrimitiveConvertName.STRING_STRING_CONVERT;
                        }
                        break;
               }
            }
//            else if (dateConvert!=null){
//                convertRegistryName= da.convertName();
//                simpleArg=dateConvert.format();
//            }else if (imageConvert!=null){
//                convertRegistryName= convertName.convertName();
//                simpleArg=String.valueOf(imageConvert.quality());
//            }
            codeBlocks.add(CodeBlock.of(".setConvertName($S)",convertRegistryName));
            codeBlocks.add(CodeBlock.of(".setConvertSimpleArg($S)",simpleArg));
            codeBlocks.add(CodeBlock.of(".build();"));
            CodeBlock dataCellDefinitionCodeBlock= CodeBlock.join(codeBlocks,"\n");
            dataCellDefinitionCodeBlocks.add(dataCellDefinitionCodeBlock);
        }
        StringBuilder rowDefinitionNameBuilder=new StringBuilder();
        rowDefinitionNameBuilder.append("dataRowDefinition_")
                .append(index);
        List<CodeBlock> rowDefinitionCodeBlocks=new ArrayList<>();
        rowDefinitionCodeBlocks.add(CodeBlock.of("$1T $2L=new $1T();",typeMirrors.dataRowDefinitionTypeMirror,rowDefinitionNameBuilder.toString()));
        for (int i = 0; i <dataCellBeans.size() ; i++) {
            rowDefinitionCodeBlocks.add(CodeBlock.of("$N.registerCellDefinition($N);",rowDefinitionNameBuilder.toString(),dataCellDefinitionName[i]));
        }
        rowDefinitionCodeBlocks.add(CodeBlock.of("$N.setHeight($L);",rowDefinitionNameBuilder.toString(),dataRowBean.getDataRow().height()));
        rowDefinitionCodeBlocks.add(CodeBlock.of("$N.setSortStrategy($T.$L);",rowDefinitionNameBuilder.toString(),typeMirrors.sortStrategyTypeMirror,dataRowBean.getDataRow().sortStrategy().toString()));

        dataRowBean.setDataRowDefinitionName(rowDefinitionNameBuilder.toString());
        allCodeBlocks.add(CodeBlock.join(dataCellDefinitionCodeBlocks,"\n"));

        allCodeBlocks.add(CodeBlock.join(rowDefinitionCodeBlocks,"\n"));
        logger.debug("size:"+allCodeBlocks.size());
        return CodeBlock.join(allCodeBlocks,"\n");
    }


    /**
     * @ConvertClass(convertClass =  FileImageConvert.class,simpleArg ="50") 获取FileImageConvert对应的TypeMirror
     * @param variableElement
     * @return
     */
    public TypeMirror getConvertClassType(VariableElement variableElement) {
        List<? extends AnnotationMirror> annotationMirrors = variableElement.getAnnotationMirrors();
         TypeMirror[] targets=new TypeMirror[1];
         for (AnnotationMirror annotationMirror : annotationMirrors) {
            logger.debug("annotationMirror:" + annotationMirror.toString());
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            boolean isSame = typeHelper.getTypes().isSameType(annotationType, typeMirrors.convertClassAnnotationTypeMirror);
            if (!isSame) {
                continue;
            }

            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
            if (elementValues==null||elementValues.isEmpty()){
                continue;
            }
            elementValues.forEach(new BiConsumer<ExecutableElement, AnnotationValue>() {
                @Override
                public void accept(ExecutableElement executableElement, AnnotationValue annotationValue) {
                    //注解方法   convertClass()
                    logger.debug("methodName:" + executableElement.getSimpleName().toString());
                    logger.debug("exe:" + executableElement.toString());
                    logger.debug("value:" + annotationValue.toString());
                    String methodName= executableElement.getSimpleName().toString();
                    if (methodName.equals("convertClass")){
                        ConvertClassAnnotationVisitor valueVisitor8 = new ConvertClassAnnotationVisitor();
                        TypeMirror  mirror= annotationValue.accept(valueVisitor8, null);
                        logger.debug("value:"+mirror.toString());
                        targets[0]=mirror;
                    }


                }
            });

        }
         return targets[0];
    }

    /**
     *    ColumnDefinition columnDefinition=new ColumnDefinition.Builder<TextTypeConfig>()
     *             .setColumnName("姓名")
     *             .setJavaColumnName("name")
     *             .setTypeConfig(new TextTypeConfig())
     *             .setColumnType(ColumnType.Text)
     *             .setColumnIndex(1)
     *             .build();
     *
     * @return
     */
//    private CodeBlock generateColumnCodeBlock(ColumnBean columnBean){
//        //    ColumnDefinition columnDefinition=new ColumnDefinition.Builder<TextTypeConfig>()
//        DataCell dataCell =columnBean.getDataCell();
//        Element element= columnBean.getElement();
//        TypeHelper.TypeMirrors typeMirrors= typeHelper.getTypeMirrors();
//        List<CodeBlock> codeBlocks=new ArrayList<>();
//        codeBlocks.add(CodeBlock.of("$1T $3L=new $2T<TextTypeConfig>()",
//                TypeName.get(typeMirrors.columnDefinitionTypeMirror),
//                TypeName.get(typeMirrors.columnDefinitionBuilderTypeMirror),
//                columnBean.getColumnDefinitionName()
//        ));
//        //            .setColumnName("姓名")
//        codeBlocks.add( CodeBlock.of("setColumnName($S)", dataCell.name()));
//        //            .setJavaColumnName("name")
//        codeBlocks.add( CodeBlock.of("setJavaColumnName($S)",element.getSimpleName().toString()));
//        //            .setTypeConfig(new TextTypeConfig())
//        codeBlocks.add(   CodeBlock.of("setTypeConfig(new $T())",typeMirrors.textTypeConfigTypeMirror));
////            .setColumnType(ColumnType.Text)
//        codeBlocks.add(CodeBlock.of("setColumnType($T.Text)",typeMirrors.columnTypeTypeMirror));
//        //    .setColumnIndex(1)
//        codeBlocks.add( CodeBlock.of("setColumnIndex($L)", dataCell.index()));
//        codeBlocks.add( CodeBlock.of("build()"));
//
//        return CodeBlock.join(codeBlocks,".\n");
//
//    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> strings=new HashSet<>();
        strings.add(TableConfig.class.getName());
        strings.add(TitleRow.class.getName());
        strings.add(TitleCell.class.getName());
        strings.add(DataRow.class.getName());
        strings.add(DataCell.class.getName());
        strings.add(ImageConvert.class.getName());
        strings.add(DateConvert.class.getName());
        strings.add(ConvertClass.class.getName());
        strings.add(ConvertName.class.getName());
//        strings.add(SnPath.class.getName());
        return strings;
    }

}