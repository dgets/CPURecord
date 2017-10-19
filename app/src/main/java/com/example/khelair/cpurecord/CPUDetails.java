package com.example.khelair.cpurecord;

import android.widget.Toast;
import android.content.Context;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by khelair on 10/19/17.
 */

public class CPUDetails {
    private String vendorId, modelName;
    private int cpuFamily, coreId;
    private float speedMhz;
    public static Context ctxt;

    //constructor(s)
    public CPUDetails(Context c) {
        this.ctxt = c;
    }

    public CPUDetails(Context c, String vid, String mn, int cf, int ci,
                      float spd) {
        this.vendorId = vid;
        this.modelName = mn;
        this.cpuFamily = cf;
        this.coreId = ci;
        this.speedMhz = spd;
        this.ctxt = c;
    }

    //getters/setters (ouah)
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vid) {
        this.vendorId = vid;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String mName) {
        this.modelName = mName;
    }

    public int getCpuFamily() {
        return cpuFamily;
    }

    public void setCpuFamily(int cfam) {
        this.cpuFamily = cfam;
    }

    public int getCoreId() {
        return coreId;
    }

    public void setCoreId(int cid) {
        this.coreId = cid;
    }

    public float getSpeedMhz() {
        return speedMhz;
    }

    public void setSpeedMhz(float speed) {
        this.speedMhz = speed;
    }

    //general methods
    public int getCoars(Context ctxt) {
        //I believe that an effective method to obtain this information will
        //be to check the 'siblings' line of the first CPU/core listed in
        // /proc/cpuinfo; best to check on that somewhere, though

        int guhUpDown = 0;
        RandomAccessFile ouah = null;

        if (RecordUsage.debugging >= 2) {
            Toast.makeText(ctxt, "getCoars()", Toast.LENGTH_SHORT).show();
        }

        try {
            ouah = new RandomAccessFile("/proc/cpuinfo", "r");
            String nakk = ouah.readLine();
            while (nakk != null) {
                if (nakk.contains("processor")) {
                    guhUpDown = (Integer.parseInt(nakk.split(": ")[1]) + 1);
                }
                nakk = ouah.readLine();
            }
            ouah.close();
        } catch (IOException ex) {
            //no idea why this isn't working the same way that it did in
            //MinTone :-?(beep)
            Toast.makeText(ctxt, "Problem reading cpuinfo",
                    Toast.LENGTH_SHORT).show();
        }

        if (RecordUsage.debugging >= 1) {
            Toast.makeText(ctxt, "CPU Siblings: " + guhUpDown, Toast.LENGTH_LONG);
        }

        return guhUpDown;
    }
}
