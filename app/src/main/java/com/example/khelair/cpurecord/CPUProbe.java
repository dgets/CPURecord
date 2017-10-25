package com.example.khelair.cpurecord;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        //welp, since this refuses to work, maybe a List will help...
        int[][] cpuStats = new int[MAX_COARS][4];
        //List perCoarStats = new ArrayList<Integer>();

        int coars = 0;

        CPUDetails wut = new CPUDetails(ctxt);

        try {
            coars = wut.getCoars();
            for (int cntr = 0; cntr < coars; cntr++) {
                //Toast.makeText(ctxt, "Coar: " + cntr, Toast.LENGTH_SHORT).show();
                cpuStats[cntr] = wut.getCPUUsage(ctxt, cntr);
                //perCoarStats.add(wut.getCPUUsage(ctxt, cntr));
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
                Toast.makeText(ctxt, "Coar siblings: " + coars,
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(ctxt, "Usage entries (core 0): " +
                        wut.getCPUUsage(ctxt, 0), Toast.LENGTH_LONG).show();
            }

            //heah
            for (int cntr = 0; cntr < coars; cntr++) {
                for (int perCoarEntry : wut.getCPUUsage(ctxt, cntr)) {
                    Toast.makeText(ctxt, perCoarEntry, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(ctxt, "2: " + e,
                    Toast.LENGTH_LONG).show();
        }
    }
}