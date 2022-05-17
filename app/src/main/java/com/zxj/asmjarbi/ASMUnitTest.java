package com.zxj.asmjarbi;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

public class ASMUnitTest {

    private Context mContext;

    public void init() {
        /*if(mContext != null){
            Log.e("zuojie","mContext is null");
            return ;
        }*/
        mContext = Utils.getApp();
        Log.e("zuojie","mContext is "+mContext);
        // 原本的逻辑代码
    }

    public void test(){
        // 原本的逻辑代码
        System.out.println("123");
        if(LogConfig.LOGD_ENABLED) {
            Log.e("zuojie", "mContext is " + mContext);
        }
        // 原本的逻辑代码
        System.out.println("456");
    }
}
