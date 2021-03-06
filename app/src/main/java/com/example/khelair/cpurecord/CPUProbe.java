package com.example.khelair.cpurecord;

import android.content.Context;
import android.widget.Toast;

/**
 * This class is used for initiating probes into the kernel's CPU hardware
 * entries in /proc.  It utilizes the methods of CPUDetails for this.
 * CPUDetails will later be made a subclass of CPUProbe.
 *
 * @author Damon Getsman
 * @since 2017-10-19
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

    /**
     * This method probes the kernel statistics on time spent in different
     * modes on each of the cores.
     *
     * @param ctxt
     * @return int[][] - array of 4 processor states for each core
     */
    public static int[][] probeStats(Context ctxt) throws Exception {
        CPUDetails processor = new CPUDetails(ctxt);    //phase out ctxt
        int[][] curProcStats = new int[MAX_COARS][5];
        int coars = processor.getCoars(ctxt);
        int cntr = 0;

        for (; cntr < MAX_COARS; cntr++) {

            if (cntr < coars) {
                try {
                    curProcStats[cntr] = processor.getCPUUsage(ctxt, cntr);
                } catch (Exception e) {
                    throw new Exception("CPUProbe.probeStats(): " + e);
                    /*Toast.makeText(ctxt, "Fucked: probeStats()",
                        Toast.LENGTH_SHORT).show();*/
                }
            } else {
                curProcStats[cntr][0] = 0; curProcStats[cntr][1] = 0;
                curProcStats[cntr][2] = 0; curProcStats[cntr][3] = 0;
            }
        }

        return curProcStats;
    }

    /**
     * Method provides a simple way to handle tabulation and rounding of the
     * statistics from each core
     *
     * @param times - array of 4 core time values
     * @return float - array of 4 time percentages (rounded to 2d)
     */
    public static float[] tabNRoundStats(int[] times) {
        int total = 0;
        float[] pct = new float[4];
        float tmp;

        for (int ouah : times) {
            total += ouah;
        }

        for (int cntr = 0; cntr < 4; cntr++) {
            //handles the rounding & percentage calculation
            tmp = ((float)times[cntr] / total);
            pct[cntr] = Math.round(tmp * 100);
        }

        return pct;
    }

    /**
     * This method is used primarily during debugging of CPUProbe &
     * CPUDetails...  Again, due to the fact that I've not yet learned
     * enough regarding unit testing or using the debugger efficiently.
     */
    public static void displayInfo() {
        int[][] cpuStats = new int[MAX_COARS][4];
        /*
         * cpuStats[core #] array breaks down as follows:
         * 1: idle
         * 2: iowait
         * 3: irq
         * 4: softirq
         */

        int coars = 0;

        CPUDetails wut = new CPUDetails(ctxt);

        try {
            coars = wut.getCoars(ctxt);
            for (int cntr = 0; cntr < coars; cntr++) {
                cpuStats[cntr] = wut.getCPUUsage(ctxt, cntr);
            }
        } catch (Exception e) {
            Toast.makeText(ctxt, "1: " + e,
                    Toast.LENGTH_LONG).show();
        }

        if (RecordUsage.debugging >= RecordUsage.METHOD_CALLS) {
            Toast.makeText(ctxt, "displayInfo()",
                    Toast.LENGTH_SHORT).show();
        }

        try {
            if (RecordUsage.debugging >= RecordUsage.GENERAL) {
                Toast.makeText(ctxt, "Coar siblings: " + coars,
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(ctxt, "Usage entries (core 0): " +
                        wut.getCPUUsage(ctxt, 0),
                        Toast.LENGTH_LONG).show();
            }

            for (int cntr = 0; cntr < coars; cntr++) {
                for (int perCoarEntry : wut.getCPUUsage(ctxt, cntr)) {
                    Toast.makeText(ctxt, perCoarEntry,
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(ctxt, "2: " + e,
                    Toast.LENGTH_LONG).show();
        }
    }
}