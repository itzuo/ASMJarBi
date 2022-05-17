package com.zxj.asmjarbi;

import android.app.Application;

import com.blankj.utilcode.util.Utils;


public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
       /* DataReport.setContext(this)
                .setDebugMode(DataReport.TEST_MODEL) //设置正式模式还是测试模式  DataReport.REGULAR_MODEL/DataReport.TEST_MODEL
                .setOverSeaMode(DataReport.CN_MODEL) //设置海外模式还是国内模式 DataReport.OVERSEA_MODEL/DataReport.CN_MODEL
                .setIfLoginOnOtherProcess(false) //设置是否需要在其他进程上报login字段
                .init();*/
    }
}
