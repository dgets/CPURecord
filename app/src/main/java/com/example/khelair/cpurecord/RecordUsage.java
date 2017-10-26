package com.example.khelair.cpurecord;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
        appShit = getApplicationContext();

        TextView statsBox = (TextView) findViewById(R.id.txtStats);

        int[][] deviceStats = new int[CPUProbe.MAX_COARS][5];

        int total, idle, iowait, irq, softirq;
        int cntr = 0;

        if (debugging >= METHOD_CALLS) {
            Toast.makeText(appShit, "fillStatsListView()",
                    Toast.LENGTH_SHORT).show();
        }

        statsBox.setMovementMethod(new ScrollingMovementMethod());
        statsBox.setText("");

        //why is this line causing a crash?
        deviceStats = CPUProbe.probeStats(appShit);

        for (int cntr2 = 0; cntr2 < 3; cntr2++) {
            idle = deviceStats[cntr2][0]; iowait = deviceStats[cntr2][1];
            irq = deviceStats[cntr2][2]; softirq = deviceStats[cntr2][3];

            /*for (int statline : deviceStats[cntr2]) {
                idle = statline[0];
                iowait = statline[1];
                irq = statline[2];
                softirq = statline[3];*/

                total = (idle + iowait + irq + softirq) / 100;  //percentage work
                if (debugging >= GENERAL) {
                    Toast.makeText(appShit, "total: " + total +
                            "\nidle: " + idle + "\niowait: " + iowait +
                            "\nirq: " + irq + "\nsoftirq: " + softirq,
                            Toast.LENGTH_SHORT).show();
                }

                try {
                    statsBox.append("\nCore: " + cntr2 + "\n\tIdle: " +
                            ((float)idle / total) + "%\tIO Wait: " + ((float)iowait / total));
                    statsBox.append("%\n\tIRQ: " + ((float)irq / total) + "%\tSoft IRQ: " +
                            ((float)softirq / total) + "%\n\n");
                } catch (Exception e) {
                    Toast.makeText(appShit, "cntr: " + cntr2 + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            //}
        }
    }

}
