package com.sample.hrv.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sample.hrv.R;
import com.sample.hrv.sensor.BleHeartRateSensor;
import com.sample.hrv.sensor.BleSensor;


public class DemoHeartRateSensorActivity extends DemoSensorActivity {
    private final static String TAG = DemoHeartRateSensorActivity.class
            .getSimpleName();
    private LineGraphSeries<DataPoint> mSeries2;
    private double graph2LastXValue = 0d;
    private boolean isStarted = false;
    private TextView viewText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);


        GraphView graph2 = (GraphView) findViewById(R.id.graph2);

        mSeries2 = new LineGraphSeries<>();
        mSeries2.setColor(Color.rgb(255, 10, 10));
        graph2.addSeries(mSeries2);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(60);
        graph2.setTitle("Puls");
        graph2.getViewport().setScalable(true);
        graph2.getViewport().setScrollable(true);

        getActionBar().setTitle(R.string.app_name);

        viewText = (TextView) findViewById(R.id.text);

        //view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        // Render when hear rate data is updated

    }

    private void collectDataToHeartRateGraph(double data){
        if(isStarted) {
            graph2LastXValue += 1d;
            mSeries2.appendData(new DataPoint(graph2LastXValue, data), true, 400);
            cos();
        }
    }

    @Override
    public void onDataRecieved(BleSensor<?> sensor, String text) {
        if (sensor instanceof BleHeartRateSensor) {
            final BleHeartRateSensor heartSensor = (BleHeartRateSensor) sensor;
            float[] values = heartSensor.getData();

            System.out.println("Values: " + values[0]);
            collectDataToHeartRateGraph(((BleHeartRateSensor) sensor).getDataDouble());
            viewText.setText(text);
        }
    }
    private void cos() {
        TextView textViewNajnizszaCzestotliwosc = (TextView) findViewById(R.id.editTextNajnizszaCzestotliwosc);
        TextView textViewNajwyzszaCzestotliwosc = (TextView) findViewById(R.id.editTextnajwyzszaCzestotliwosc);
        textViewNajnizszaCzestotliwosc.setText(String.format("%.1f", mSeries2.getLowestValueY()) + " uderzeń na minutę");
        textViewNajwyzszaCzestotliwosc.setText(String.format("%.1f", mSeries2.getHighestValueY()) + " uderzeń na minutę");
    }
    public void start(View view){
        isStarted = true;
    }
    public void stop(View view){
        isStarted = false;
    }

}
