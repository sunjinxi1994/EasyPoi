package com.sjx.compiler.config;

/**
 * author： hanwang
 * time: 2020/12/16  18:18
 */
public class Constant {



    public static class TableDefinitionInfo{
        public static final String CLASS_NAME_FULL_TABLE_DEFINITION="com.sjx.poi.config.TableDefinition";
        public static final String CLASS_NAME_FULL_TABLE_DEFINITION_REGISTRY="com.sjx.poi.config.TableDefinitionRegistry";

    }

    public static class DataDefinitionInfo{
        public static final String CLASS_NAME_FULL_DATA_CELL_DEFINITION_ROW="com.sjx.poi.config.DataRowDefinition";
        public static final String CLASS_NAME_FULL_DATA_CELL_DEFINITION="com.sjx.poi.config.DataCellDefinition";
        public static final String CLASS_NAME_FULL_DATA_CELL_DEFINITION_BUILDER="com.sjx.poi.config.DataCellDefinition.Builder";
        public static final String CLASS_NAME_FULL_DATA_CELL_DEFINITION_ROW_SORT_STRATEGY="com.sjx.annotation.poi.SortStrategy";

    }

    public static class TitleDefinitionInfo{
        public static final String CLASS_NAME_FULL_TITLE_CELL_DEFINITION_ROW="com.sjx.poi.config.TitleRowDefinition";
        public static final String CLASS_NAME_FULL_TITLE_CELL_DEFINITION="com.sjx.poi.config.TitleCellDefinition";
        public static final String CLASS_NAME_FULL_TITLE_CELL_DEFINITION_BUILDER="com.sjx.poi.config.TitleCellDefinition.Builder";
    }


    public static class CellType{
        public static final String CLASS_NAME_FULL_CELL_TYPE="com.sjx.annotation.poi.CellType";
    }
    public static class CellScope{
        public static final String CLASS_NAME_FULL_CELL_TYPE="com.sjx.annotation.poi.CellScope";
    }
    public static class ProcessorOptionKey{
        public static final String KEY_MODULE_NAME="MODULE_NAME";
        public static final String KEY_PACKAGE_POI_GENERATE="PACKAGE_POI_GENERATE";

    }

    /**
     * DataProvider
     */
    public static class DataProviderInfo{
        public static final String CLASS_NAME_SPLIT="$$";
        public static final String CLASS_NAME_SUFFIX="DataProvider";
        public static final String CLASS_NAME_FULL="com.sjx.poi.dataprovider.DataProvider";
        public static final String METHOD_NAME_REGISTER="register";


        public static final String CLASS_NAME_FULL_COMPOSITE="com.sjx.poi.dataprovider.CompositeDataProvider";
        public static final String CLASS_NAME_FULL_COMPOSITE_DEFAULT="com.sjx.poi.dataprovider.DefaultCompositeDataProvider";
        public static final String CLASS_NAME_FULL_ADAPTER="com.sjx.poi.dataprovider.DataProviderAdapter";
        public static final String PARAMETER_NAME_REGISTRY="tableDefinitionRegistry";

    }

    /**
     * DataProviderFactory
     */
    public static class DataProviderFactoryInfo{
        public static final String CLASS_NAME_GENERATE="AptDataProviderFactory";
        public static final String CLASS_NAME_FULL="com.sjx.poi.dataprovider.DataProviderFactory";

    }



    public static class ConvertInfo{
        public static final String CLASS_NAME_BASE="com.sjx.poi.convert.";
        public static final String CLASS_NAME_CONVERT_IMAGE_FULL="com.sjx.poi.convert.";

    }

    public static class ConvertProviderInfo extends ConvertInfo{
        public static final String CLASS_NAME_GENERATE="AptConvertProvider";
        public static final String CLASS_NAME_CONVERT_PROVIDER_FULL=CLASS_NAME_BASE+"ConvertProvider";
        public static final String CLASS_NAME_CONVERT_REGISTRY_FULL=CLASS_NAME_BASE+"ConvertRegistry";

        public static final String METHOD_NAME_REGISTRY="register";

        public static final String PARAMETER_NAME_REGISTRY="convertRegistry";


    }
    public static class ConvertProviderFactoryInfo extends ConvertInfo{
        public static final String CLASS_NAME_GENERATE="AptConvertProviderFactory";
        public static final String CLASS_NAME_CONVERT_PROVIDER_FACTORY_FULL=CLASS_NAME_BASE+"ConvertProviderFactory";

        public static final String METHOD_NAME_CREATE="createConvertProvider";

    }

    public static class AnnotationInfo {
        public static final String ANNOTATION_NAME_CONVERT_CLASS="com.sjx.annotation.convert.ConvertClass";

    }

    public static class PrimitiveConvertName{
        public static final String  BOOLEAN_STRING_CONVERT="boolean_string_convert";
        public static final String  BYTE_STRING_CONVERT="byte_string_convert";
        public static final String  SHORT_STRING_CONVERT="short_string_convert";
        public static final String  INTEGER_STRING_CONVERT="integer_string_convert";
        public static final String  FLOAT_STRING_CONVERT="float_string_convert";
        public static final String  DOUBLE_STRING_CONVERT="double_string_convert";
        public static final String  LONG_STRING_CONVERT="long_string_convert";
        public static final String  CHARACTER_STRING_CONVERT="character_string_convert";
        public static final String  STRING_STRING_CONVERT="string_string_convert";

    }

}