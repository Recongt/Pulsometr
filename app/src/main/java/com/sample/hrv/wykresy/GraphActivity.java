package com.sample.hrv.wykresy;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sample.hrv.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GraphActivity extends Activity {

    private final Handler mHandler = new Handler();
    private final Handler mHandler2 = new Handler();
    private Runnable mTimer2;
    private Runnable mTimer1;
    private LineGraphSeries<DataPoint> mSeries2;
    private Queue<Double> kolejka = new LinkedList<>();
    private String najwyzszyPuls = "0";
    private String najnizszyPuls = "0";
    private double graph2LastXValue = 0d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        wypKolejki();
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
        cos();
    }

    private void cos() {

        TextView textViewNajnizszaCzestotliwosc = (TextView) findViewById(R.id.editTextNajnizszaCzestotliwosc);
        TextView textViewNajwyzszaCzestotliwosc = (TextView) findViewById(R.id.editTextnajwyzszaCzestotliwosc);
        najnizszyPuls = String.format("%.1f", mSeries2.getLowestValueY()) + " uderzeń na minutę";
        najwyzszyPuls = String.format("%.1f", mSeries2.getHighestValueY()) + " uderzeń na minutę";
        textViewNajnizszaCzestotliwosc.setText(najnizszyPuls);
        textViewNajwyzszaCzestotliwosc.setText(najwyzszyPuls);


    }


    private void startGen() {
        mTimer2 = new Runnable() {
            @Override
            public void run() {
                graph2LastXValue += 1d;
                mSeries2.appendData(new DataPoint(graph2LastXValue, kolejnyElement()), true, 400);
                mHandler.postDelayed(this, 1000);
                najnizszyPuls = Double.toString(mSeries2.getLowestValueY());
                najwyzszyPuls = Double.toString(mSeries2.getHighestValueY());
                cos();

            }
        };
        mHandler.postDelayed(mTimer2, 0);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer2);
        super.onPause();
    }

    private DataPoint[] generateData() {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i = 0; i < count; i++) {
            double x = i;
            double f = mRand.nextDouble() * 0.15 + 0.3;
            double y = Math.sin(i * f + 2) + mRand.nextDouble() * 0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.graph_menu, menu);
        return true;
    }
    double mLastRandom = 2;
    Random mRand = new Random();

    private double getRandom() {
        double min = 65;
        double max = 75;
        double nowe = Math.round( min + Math.random() * (max - min));
        System.out.println("nowa wartosc: " + nowe);
        return nowe;
    }

    private void wypKolejki() {
        kolejka.add(new Double(71));
        kolejka.add(new Double(70));
        kolejka.add(new Double(72));
        kolejka.add(new Double(70));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));
        kolejka.add(new Double(71));

    }

    private double kolejnyElement() {
        kolejka.add(new Double(getRandom()));
        kolejka.poll();
        List<Double> lista = new ArrayList<>(kolejka);
        Double sum = 0.0;
        System.out.println("Ilość elementów: " + lista.size());
        for (int i = lista.size(); i > 0; i--) {
            sum = sum + lista.get(i - 1);
        }
        System.out.println("Srednia: " + ((sum / lista.size())*100)/100);
        return  ((sum / lista.size())*100)/100;

    }

    public void stop(View view) {
        mHandler.removeCallbacks(mTimer2);
    }

    public void start(View view) {
        startGen();
    }
}
