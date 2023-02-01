package com.sjx.poi.core;

import android.text.TextUtils;

import com.sjx.poi.config.Configuration;
import com.sjx.poi.convert.ConvertProvider;
import com.sjx.poi.convert.ConvertProviderFactory;
import com.sjx.poi.convert.InternalConvertProviderFactory;
import com.sjx.poi.dataprovider.DataProvider;
import com.sjx.poi.dataprovider.DataProviderFactory;
import com.sjx.poi.executor.PlatformFactory;
import com.sjx.poi.executor.TransferPoolExecutor;
import com.sjx.poi.request.TransferRequestManager;
import com.sjx.poi.transfer.TableFactory;
import com.sjx.poi.util.Preconditions;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;


public class EasyPoi {


    private Configuration configuration;
    private List<DataProviderFactory> dataProviderFactories;
    private List<ConvertProviderFactory> convertProviderFactories;
    private TableFactory tableFactory;
    private TransferRequestManager transferRequestManager;
    private TransferPoolExecutor transferExecutor;
    private Executor callbackExecutor;
    private String generatePackageName;

    private EasyPoi(Builder builder){
        configuration=new Configuration();
        this.dataProviderFactories=builder.dataProviderFactories;
        this.convertProviderFactories=builder.convertProviderFactories;
        this.tableFactory=builder.tableFactory;
        this.transferExecutor=builder.transferExecutor;
        this.callbackExecutor=builder.callbackExecutor;
        this.generatePackageName=builder.generatePackageName;
        this.transferRequestManager=new TransferRequestManager(transferExecutor);

        init();
    }


    private void init(){
        //find generate factory
        DataProviderFactory generateDataProviderFactory= findFactory(DataProviderFactory.class);
        ConvertProviderFactory generateConvertProviderFactory= findFactory(ConvertProviderFactory.class);
        if (generateDataProviderFactory!=null){
            dataProviderFactories.add(generateDataProviderFactory);
        }
        if (generateConvertProviderFactory!=null){
            convertProviderFactories.add(generateConvertProviderFactory);
        }

        //register TableDefinition
        Iterator<DataProviderFactory> dataProviderFactoryIterator = dataProviderFactories.iterator();
        while (dataProviderFactoryIterator.hasNext()){
            DataProviderFactory dataProviderFactory = dataProviderFactoryIterator.next();
            DataProvider dataProvider = dataProviderFactory.createDataProvider();
            dataProvider.register(configuration.getTableDefinitionRegistry());
        }
        //register custom convert
        Iterator<ConvertProviderFactory> convertProviderFactoryIterator = convertProviderFactories.iterator();
        while (convertProviderFactoryIterator.hasNext()){
            ConvertProviderFactory convertProviderFactory=convertProviderFactoryIterator.next();
            ConvertProvider convertProvider = convertProviderFactory.createConvertProvider();
            convertProvider.register(configuration.getConvertRegistry());
        }
        //register common convert

    }

    /**
     *
     * find AptDataProviderFactory and AptConvertProviderFactory
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T findFactory(Class<T> clazz){
        String className=null;
        if (DataProviderFactory.class.isAssignableFrom(clazz)){
            className=generatePackageName+".AptDataProviderFactory";
        }else if (ConvertProviderFactory.class.isAssignableFrom(clazz)){
            className=generatePackageName+".AptConvertProviderFactory";
        }
        if (TextUtils.isEmpty(className)){
            return null;
        }
        Object object=null;
        try {
            Class targetClazz=  Class.forName(className);
            object=  targetClazz.newInstance();
        } catch (ClassNotFoundException e) {
        }catch (InstantiationException e){

        }catch (IllegalAccessException e){

        }
        return (T) object;
    }

    public TransferPoolExecutor getTransferExecutor() {
        return transferExecutor;
    }

    public Executor getCallbackExecutor() {
        return callbackExecutor;
    }

    public TransferRequestManager getTransferRequestManager() {
        return transferRequestManager;
    }

    public TableFactory getTableFactory() {
        return tableFactory;
    }

    public <T> T getService(Class<T> t){
        return (T) Proxy.newProxyInstance(t.getClassLoader(),new Class[]{t},new ServiceProxy(this));
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public static class Builder{
        private List<DataProviderFactory> dataProviderFactories;
        private List<ConvertProviderFactory> convertProviderFactories;
        private TableFactory tableFactory;
        private TransferPoolExecutor transferExecutor;
        private Executor callbackExecutor;
        private String generatePackageName;

        public Builder(){
            dataProviderFactories=new ArrayList<>();
            convertProviderFactories=new ArrayList<>();
        }


        /**
         * 设置自动生成的文件的包名
         * @param packageName
         * @return
         */
        public Builder setGeneratePackageName(String packageName) {
            this.generatePackageName = packageName;
            return this;
        }

        /**
         * 导入导出的执行器
         * @param executor
         * @return
         */
        public Builder setTransferExecutor(TransferPoolExecutor executor) {
            this.transferExecutor = executor;
            return this;
        }

        /**
         * 回调的执行器
         * @param executor
         * @return
         */
        public Builder setCallbackExecutor(Executor executor) {
            this.callbackExecutor = executor;
            return this;
        }

        /**
         * 设置POI表格核心工厂
         * @param tableFactory
         * @return
         */
        public Builder setTableFactory(TableFactory tableFactory) {
            this.tableFactory = tableFactory;
            return this;
        }

        /**
         *
         * 添加POI表格配置数据注册工厂
         * @param dataProviderFactory
         * @return
         */
        public Builder addDataProviderFactory(DataProviderFactory dataProviderFactory) {
            dataProviderFactories.add(Preconditions.checkNotNull(dataProviderFactory,"can't add a null DataProviderFactory "));
            return this;
        }

        /**
         *
         * 添加数据类型装换提供者工厂
         * @param convertProviderFactory
         * @return
         */
        public Builder addConvertProviderFactory(ConvertProviderFactory convertProviderFactory) {
            convertProviderFactories.add(Preconditions.checkNotNull(convertProviderFactory,"can't add a null ConvertProviderFactory "));
            return this;
        }

        public Builder setExcelType( int excelType) {
            return this;
        }

        public EasyPoi build(){
            Preconditions.checkNotNull(generatePackageName,"GeneratePackageName is can't null");

            Preconditions.checkNotNull(tableFactory,"TableFactory is can't null");
            convertProviderFactories.add(new InternalConvertProviderFactory());
            PlatformFactory platformFactory=new PlatformFactory.DefaultPlatformFactory();
            PlatformFactory.Platform platform= platformFactory.createPlatform();
            if (callbackExecutor==null){
                callbackExecutor=platform.callbackExecutor();
            }
            if (transferExecutor==null){
                transferExecutor=platform.transferExecutor();
            }

            return new EasyPoi(this);
        }

    }


}