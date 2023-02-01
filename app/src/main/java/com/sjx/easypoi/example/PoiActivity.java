package com.sjx.easypoi.example;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sjx.easypoi.BuildConfig;
import com.sjx.easypoi.R;
import com.sjx.poi.core.EasyPoi;
import com.sjx.poi.listener.TransferListener;
import com.sjx.poi.listener.TransferListenerAdapter;
import com.sjx.poi.request.TransferRequest;
import com.sjx.poi.request.TransferRequestManager;
import com.sjx.poi.transfer.jxl.JxlTableFactory;
import com.sjx.poi.util.PoiLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * author： hanwang
 * time: 2020/12/31  12:46
 */
public class PoiActivity extends AppCompatActivity {


    private static final String TAG="PoiActivity";
    private TextView textView;
    private SeekBar seekBar;
    private TransferRequest transferRequest;
    private TransferRequestManager transferRequestManager;
    private String tag;

    private EasyPoi easyPoi;
    private static final String POI_DIR=Environment.getExternalStorageDirectory().getAbsolutePath()+"/poi-test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);
        textView=findViewById(R.id.tv_progress);
        seekBar=findViewById(R.id.sb_progress);
        init();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
            List<String> deniedPermissions=new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                int result= ContextCompat.checkSelfPermission(this, permissions[i]);
                if (result==PackageManager.PERMISSION_DENIED){
                    deniedPermissions.add(permissions[i]);
                }
            }
            if (!deniedPermissions.isEmpty()){
                String[] requestPermissions=new String[deniedPermissions.size()];
                for (int i = 0; i < requestPermissions.length; i++) {
                    requestPermissions[i]=deniedPermissions.get(i);
                }
                requestPermissions(requestPermissions,100);
            }else {
//                startImport();
            }
        }else {
//            startImport();
        }
    }


    private void init(){
         easyPoi= new EasyPoi.Builder()
//                .addDataProviderFactory(new AptDataProviderFactory())
//                .addConvertProviderFactory(new AptConvertProviderFactory())
                .setGeneratePackageName("com.sjx.easypoi.example")
                .setTableFactory(new JxlTableFactory())
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100){
            boolean isSuccess=true;
            for (int i = 0; i <grantResults.length ; i++) {
                if (grantResults[i]==PackageManager.PERMISSION_DENIED){
                    isSuccess=false;
                }
            }
            if (isSuccess){
//                startImport();
            }
        }
    }


    /**
     * 导出表数据到文件
     * 导出图片 需要在/sdcard/poi-test 下准备两张图片capture.jpg和register.jpg
     * @param view
     */
    public void startExport(View view){

        File file=new File(POI_DIR);
        if (!file.exists()){
            file.mkdirs();
        }
        PoiLogger.setEnable(BuildConfig.DEBUG);
        //获取调用接口
        Export export=  easyPoi.getService(Export.class);
        String path=POI_DIR+"/user.xls";
        List<ICInfo> icInfos=new ArrayList<>();
        for (int i=0;i<50;i++){
            ICInfo icInfo=new ICInfo();
            icInfo.setName("Danny");
            icInfo.setIdNum("135531199812152546");
            icInfo.setIcNum("784915633");
            icInfo.setJobNum("4589723");
            icInfo.setDeviceName("移动设备");
            icInfo.setTamper(35.6f);
            icInfo.setMask((short) 1);
            icInfo.setIsPass((byte) 0);
            icInfo.setIsUpload((byte) 0);
            icInfo.setUserType('1');
            icInfo.setRecognitionType(1);
            icInfo.setCompareScore(0.95f);
            icInfo.setCompareTime(new Date());
            icInfo.setCapturePhoto(POI_DIR+"/capture.jpg");
            icInfo.setRegisterPhoto(POI_DIR+"/register.jpg");
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
    }

    /**
     * @param view
     * 导入数据
     */
    public void importData(View view) {
            PoiLogger.setEnable(BuildConfig.DEBUG);
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
    }





    public void stop(View view) {
        transferRequestManager.stop(tag);
    }

    public void resume(View view) {
        transferRequestManager.resume(tag);
    }

    public void pause(View view) {
        transferRequestManager.pause(tag);
    }

    public void start(View view) {
        String tag= transferRequest.getTag();
        Log.e(TAG,transferRequest.getState().name());
        transferRequest.start();
    }

    public void printInfo(View view) {
        Log.e(TAG,transferRequest.getState().toString());
        TransferRequest request= transferRequestManager.getRequestByTag(tag);
        Log.e(TAG,"request:"+request);
    }


}