package com.example.khelair.cpurecord;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RecordUsage extends AppCompatActivity {

    //'constants'
    public static final int readingDelay = 3000;    //between readings (ms)
    public static final int debugging = 1;

    public Context appShit; // = this.getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_usage);

        appShit = getApplicationContext();
    }

    public void onManualRecordClick(View view) {

    }

    public void getDebugInfos(View view) {
        appShit = getApplicationContext();

        if (debugging >= 2) {
            Toast.makeText(appShit, "getDebugInfos()",
                    Toast.LENGTH_SHORT).show();
        } else if (debugging >= 1) {
            CPUProbe.displayInfo(appShit);
        }
    }
}
