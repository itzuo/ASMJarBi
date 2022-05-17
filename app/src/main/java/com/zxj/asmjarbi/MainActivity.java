package com.zxj.asmjarbi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import bi.com.tcl.bi.DataReport;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*HashMap<String, String> map = new HashMap<String, String>();
        map.put("type","35A1A9E436");
        DataReport.custReport(map);*/
        DataReport.custErrorReport("",null);
    }
}