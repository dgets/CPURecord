package com.example.khelair.cpurecord;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.khelair.cpurecord.CPUProbe.MAX_COARS;

/**
 * Class is for things related to the running statistics per coar
 *
 * @author Damon Getsman
 * @since 2017-11-02
 */

public class CPURunStats {
    //general per-cycle CPU stats
    private Map<String, Integer>[] cpuTime = new HashMap[MAX_COARS];
    private static final String[] sliceTypes =
            { "idle", "iowait", "irq", "softirq" };

    //constructor(s)
    public CPURunStats() throws Exception {
        Timestamp timeStamp = null;
        int[][] ouah = new int[MAX_COARS][4];

        try {
            ouah = CPUProbe.probeStats(CPUDetails.ctxt);
        } catch (Exception e) {
            throw new Exception("God ouah: " + e);
        }

        timeStamp.setTime(Calendar.getInstance().getTimeInMillis());
        for (int cntr = 0; cntr < MAX_COARS; cntr++) {
            cpuTime[cntr].put("idle", ouah[cntr][0]);
            cpuTime[cntr].put("iowait", ouah[cntr][1]);
            cpuTime[cntr].put("irq", ouah[cntr][2]);
            cpuTime[cntr].put("softirq", ouah[cntr][3]);
        }
    }

    /**
     * Method returns the number of cycle slices counted for the specified
     * core.
     *
     * @param coar
     * @return
     */
    public int totalSlices(int coar) throws Exception {
        int total = 0;

        if (coar >= MAX_COARS) {
            throw new Exception("Coar out of range");
        }
        for (String stype : sliceTypes) {
            total += cpuTime[coar].get(stype);
        }

        return total;
    }

    /**
     * Method is a slice type total getter (coars' specific value totalled);
     * not sure exactly what the difference with this method is from above
     * just yet.
     */
    public int getSliceTotal(String stype) throws Exception {
        int total = 0;  int curCoar = 0;

        do {
            total += cpuTime[curCoar++].get(stype);
        } while (cpuTime[curCoar].get("idle") != 0);

        return total;
    }
}
