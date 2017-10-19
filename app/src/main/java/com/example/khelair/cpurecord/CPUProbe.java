package com.example.khelair.cpurecord;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Damon Getsman on 10/19/17.
 */

public class CPUProbe {
    //constant(s)
    public static final int MAX_COARS = 16;
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

    public static void displayInfo() {
        int[][] cpuStats = new int[MAX_COARS][4];

        CPUDetails wut = new CPUDetails(ctxt);

        try {
            for (int cntr = 0; cntr < wut.getCoars(); cntr++) {
                cpuStats[cntr] = wut.getCPUUsage(ctxt, cntr);
            }
        } catch (Exception e) {
            Toast.makeText(ctxt, "1: " + e,
                    Toast.LENGTH_LONG).show();
        }

        //god ouah
        if (RecordUsage.debugging >= RecordUsage.METHOD_CALLS) {
            Toast.makeText(ctxt, "displayInfo()", Toast.LENGTH_SHORT).show();
        }

        try {
            if (RecordUsage.debugging >= RecordUsage.GENERAL) {
                Toast.makeText(ctxt, "Coar siblings: " +
                        wut.getCoars(), Toast.LENGTH_SHORT).show();
                Toast.makeText(ctxt, "Usage entries (core 0): " +
                        wut.getCPUUsage(ctxt, 0), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(ctxt, "2: " + e,
                    Toast.LENGTH_LONG).show();
        }
    }
}