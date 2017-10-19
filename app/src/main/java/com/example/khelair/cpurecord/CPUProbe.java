package com.example.khelair.cpurecord;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Damon Getsman on 10/19/17.
 */

public class CPUProbe {

    //Context appShit;

    public void gatherCPUInfo() {
        //determine coars

    }

    public void probeStats() {

    }

    public static void displayInfo(Context aShit) {
        Toast.makeText(aShit, "Coar siblings: " +
            CPUDetails.getCoars(aShit), Toast.LENGTH_LONG);
    }
}