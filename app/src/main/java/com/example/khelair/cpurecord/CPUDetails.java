package com.example.khelair.cpurecord;

import android.widget.Toast;
import android.content.Context;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * This class is basically just for any of the CPU information that we can
 * procure via /proc
 *
 * @author Damon Getsman
 * @since 2017-10-19
 */
public class CPUDetails {
    private String vendorId, modelName, cpuFamily;
    //private float speedMhz;
    public static Context ctxt; //GET RID OF THIS

    //constructor(s)
    public CPUDetails(Context c) {
        this.ctxt = c;
    }

    public CPUDetails() throws Exception {
        try {
            this.getMisc();
        } catch (Exception ex) {
            throw new Exception(
                    "CPUDetails.getMisc() issue: " + ex.getMessage());
        }
    }

    //getters/setters (ouah)
    //of course these ones don't have 'setters', as everything is handled in
    //getMisc() for these values
    public String getVendorId() {
        return vendorId;
    }

    public String getModelName() {
        return modelName;
    }

    public String getCpuFamily() {
        return cpuFamily;
    }

    /*public int getCoreId() {
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
    }*/

    //general methods
    /**
     * Method probes cpuinfo for various details about the hardware
     *
     * @exception Exception generic on multiple issues
     */
    public void getMisc() throws Exception {
        RandomAccessFile ouah = null;

        try {
            ouah = new RandomAccessFile("/proc/cpuinfo", "r");
        } catch (Exception ex) {
            throw new Exception("opening cpuinfo: " + ex.getMessage());
        }

        //String gnah, godOuah[];
        String nang = ouah.readLine();

        while (nang != null) {
            if (nang.contains("Processor")) {
                String gnah = nang.split(": ")[1];
                String godOuah[] = gnah.split(" ");

                vendorId = godOuah[0];
                modelName = godOuah[0] + " " + godOuah[1] + " " +
                        godOuah[2] + " " + godOuah[3];

                try {
                    cpuFamily = godOuah[4].subSequence(1,
                            (godOuah[4].length() - 1)).toString();
                } catch (Exception ex) {
                    throw new Exception("cpuFamily setting: " + ex.getMessage());
                }
            }
            nang = ouah.readLine();
        }

        ouah.close();
    }

    /**
     * Method displays the schitt that we don't need to format nicely
     */
    public String toString() {
        return "CPU Misc\n-=-=-=-=-\nVendor ID:\t" + vendorId +
        "\t\tModel:\t" + modelName + "\t\tFamily:\t" + cpuFamily + "\n\n";
    }

    /**
     * Method probes for the number of cores on this device.
     *
     * @param ctxt
     * @return int - number of cores found
     */
    public int getCoars(Context ctxt) {
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
                    guhUpDown =
                            (Integer.parseInt(nakk.split(": ")[1]) + 1);
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

    /**
     * This method returns an array of 4 integers for the different
     * processor modes' time spent.
     *
     * @param ctxt
     * @param processor - id of the core to probe
     * @return float[] - array of tiem spent in each mode
     * @throws Exception generic
     */
    public int[] getCPUUsage(Context ctxt, int processor) throws Exception {
        int[] guhUpDown = new int[4];
        String[] preGuhUpDown = new String[4];
        RandomAccessFile ouah;

        try {
            if (RecordUsage.debugging >= RecordUsage.METHOD_CALLS) {
                Toast.makeText(ctxt, "getCPUUsage()",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            throw new Exception("4: " + e);
        }

        try {
            ouah = new RandomAccessFile("/proc/stat", "r");

            String nakk;

            ouah.readLine();    //ditch the first, which is
                                // aggregate stats (for now, anyway)
            nakk = ouah.readLine();
            while (nakk != null) {
                if (nakk.contains("cpu" + processor)) {
                    try {
                        preGuhUpDown = Arrays.copyOfRange(
                                nakk.split(" "), 3, 6);
                    } catch (Exception e) {
                        Toast.makeText(ctxt, "fucked!!",
                                Toast.LENGTH_LONG).show();
                    }
                }

                nakk = ouah.readLine();
            }
            ouah.close();
        } catch (IOException e) {
            Toast.makeText(ctxt, "Problem reading /proc/stat",
                    Toast.LENGTH_LONG);
        }

        if (preGuhUpDown != null) {
            for (int cntr = 0; cntr < preGuhUpDown.length; cntr++) {
                try {
                    guhUpDown[cntr] = Integer.parseInt(preGuhUpDown[cntr]);
                } catch (Exception e) {
                    Toast.makeText(ctxt, "WTF-f-f",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } //raise an exception in an 'else' clause heah

        return guhUpDown;
    }
}
