package com.sjx.poi.core;

import com.sjx.annotation.poi.CellScope;
import com.sjx.annotation.poi.CellType;
import com.sjx.poi.config.Configuration;
import com.sjx.poi.config.DataCellDefinition;
import com.sjx.poi.config.DataRowDefinition;
import com.sjx.poi.config.TableDefinition;
import com.sjx.poi.config.TableDefinitionRegistry;
import com.sjx.poi.convert.ConvertRegistry;
import com.sjx.poi.convert.image.DefaultImageConvert;
import com.sjx.poi.convert.image.ImageConvert;
import com.sjx.poi.convert.text.DefaultTextConvert;
import com.sjx.poi.convert.text.TextConvert;
import com.sjx.poi.listener.TransferListener;
import com.sjx.poi.listener.TransferObservable;
import com.sjx.poi.transfer.ImportTable;
import com.sjx.poi.transfer.TableFactory;
import com.sjx.poi.util.PoiLogger;
import com.sjx.poi.util.ReflectUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * author： hanwang
 * time: 2020/9/30  10:54
 */
class ImportCommandExecutor extends AbstractTransferCommandExecutor {

    private static final String TAG="ImportCommand";


    @Override
    public void executeCommand(CommandArguments commandArguments) throws Throwable {
        PoiLogger.e(TAG,"execute");
//        Object[] args = commandArguments.getArgs();
        EasyPoi easyPoi= commandArguments.getEasyPoi();
        Configuration configuration=easyPoi.getConfiguration();
//        ServiceMethod serviceMethod = commandArguments.getServiceMethod();
        TransferObservable transferObservable = commandArguments.getTransferObservable();
        ExportParameter exportParameter= commandArguments.getExportParameter();
        String path=exportParameter.getPath().getValue();
        TransferListener listener=exportParameter.getListener().getValue();
        Type type= listener.getClass().getGenericSuperclass();
        Class actualType=null;
        if (type instanceof ParameterizedType){
            ParameterizedType parameterizedType= (ParameterizedType) type;
            actualType= (Class) parameterizedType.getActualTypeArguments()[0];
            PoiLogger.e(TAG,"actualType:"+actualType);
        }
        boolean lazy=false;
        if (exportParameter.getLazy()!=null){
            lazy=exportParameter.getLazy().value;
        }
        //注册参数中的监听器
        transferObservable.addListener(listener);
        if (lazy){
            //需要调用start的时候才会继续执行
            waitForLazy();
        }
        transferObservable.onStart();

        TableDefinitionRegistry tableDefinitionRegistry= configuration.getTableDefinitionRegistry();
        TableDefinition tableDefinition= tableDefinitionRegistry.retrieveTableDefinition(actualType);
        TableFactory tableFactory = easyPoi.getTableFactory();
        ImportTable importTable = tableFactory.createImportTable();
        DataRowDefinition dataRowDefinition = tableDefinition.getDataRowDefinitions().get(0);
        ConvertRegistry convertRegistry=configuration.getConvertRegistry();
        try {
            importTable.createTable(path);
            int sheetNum = importTable.getSheetNum();
            int sum=0;
            for (int i = 0; i < sheetNum; i++) {
                sum+=importTable.getRowNum(i);
            }
            int progress=0;
            for (int i = 0; i <sheetNum; i++) {
                int rowNum = importTable.getRowNum(i);
                for (int j = 0; j <rowNum ; j++) {
                    try {
                        Object data=    actualType.newInstance();
                        int cellNum = dataRowDefinition.getCellNum();
                        int skip=0;
                        int column=0;
                        for (int k = 0; k <cellNum; k++) {
                            DataCellDefinition cellDefinition = dataRowDefinition.getCellDefinition(k);
                            CellScope cellScope = cellDefinition.getCellScope();
                            if (cellScope.equals(CellScope.EXPORT)){
                                continue;
                            }
                            CellType cellType = cellDefinition.getCellType();
                            String convertName = cellDefinition.getConvertName();
                            String convertSimpleArg = cellDefinition.getConvertSimpleArg();
                            Object value=null;
                            if (cellType.equals(CellType.Text)){
                                String rawValue= importTable.getTextValue(i,j,column);
                                TextConvert textConvert=  convertRegistry.findConvertByNameAndType(convertName, TextConvert.class);
                                if (textConvert==null){
                                    textConvert=convertRegistry.findConvertByType(DefaultTextConvert.class);
                                }
                                value= textConvert.importConvert(rawValue,convertSimpleArg);
                            }else if (cellType.equals(CellType.Image)){
                                byte[] rawValue= importTable.getImageValue(i,j,column);
                                ImageConvert imageConvert=  convertRegistry.findConvertByNameAndType(convertName, ImageConvert.class);
                                if (imageConvert==null){
                                    imageConvert=convertRegistry.findConvertByType(DefaultImageConvert.class);
                                }
                                value= imageConvert.importConvert(rawValue,convertSimpleArg);
                            }
                            ReflectUtil.setFieldValue(data,cellDefinition.getJavaFieldName(),value);
                            column+=cellDefinition.getMergeColumnNum()+1;
                        }
                        transferObservable.onProgressUpdate(sum,++progress,data);
                    }catch (Exception e){
                        PoiLogger.e(TAG,e.toString());
                    }finally {

                    }
                }

            }
        }catch (Exception e){
            throw e;
        }finally {
            importTable.finish();
        }

    }
}