package com.sjx.easypoi.example;

import com.sjx.annotation.poi.Data;
import com.sjx.annotation.poi.Lazy;
import com.sjx.annotation.poi.Listener;
import com.sjx.annotation.poi.Path;
import com.sjx.annotation.poi.Sheet;
import com.sjx.annotation.poi.SkipRow;
import com.sjx.annotation.poi.Tag;
import com.sjx.annotation.poi.Transfer;
import com.sjx.annotation.poi.TransferType;
import com.sjx.poi.listener.TransferListener;

import java.util.List;

/**
 * 导入导出的接口
 * TransferType.Export 标记导出方法
 * TransferType.Import 标记导入方法
 * @Path 导入导出的文件路径
 * @Listener 标记监听器 类型应该是TransferListener
 * @Sheet 标记导出的工作表名称
 * @Tag 请求tag 用来后续查询导出导入状态
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
    void importData(@Path String path,
                    @Listener TransferListener transferListener,
                    @SkipRow int[] skipRows);
}