package com.sample.hrv.testowe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sample.hrv.DeviceScanActivity;
import com.sample.hrv.R;
import com.sample.hrv.wykresy.GraphActivity;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

    public void testWykresow(View view) {
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }

    public void testBluetoothLE(View view) {
        Intent intent = new Intent(this, DeviceScanActivity.class);
        startActivity(intent);
    }

    public void doPowitalnej(View view){
        Intent intent = new Intent(this, PowitalneActivity.class);
        startActivity(intent);
    }


}