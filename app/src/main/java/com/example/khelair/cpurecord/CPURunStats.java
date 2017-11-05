package com.example.khelair.cpurecord;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.khelair.cpurecord.CPUProbe.MAX_COARS;

/**
 * Created by Damon Getsman on 11/2/17.
 */

public class CPURunStats {
    //general per-cycle CPU stats
    /*private float cpuTime = { "idle" => null, "iowait" => null, "irq" => null,
            "softirq" => null);*/
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
     * Method is a slice type total getter (coars values totalled)
     */
    public int getSliceTotal(String stype) throws Exception {
        int total = 0;  int curCoar = 0;

        do {
            total += cpuTime[curCoar++].get(stype);
        } while (cpuTime[curCoar].get("idle") != 0);
        //there is certainly a much better way to determine coars;
        //surgery recovery is my excuse today

        return total;
    }

    /**
     * Method is a slice type total getter, only for the one coar
     */
    /*public int getSliceTotal(String stype, int coar) throws Exception {
        return (int) cpuTime[coar].get(stype);
    }*/
}
