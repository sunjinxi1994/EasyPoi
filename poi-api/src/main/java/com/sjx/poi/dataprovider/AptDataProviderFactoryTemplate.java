package com.sjx.poi.dataprovider;

import com.sjx.poi.config.Configuration;
import com.sjx.poi.config.TableDefinitionRegistry;

/**
 * author： hanwang
 * time: 2020/12/16  12:48
 */
public class AptDataProviderFactoryTemplate implements DataProviderFactory<CompositeDataProvider> {


    @Override
    public CompositeDataProvider createDataProvider()  {
        CompositeDataProvider compositeDataProvider=new DefaultCompositeDataProvider();
//         registerProvider(new ICInfo$$$DataProvider());
        compositeDataProvider.registerProvider(new ICInfo$$$DataProvider());

        return compositeDataProvider;
    }

//    private void registerProvider(String className,CompositeDataProvider aptDataProvider,DataProvider dataProvider) throws ClassNotFoundException {
////            Class clazz=Class.forName(className);
//            Class clazz=Thread.currentThread().getContextClassLoader().loadClass(className);
//            aptDataProvider.registerProvider(clazz,dataProvider);
//    }

//     class AptDataProvider extends DefaultCompositeDataProvider{
//        @Override
//        public void register(String name, Configuration configuration) {
//           DataProvider dataProvider= retrieveProvider(name);
//            if (dataProvider!=null){
//                dataProvider.register(name,configuration);
//            }
//        }
//    }

     class ICInfo$$$DataProvider implements DataProvider{

         @Override
         public void register(TableDefinitionRegistry tableDefinitionRegistry) {

         }
     }
     class IDInfo$$$DataProvider implements DataProvider{
         @Override
         public void register(TableDefinitionRegistry tableDefinitionRegistry) {

         }
     }
}