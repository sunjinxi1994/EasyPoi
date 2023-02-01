package com.sjx.easypoi.example;

import com.sjx.annotation.poi.Data;
import com.sjx.annotation.poi.Lazy;
import com.sjx.annotation.poi.Listener;
import com.sjx.annotation.poi.Path;
import com.sjx.annotation.poi.Sheet;
import com.sjx.annotation.poi.Tag;
import com.sjx.annotation.poi.Transfer;
import com.sjx.annotation.poi.TransferType;
import com.sjx.poi.listener.TransferListener;

import java.util.List;


public interface Export {


    @Transfer(type = TransferType.Export)
    String export(@Path String path,
                  @Data List<ICInfo> data,
                  @Listener TransferListener transferListener,
                  @Sheet String sheetName,
                  @Tag String tag,
                  @Lazy boolean lazy);


    @Transfer(type = TransferType.Import)
    void importData(@Path String path,@Listener TransferListener transferListener);
}