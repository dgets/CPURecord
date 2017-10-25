package com.example.khelair.cpurecord;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecordUsage extends AppCompatActivity {

    //'constants'
    public static final int GENERAL = 1;
    public static final int METHOD_CALLS = 2;

    public static final int debugging = GENERAL;
    //public static final int debugging = METHOD_CALLS;

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


        CPUProbe ouah = new CPUProbe(appShit);

        if (debugging >= METHOD_CALLS) {
            Toast.makeText(appShit, "getDebugInfos()",
                    Toast.LENGTH_SHORT).show();
        }

        if (debugging >= GENERAL) {
            CPUProbe.displayInfo();
        }

    }

    public void fillStatsListView(View view) {
        //ListView statsView = (ListView) findViewById(R.id.lvwStats);
        TextView statsBox = (TextView) findViewById(R.id.txtStats);

        int[][] deviceStats = new int[CPUProbe.MAX_COARS][4];

        int total, idle, iowait, irq, softirq;
        int cntr = 0;

        deviceStats = CPUProbe.probeStats();

        for (int[] statline : deviceStats) {
            idle = statline[0]; iowait = statline[1];
            irq = statline[2]; softirq = statline[3];

            total = idle + iowait + irq + softirq;

            statsBox.append("\nCore: " + cntr++ + "\tIdle: " +
                    (idle / total) + "%\tIO Wait: " + (iowait / total) +
                    "%\tIRQ: " + (irq / total) + "%\tSoft IRQ: " +
                    (softirq / total) + "%");
        }
    }

}
