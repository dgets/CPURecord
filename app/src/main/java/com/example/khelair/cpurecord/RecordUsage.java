package com.example.khelair.cpurecord;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Class handles the entry point and primary display of CPU statistics.
 * Just an idea that I had after a few months of noticing every time that I'd
 * turn my phone's display back on, the CPU would be pegged. I'm not sure,
 * but I think that it might be indicative of some malware, and I want to
 * collect some other statistics regarding what is going on when the display
 * is inactive.
 *
 * @author Damon Getsman
 * @version 0.1a
 * @since 2017-10-18
 *
 */
public class RecordUsage extends AppCompatActivity {
    //'constants'
    public static final int GENERAL = 1;
    public static final int METHOD_CALLS = 2;
    public static final int TICK_TOCK = 3;

    //public static final int debugging = GENERAL;
    //public static final int debugging = METHOD_CALLS;
    public static final int debugging = TICK_TOCK;

    //really need to try to get rid of this
    private Context appShit;

    //these absolutely do need to be abolished and transferred around properly
    private boolean recording = false;
    private List<CPURunStats> cpuTickTock;
    private ScheduledExecutorService ses =
            Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*final ScheduledExecutorService ses =
                Executors.newSingleThreadScheduledExecutor(); */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_usage);

        appShit = getApplicationContext();

    }

    /**
     * Method is to append each usage cycle's data collected to
     * an internal log file; graphing can take place from these
     * stats later on.
     *
     * @param view
     */
    public void onManualRecordClick(View view) {
        //CPURunStats statsLog[] = null;
        //int cntr = 0;
        ScheduledFuture wutsGoinOnHeah = null;

        if (!recording) {
            //start recording, por dios; we need to find a different way to
            //handle the following object; this is going to crash on each
            //invocation after the first, isn't it?
            if (debugging >= METHOD_CALLS) {
                Toast.makeText(appShit, "setting ses.scheduleAtFixedRate()",
                        Toast.LENGTH_SHORT).show();
            }
            wutsGoinOnHeah = ses.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    TextView debugBox = (TextView) findViewById(R.id.txtStats);
                    Context gnah = getApplicationContext();

                    try {
                        cpuTickTock.add(new CPURunStats());
                        Toast.makeText(gnah, "god ouah, man",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(gnah, "via onManualRecordClick(): " +
                            ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    if (debugging >= TICK_TOCK) {
                        debugBox.append("Debugging:");
                        debugBox.append("\n" + cpuTickTock.toString());
                    }
                }
            }, 0, 2, TimeUnit.SECONDS);

            //debugBox.append("WtF-f-f-f-f-f");
            //Toast.makeText(appShit, "wtF-f-f", Toast.LENGTH_SHORT).show();
        } else {
            //stop and write it
            ses.shutdown();
        }

        /*if (debugging >= TICK_TOCK) {
            Toast.makeText(appShit, wutsGoinOnHeah.toString(),
                    Toast.LENGTH_LONG).show();
        }*/
        recording = !recording;
    }

    /**
     * This method is to purdify the SurfaceView, as well as to provide
     * a good place for me to learn to use it.  This method, and its friends
     * to come, may well end up in a separate class at some point soon.
     */
    public void drawBoundary() {
        SurfaceView drawHeah = (SurfaceView) findViewById(R.id.sfvCPUGraph);
        Canvas canvas = new Canvas();

        Paint borderPaint = new Paint();

        int maxX, maxY;

        maxY = drawHeah.getHeight(); maxX = drawHeah.getWidth();

        borderPaint.setColor(Color.YELLOW);
        drawHeah.setBackgroundColor(Color.BLUE);

        canvas.drawLine(1, 1, maxX - 1, 1, borderPaint);
        canvas.drawLine(maxX - 1, 1, maxX - 1, maxY - 1, borderPaint);
        canvas.drawLine(maxX - 1, maxY - 1, 1, maxY - 1, borderPaint);
        canvas.drawLine(1, maxY - 1, 1, 1, borderPaint);

        drawHeah.draw(canvas);
    }

    /**
     * This class is being used in development because I still don't know how
     * to work with unit testing, or the debugger, well enough to handle
     * this stuff the right way.  :|
     *
     * @param view
     */
    public void getDebugInfos(View view) {
        appShit = getApplicationContext();

        CPUProbe ouah = new CPUProbe(appShit);

        if (debugging >= METHOD_CALLS) {
            Toast.makeText(appShit, "getDebugInfos()",
                    Toast.LENGTH_SHORT).show();
        }

        if (debugging >= GENERAL) {
            CPUProbe.displayInfo();
        }
    }

    /**
     * This class gathers statistics from CPUProbe & CPUDetails, then filling
     * the TextBox with our information.
     *
     * @param view
     */
    public void fillStatsListView(View view) {
        appShit = getApplicationContext();

        TextView statsBox = (TextView) findViewById(R.id.txtStats);

        int[][] deviceStats = new int[CPUProbe.MAX_COARS][4]; //5=stats+total

        if (debugging >= METHOD_CALLS) {
            Toast.makeText(appShit, "fillStatsListView()",
                    Toast.LENGTH_SHORT).show();
        }

        statsBox.setMovementMethod(new ScrollingMovementMethod());
        statsBox.setText("");

        try {
            deviceStats = CPUProbe.probeStats(appShit);
        } catch (Exception ex) {
            Toast.makeText(appShit, "RecordUsage.fillStatsListView(): " +
                ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        int total = 0;
        for (int cntr = 0; cntr < 4; cntr++) {
            float[] percentages = new float[4];

            for (int cur = 0; cur < deviceStats[0].length; cur++) {
                total += deviceStats[cur][cntr];
            }
            for (int cur = 0; cur < deviceStats[0].length; cur++) {
                percentages[cur] =
                        (float) ((deviceStats[cur][cntr] * 100) / total);
            }

            statsBox.append("\nCore: " + cntr + "\n");
            statsBox.append("Idle: \t\t" + Float.toString(percentages[0])
                    + "%\t\t");
            statsBox.append("IO Wait:\t" + Float.toString(percentages[1])
                    + "%\n");
            statsBox.append("IRQ:\t\t" + Float.toString(percentages[2])
                    + "%\t\t");
            statsBox.append("Soft IRQ:\t" + Float.toString(percentages[3])
                    + "%\n");
        }

        CPUDetails ouahouah = null;

        //cheggout the misc stats
        try {
            ouahouah = new CPUDetails();
        } catch (Exception ex) {
            Toast.makeText(appShit, "ouah: " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        statsBox.append("\n\n" + ouahouah);
    }

}
