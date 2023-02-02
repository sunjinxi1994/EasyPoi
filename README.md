# EasyPoi

## 1.简介

​    使用注解和apt对java中的poi库二次封装，适用于android平台，可以快速实现对Excel表格的操作，支持以下功能：

1. 支持底层poi框架的切换，自带常用的框架poi和jxl的实现。
2. 支持java对象和表格列的映射，包含标题行和数据行。
3. 数据行注解支持文本和图片，跨行和列排序已经java数据类型和表格数据的转换。
4. 支持表格和java映射类的自动生成和动态添加。
5. 支持导出导入过程的监控，包括进度、异常、开始和结束等。

## 2.使用

### 2.1 gradle配置

```groovy
 defaultConfig {
       //......
        javaCompileOptions{
            annotationProcessorOptions{
                //指定apt生成的代码的存放位置
                arguments=[
                        MODULE_NAME:project.getName(),
                        PACKAGE_POI_GENERATE:'com.sjx.easypoi.example'
                ]
            }
        }
    }
```

在gradle中添加依赖

```java
dependencies {
    //sdk库依赖
    implementation ('io.github.sunjinxi1994:poi-api:1.0.1')
    //注解处理器    
    annotationProcessor ('io.github.sunjinxi1994:poi-compiler:1.0.0')
}
```

### 2.2 接口定义

​       定义一个接口，添加导入和导出的方法。

```java
/**
 * 导入导出的接口
 * TransferType.Export 标记导出方法
 * TransferType.Import 标记导入方法
 * @Path 导入导出的文件路径
 * @Listener 标记监听器 类型应该是TransferListener
 * @Sheet 标记导出的工作表名称
 * @Tag 请求tag 用来后续查询导出导入状态
 * @SkipRow 导入使用 用来跳过某行 可以用来跳过处理标题
 **/
public interface Export {
    @Transfer(type = TransferType.Export)
    String export(@Path String path,
                  @Data List<ICInfo> data,
                  @Listener TransferListener transferListener,
                  @Sheet String sheetName,
                  @Tag String tag,
                  @Lazy boolean lazy);
    
    @Transfer(type = TransferType.Import)
    void importData(@Path String path,@Listener TransferListener transferListener,
                    @SkipRow int[] skipRows
                   );
}
```

### 2.3 数据类的定义

```java
/**
 * @TableConfig 用来标记一个java bean类 一个java类对应一张数据表
 * 数据表由标题行和数据行组成
 * @TitleRow 用来定义标题行的属性 包括height=标题高度 sortStrategy=排序方式 TitleCell=单元格
 * @DataRow 用来定义数据行的属性
 * @DataCell 用来定义数据单元格
 * @ConvertClass 用来指定数据转换器
 **/
@TableConfig
public class ICInfo implements Serializable {



   @TitleRow(
         height = 200,
         sortStrategy = SortStrategy.SORT_INDEX,
         titles = {
               @TitleCell(index = 0,name = "姓名"),
               @TitleCell(index = 1,name = "身份证号"),
               @TitleCell(index = 2,name = "人员类型"),
               @TitleCell(index = 4,name = "ID号"),
               @TitleCell(index = 5,name = "IC卡号"),
               @TitleCell(index = 6,name = "打卡时间"),
               @TitleCell(index = 7,name = "设备名称"),
               @TitleCell(index = 8,name = "识别方式"),
               @TitleCell(index = 9,name = "对比得分"),
               @TitleCell(index = 10,name = "是否通过"),
               @TitleCell(index = 11,name = "是否上传"),
               @TitleCell(index = 12,name = "人体温度"),
               @TitleCell(index = 13,name = "口罩佩戴"),
               @TitleCell(index = 14,name = "登记照片",mergeColumnNum = 1),
               @TitleCell(index = 15,name = "实时照片",mergeColumnNum = 1),
         }
   )
   public void configTitleRow(){

   }

   @DataRow(height =100,sortStrategy = SortStrategy.SORT_INDEX)
   public void configDataRow(){

   }



   // 身份证号_性别_出生年月日_发证机关_姓名_民族_有效期
   /**
    * 姓名
    */
   @DataCell(index = 1,type = CellType.Text)
   private String name;
   /**
    * 身份证号
    */
   @DataCell(index = 2,type = CellType.Text)
   private String idNum;
   /**
    * 人员类型
    */
   @DataCell(index = 3,type = CellType.Text)
   private char userType;
   /**
    * 工号
    */
   @DataCell(index =4,type = CellType.Text)
   private String jobNum;
   /**
    * ic卡号
    */
   @DataCell(index =4,type = CellType.Text)
   private String icNum;

   /**
    * 比对时间
    */
   @ConvertClass(convertClass = DateConvert.class,simpleArg ="YYYY-MM-dd HH:mm:ss")
   @DataCell(index =5,type = CellType.Text)
   private Date compareTime;


   /**
    * 设备名称
    */
   @DataCell(index =5,type = CellType.Text)
   private String deviceName;

   /**
    * 识别方式
    */
   @DataCell(index =7,type = CellType.Text)
   private int recognitionType;

   /**
    * 对比得分
    */
   @DataCell(index =8,type = CellType.Text)
   private float compareScore;

   /**
    * 是否通过
    */
   @DataCell(index =9,type = CellType.Text)
   private byte isPass;

   /**
    * 是否上传
    */
   @DataCell(index =10,type = CellType.Text)
   private byte isUpload;;


   /**
    * 人体温度
    */
   @DataCell(index =10,type = CellType.Text)
   private float tamper;;
   /**
    * 口罩佩戴
    */
   @DataCell(index =9,type = CellType.Text)
   private short mask;
   /**
    * 登记照片
    */
   @ConvertClass(convertClass = FileImageConvert.class,simpleArg ="50")
   @DataCell(index =9,type = CellType.Image,mergeColumnNum = 1,scope = CellScope.EXPORT)
   private String registerPhoto;
   /**
    * 实时照片
    */
   @ConvertClass(convertClass =  FileImageConvert.class,simpleArg ="50")
   @DataCell(index =9,type = CellType.Image,mergeColumnNum = 1,scope = CellScope.EXPORT)
   private String capturePhoto;


   /**
    * 登记照片原始数据
    */
   @DataCell(index =9,type = CellType.Image,mergeColumnNum = 1,scope = CellScope.IMPORT)
   private byte[] registerPhotoRaw;
   /**
    * 抓拍照片原始数据
    */
   @DataCell(index =9,type = CellType.Image,mergeColumnNum = 1,scope = CellScope.IMPORT)
   private byte[] capturePhotoRaw;


   /**
    * 比对时间 日期格式
    *
    */
// @ConvertClass(convertClass = DateConvert.class,simpleArg ="YYYY-MM-dd HH:mm:ss")
// @DataCell(index =5,type = CellType.Text)
// private Date compareDate;

   /**
    * base64 照片
    */
   @ConvertClass(convertClass = CustomConvert.class)
   @DataCell(index =9,type = CellType.Image,mergeColumnNum = 1)
   private String base64Photo;

   /**
    * 加密 照片
    */
   @ConvertClass(convertClass = CustomConvert.class,simpleArg = "arg")
   @DataCell(index =9,type = CellType.Image,mergeColumnNum = 1)
   private String encryptPhoto;
    
   //set/get 
 }  
```

### 2.4 代码调用

#### 2.4.1 初始化

```java
 easyPoi= new EasyPoi.Builder()
//                .addDataProviderFactory(new AptDataProviderFactory())
//                .addConvertProviderFactory(new AptConvertProviderFactory())
                .setGeneratePackageName("com.sjx.easypoi.example")
                .setTableFactory(new JxlTableFactory())
                .build();
```

#### 2.4.2 导出数据

```java
Export export=  easyPoi.getService(Export.class);
String path=POI_DIR+"/user.xls";
List<ICInfo> icInfos=new ArrayList<>();
for (int i=0;i<50;i++){
    ICInfo icInfo=new ICInfo();
    //组装数据
    icInfos.add(icInfo);
}
//如果lazy设置为true 调用export不会直接导出 当后面调用transferRequest.start();的时候才会开始导出
String tag=   export.export(path, icInfos, new TransferListener<ICInfo>() {
    @Override
    public void onBeforeExecute() {
        Log.e(TAG,"onBeforeExecute");
    }

    @Override
    public void onStart() {
        String name= Thread.currentThread().getName();
        Log.e(TAG,"onStart:"+name);
    }

    @Override
    public void onProgressUpdate(int num, int progress,ICInfo icInfo) {
        Log.e(TAG,"num:"+num+"progress:"+progress);
        textView.setText("num:"+num+"pro:"+progress);
        seekBar.setMax(num);
        seekBar.setProgress(progress);
    }

    @Override
    public void onEnd() {
        Log.e(TAG,"onEnd");

    }

    @Override
    public void onAfterExecute(Throwable throwable) {
        if (throwable!=null)
            Log.e(TAG,"throwable:"+throwable.toString());

    }
},"record","record",true);
Log.e(TAG,tag);
transferRequestManager = easyPoi.getTransferRequestManager();
//根据tag查询对应的TransferRequest 可以通过TransferRequest控制传输的开始 暂停 恢复等
transferRequest=transferRequestManager.getRequestByTag(tag);
this.tag=tag;
```

#### 2.4.3 导入数据

```java
 Export export=  easyPoi.getService(Export.class);
            String path=POI_DIR+"/user.xls";
            export.importData(path, new TransferListenerAdapter<ICInfo>() {

                @Override
                public void onBeforeExecute() {
                    super.onBeforeExecute();
                    Log.e(TAG,"onBeforeExecute_import");
                }

                @Override
                public void onProgressUpdate(int num, int progress, ICInfo data) {
                    super.onProgressUpdate(num, progress, data);
                    Log.e(TAG,"num:"+num+"_progress:"+progress);
                    textView.setText("num:"+num+"pro:"+progress);
                    seekBar.setMax(num);
                    seekBar.setProgress(progress);
                    if (data!=null){
                        byte[] registerRaw= data.getRegisterPhotoRaw();
                        byte[] captureRaw=data.getCapturePhotoRaw();
//                Log.e(TAG,registerRaw.length+"-"+captureRaw.length);
                        String registerPath= POI_DIR+File.separator+"poi-test/image_register";
                        String capturePath= POI_DIR+File.separator+"poi-test/image_capture";
                        FileUtil.saveImage(registerRaw,registerPath,data.getIdNum()+"_"+progress+".jpg");
                        FileUtil.saveImage(captureRaw,capturePath,data.getIdNum()+"_"+progress+".jpg");
                        Log.e(TAG,data.toString());
                    }else {
                        Log.e(TAG,"skip");
                    }


                }

                @Override
                public void onAfterExecute(Throwable throwable) {
                    super.onAfterExecute(throwable);
                    if (throwable!=null){
                        Log.e(TAG,throwable.toString());
                    }else {
                        Log.e(TAG,"onAfterExecute_import");

                    }
                }
            },
                    //指定跳过标题栏
                    new int[]{0});
```

#### 2.4.4 请求控制

```java
//停止数据传输
public void stop() {
    transferRequestManager.stop(tag);
}
//恢复数据传输
public void resume(View view) {
    transferRequestManager.resume(tag);
}
//暂停数据传输
public void pause(View view) {
    transferRequestManager.pause(tag);
}
```
