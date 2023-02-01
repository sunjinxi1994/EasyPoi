package com.sjx.poi.core;

import android.text.TextUtils;
import android.util.Log;

import com.sjx.annotation.poi.CellType;
import com.sjx.poi.config.Configuration;
import com.sjx.poi.config.DataCellDefinition;
import com.sjx.poi.config.DataRowDefinition;
import com.sjx.poi.config.TableDefinition;
import com.sjx.poi.config.TableDefinitionRegistry;
import com.sjx.poi.config.TitleCellDefinition;
import com.sjx.poi.config.TitleRowDefinition;
import com.sjx.poi.convert.ConvertRegistry;
import com.sjx.poi.convert.image.DefaultImageConvert;
import com.sjx.poi.convert.image.ImageConvert;
import com.sjx.poi.convert.text.DefaultTextConvert;
import com.sjx.poi.convert.text.TextConvert;
import com.sjx.poi.listener.TransferListener;
import com.sjx.poi.listener.TransferObservable;
import com.sjx.poi.transfer.ExportTable;
import com.sjx.poi.transfer.TableFactory;
import com.sjx.poi.util.PoiLogger;
import com.sjx.poi.util.Preconditions;
import com.sjx.poi.util.ReflectUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author： hanwang
 * time: 2020/9/30  10:54
 */
 class ExportCommandExecutor extends AbstractTransferCommandExecutor {

     private static final String TAG="ExportCommand";

     private  static AtomicInteger atomicInteger=new AtomicInteger();

    @Override
    public void executeCommand(CommandArguments commandArguments) throws Throwable{
        PoiLogger.e(TAG,"execute");
//        Object[] args = commandArguments.getArgs();
        EasyPoi easyPoi= commandArguments.getEasyPoi();
        Configuration configuration=easyPoi.getConfiguration();
//        ServiceMethod serviceMethod = commandArguments.getServiceMethod();
        TransferObservable transferObservable = commandArguments.getTransferObservable();
        ExportParameter exportParameter= commandArguments.getExportParameter();
        String path=exportParameter.getPath().getValue();
        Collection data=exportParameter.getData().getValue();
        TransferListener listener=exportParameter.getListener().getValue();
        String sheet=exportParameter.getSheet().getValue();
        if (TextUtils.isEmpty(sheet)){
            sheet="sheet_"+atomicInteger.getAndIncrement();
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

        Class dataType=exportParameter.getData().getActualType();
        TableDefinitionRegistry tableDefinitionRegistry= configuration.getTableDefinitionRegistry();
        TableDefinition tableDefinition= tableDefinitionRegistry.retrieveTableDefinition(dataType);
        TableFactory tableFactory = easyPoi.getTableFactory();
        ExportTable exportTable = tableFactory.createExportTable();

        exportTable.createTable(path);

        exportTable.createSheet(sheet);
        List<TitleRowDefinition> titleRowDefinitions = tableDefinition.getTitleRowDefinitions();

        for (int i = 0; i <titleRowDefinitions.size() ; i++) {
            TitleRowDefinition titleRowDefinition = titleRowDefinitions.get(i);
            int cellNum = titleRowDefinition.getCellNum();
            int column=0;
            for (int cellIndex = 0; cellIndex < cellNum; cellIndex++) {
                TitleCellDefinition cellDefinition = titleRowDefinition.getCellDefinition(cellIndex);
                String text = cellDefinition.getCellValue();
                exportTable.addTextCell(i,i+=cellDefinition.getMergeRowNum(), column,column+=cellDefinition.getMergeColumnNum(), text);
                column++;
            }
            verifyState();
        }


        List<DataRowDefinition> dataRowDefinitions = tableDefinition.getDataRowDefinitions();
//        for (int i = 0; i <dataRowDefinitions.size() ; i++) {
//            DataRowDefinition dataRowDefinition=dataRowDefinitions.get(i);
//            int cellNum = dataRowDefinition.getCellNum();
//            for (int j = 0; j < cellNum; j++) {
//                dataRowDefinition.getCellDefinition(j);
//            }
//        }
        ConvertRegistry convertRegistry=configuration.getConvertRegistry();
        DataRowDefinition dataRowDefinition=dataRowDefinitions.get(0);
        Iterator iterator = data.iterator();
        int row=1;
        while (iterator.hasNext()){
            Object value= iterator.next();
//            Class clazz= value.getClass();
            int column=0;
            for (int cellNum = 0;cellNum <dataRowDefinition.getCellNum() ;cellNum++) {
                DataCellDefinition dataCellDefinition= dataRowDefinition.getCellDefinition(cellNum);
                String javaFieldName = dataCellDefinition.getJavaFieldName();
                Object fieldValue= ReflectUtil.getMemberField(value,javaFieldName);
                String convertName= dataCellDefinition.getConvertName();
                String simpleArg= dataCellDefinition.getConvertSimpleArg();

//               Convert convert=null;
//               if (!TextUtils.isEmpty(convertName)){
//                  convert= convertRegistry.findConvertByName(convertName);
//               }else {
//                  convert=convertRegistry.findConvertByType(DefaultConvert.class);
//               }
//               Object object=  convert.convert(fieldValue,simpleArg);
//                clazz.getField(javaFieldName);
                //查找Convert
                CellType cellType= dataCellDefinition.getCellType();
                ImageConvert imageConvert=null;
                TextConvert  textConvert=null;
                if (cellType.equals(CellType.Image)){
                    imageConvert=  convertRegistry.findConvertByNameAndType(convertName, ImageConvert.class);
                    if (imageConvert==null){
                        imageConvert=convertRegistry.findConvertByType(DefaultImageConvert.class);
                    }
                }else if (cellType.equals(CellType.Text)){
                    textConvert=convertRegistry.findConvertByNameAndType(convertName, TextConvert.class);
                    if (textConvert==null){
                        textConvert=convertRegistry.findConvertByType(DefaultTextConvert.class);
                    }
                }
                if (cellType.equals(CellType.Image)){
                    byte[] imageData =  imageConvert.exportConvert(fieldValue,simpleArg);
                    if (!Preconditions.isEmpty(imageData)){
                        PoiLogger.e(TAG,"addImage:"+imageData.length);
                        exportTable.addImageCell(row,row+=dataCellDefinition.getMergeRowNum(),column,column+=dataCellDefinition.getMergeColumnNum(),imageData);
                    }
                }else if (cellType.equals(CellType.Text)){
                    String textData =  textConvert.exportConvert(fieldValue,simpleArg);
                    if (!TextUtils.isEmpty(textData)){
                        exportTable.addTextCell(row,row+=dataCellDefinition.getMergeRowNum(),column,column+=dataCellDefinition.getMergeColumnNum(),textData);
                    }

                }
                column++;

            }
            transferObservable.onProgressUpdate(data.size(),row,value);
            row++;
            verifyState();
            if (isStop()){
                break;
            }

        }
        try {
            exportTable.writeTable();
        } catch (IOException e) {
           throw e;
        }finally {
            exportTable.finish();
        }


//        List<TitleRowDefinition> titleRowDefinitions = tableDefinition.getTitleRowDefinitions();
//        Iterator iterator = datas.iterator();
        //1.导出标题
//        configuration.getTableDefinitionRegistry().retrieveTableDefinition()

//        while (iterator.hasNext()){
//           Object object= iterator.next();
//           Class clazz=  object.getClass();
//          TableDefinition tableDefinition= configuration.retrieveTableDefinition(clazz.getName());
//            List<TitleCellDefinition> titleCellDefinitions = tableDefinition.getTitleCellDefinitions();
//            for (int i = 0; i <titleCellDefinitions.size() ; i++) {
//                TitleCellDefinition titleCellDefinition=titleCellDefinitions.get(i);
//                HSSFCell hssfCell= hssfRow.createCell(i);
//                hssfCell.setCellValue("aa");
//            }
//
//
//        }

//        serviceMethod.getParameterHandler()
        transferObservable.onEnd();

    }
}