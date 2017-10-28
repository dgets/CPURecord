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

/**
 * @author Damon Getsman
 *
 * Class handles the entry point and primary display of CPU statistics
 */
public class RecordUsage extends AppCompatActivity {
    //'constants'
    public static final int GENERAL = 1;
    public static final int METHOD_CALLS = 2;

    public static final int debugging = GENERAL;
    //public static final int debugging = METHOD_CALLS;

    public Context appShit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_usage);

        appShit = getApplicationContext();
        //SurfaceView board = (SurfaceView) findViewById(R.id.sfvCPUGraph);

        drawBoundary(/*board*/);
        //setContentView(R.layout.activity_record_usage);
    }

    public void onManualRecordClick(View view) {

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

        int[][] deviceStats = new int[CPUProbe.MAX_COARS][5];

        if (debugging >= METHOD_CALLS) {
            Toast.makeText(appShit, "fillStatsListView()",
                    Toast.LENGTH_SHORT).show();
        }

        statsBox.setMovementMethod(new ScrollingMovementMethod());
        statsBox.setText("");

        deviceStats = CPUProbe.probeStats(appShit);

        for (int cntr = 0; cntr < 4; cntr++) {
                float[] percentages = null;
                percentages = CPUProbe.tabNRoundStats(deviceStats[cntr]);

                statsBox.append("\nCore: " + cntr + "\n");
                statsBox.append("Idle: \t\t" + percentages[0] + "%\t\t");
                statsBox.append("IO Wait:\t" + percentages[1] + "%\n");
                statsBox.append("IRQ:\t\t" + percentages[2] + "%\t\t");
                statsBox.append("Soft IRQ:\t" + percentages[3] + "%\n");
        }
    }

}
