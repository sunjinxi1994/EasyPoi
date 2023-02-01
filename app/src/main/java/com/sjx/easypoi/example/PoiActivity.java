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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);
        textView=findViewById(R.id.tv_progress);
        seekBar=findViewById(R.id.sb_progress);
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


    private void startImport(){
        PoiLogger.setEnable(BuildConfig.DEBUG);
        EasyPoi easyPoi= new EasyPoi.Builder()
//                .addDataProviderFactory(new AptDataProviderFactory())
//                .addConvertProviderFactory(new AptConvertProviderFactory())
                .setGeneratePackageName("com.sjx.easypoi.example")
                .setTableFactory(new JxlTableFactory())
                .build();
        Export export=  easyPoi.getService(Export.class);
        String path="/sdcard/poi-test/user.xls";
        export.importData(path, new TransferListenerAdapter<ICInfo>() {
            @Override
            public void onProgressUpdate(int num, int progress, ICInfo data) {
                super.onProgressUpdate(num, progress, data);
                byte[] registerRaw= data.getRegisterPhotoRaw();
                byte[] captureRaw=data.getCapturePhotoRaw();
                Log.e(TAG,registerRaw.length+"-"+captureRaw.length);
                String registerPath= Environment.getExternalStorageDirectory().toString()+File.separator+"poi-test/image_register";
                String capturePath= Environment.getExternalStorageDirectory().toString()+File.separator+"poi-test/image_capture";
                FileUtil.saveImage(registerRaw,registerPath,data.getIdNum()+"_"+progress+".jpg");
                FileUtil.saveImage(captureRaw,capturePath,data.getIdNum()+"_"+progress+".jpg");
                Log.e(TAG,data.toString());
            }

            @Override
            public void onAfterExecute(Throwable throwable) {
                super.onAfterExecute(throwable);
                if (throwable!=null){
                    Log.e(TAG,throwable.toString());
                }
            }
        });
    }

    public void startExport(View view){
        PoiLogger.setEnable(BuildConfig.DEBUG);
        EasyPoi easyPoi= new EasyPoi.Builder()
//                .addDataProviderFactory(new AptDataProviderFactory())
//                .addConvertProviderFactory(new AptConvertProviderFactory())
                .setTableFactory(new JxlTableFactory())
                .setGeneratePackageName("com.sjx.easypoi.example")

                .build();
        Export export=  easyPoi.getService(Export.class);
//                Export export2=  easyPoi.getService(Export.class);
        PoiLogger.e(TAG,export+"");
//               PoiLogger.e(TAG,export2+"");

        String path="/sdcard/poi-test/user.xls";
        List<ICInfo> icInfos=new ArrayList<>();
        for (int i=0;i<50;i++){
            ICInfo icInfo=new ICInfo();
            icInfo.setName("Danny");
            icInfo.setIdNum("130531199308053611");
            icInfo.setIcNum("784915633");
            icInfo.setJobNum("4838");
            icInfo.setDeviceName("新人证类设备");
            icInfo.setTamper(35.6f);
            icInfo.setMask((short) 1);
            icInfo.setIsPass((byte) 0);
            icInfo.setIsUpload((byte) 0);
            icInfo.setUserType('1');
            icInfo.setRecognitionType(1);
            icInfo.setCompareScore(0.95f);
            icInfo.setCompareTime(new Date());
            icInfo.setCapturePhoto("/sdcard/poi-test/capture.jpg");
            icInfo.setRegisterPhoto("/sdcard/poi-test/register.jpg");
            icInfos.add(icInfo);
        }
        String tag=   export.export(path, icInfos, new TransferListener<ICInfo>() {
            @Override
            public void onBeforeExecute() {
                PoiLogger.e(TAG,"onBeforeExecute");
                if (Log.isLoggable(TAG,Log.ERROR)){
                    Log.e(TAG,"onBeforeExecute");
                }

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
        transferRequest=transferRequestManager.getRequestByTag(tag);
        this.tag=tag;
    }

    public void stop(View view) {
    }

    public void resume(View view) {
        transferRequestManager.resume(tag);
    }

    public void pause(View view) {
        transferRequestManager.pause(tag);
    }

    public void start(View view) {
        String tag= transferRequest.getTag();
        Log.e(TAG,tag);
        Log.e(TAG,transferRequest.getState().name());

        transferRequest.start();
    }

    public void printInfo(View view) {
        Log.e(TAG,transferRequest.getState().toString());
        TransferRequest request= transferRequestManager.getRequestByTag(tag);
        Log.e(TAG,"request:"+request);
    }

    public void importData(View view) {
        startImport();
    }
}