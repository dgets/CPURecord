package com.example.khelair.cpurecord;

import android.widget.Toast;
import android.content.Context;

import java.io.IOException;
import java.io.RandomAccessFile;
//import java.lang.reflect.Array;
import java.util.Arrays;

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
    public int getCoars() {
        //I believe that an effective method to obtain this information will
        //be to check the 'siblings' line of the first CPU/core listed in
        // /proc/cpuinfo; best to check on that somewhere, though

        int guhUpDown = 0;
        RandomAccessFile ouah = null;

        if (RecordUsage.debugging >= RecordUsage.METHOD_CALLS) {
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
            Toast.makeText(ctxt, "Problem reading cpuinfo",
                    Toast.LENGTH_SHORT).show();
        }

        if (RecordUsage.debugging >= RecordUsage.GENERAL) {
            Toast.makeText(ctxt, "CPU Siblings: " + guhUpDown,
                    Toast.LENGTH_LONG);
        }

        return guhUpDown;
    }

    public int[] getCPUUsage(Context ctxt, int processor) throws Exception {
        int[] guhUpDown = new int[4];
        String[] preGuhUpDown = new String[4];
        RandomAccessFile ouah;

        try {
            if (RecordUsage.debugging >= RecordUsage.METHOD_CALLS) {
                Toast.makeText(ctxt, "getCPUUsage()", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            throw new Exception("4: " + e);
        }

        try {
            ouah = new RandomAccessFile("/proc/stat", "r");

            String nakk;

            ouah.readLine();    //ditch the first, which is
                                // aggregate stats
            nakk = ouah.readLine();
            while (nakk != null) {
                if (nakk.contains("cpu" + processor)) {
                    preGuhUpDown  = Arrays.copyOfRange(nakk.split(" "), 3, 6);
                    ouah.close();
                }
            }
        } catch (IOException e) {
            Toast.makeText(ctxt, "Problem reading /proc/stat",
                    Toast.LENGTH_LONG);
        }

        if (preGuhUpDown != null) {
            for (int cntr = 0; cntr <= preGuhUpDown.length; cntr++) {
                guhUpDown[cntr] = Integer.parseInt(preGuhUpDown[cntr]);
            }
        } //raise an exception in an 'else' clause heah

        /*guhUpDown[0] = 1;
        guhUpDown[1] = 2;*/
        return guhUpDown;
    }
}
