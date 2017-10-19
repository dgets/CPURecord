package com.example.khelair.cpurecord;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RecordUsage extends AppCompatActivity {

    //'constants'
    public static final int readingDelay = 3000;    //between readings (ms)
    public static final boolean debugging = true;

    Context appShit = ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_usage);
    }

    public void onManualRecordClick(View view) {
        CPUProbe.displayInfo(appShit);
    }
}
