package com.example.khelair.cpurecord;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Damon Getsman on 10/19/17.
 */

public class CPUProbe {
    public static Context ctxt;

    //constructor(s)
    public CPUProbe(Context c) {
        this.ctxt = c;
    }

    public void gatherCPUInfo() {
        //determine coars

    }

    public void probeStats() {

    }

    public static void displayInfo(Context aShit) {
        CPUDetails wut = new CPUDetails(ctxt);

        //god ouah
        if (RecordUsage.debugging >= 2) {
            Toast.makeText(aShit, "displayInfo()", Toast.LENGTH_SHORT).show();
        } else if (RecordUsage.debugging >= 1) {
            Toast.makeText(aShit, "Coar siblings: " +
                    wut.getCoars(aShit), Toast.LENGTH_LONG).show();
        }
    }
}