package com.example.khelair.cpurecord;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RecordUsage extends AppCompatActivity {

    //'constants'
    public static final int GENERAL = 1;
    public static final int METHOD_CALLS = 2;

    public static final int debugging = METHOD_CALLS;
    //public static final int readingDelay = 3000;    //between readings (ms)

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
        appShit = getApplicationContext();  //duplicate wtf
        CPUProbe ouah = new CPUProbe(appShit);

        if (debugging >= METHOD_CALLS) {
            Toast.makeText(appShit, "getDebugInfos()",
                    Toast.LENGTH_SHORT).show();
        }

        if (debugging >= GENERAL) {
            CPUProbe.displayInfo();
        }
    }
}
