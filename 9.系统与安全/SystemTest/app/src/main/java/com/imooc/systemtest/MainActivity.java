package com.imooc.systemtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnAM(View view) {
        startActivity(new Intent(this, AMProcessTest.class));
    }

    public void btnPM(View view) {
        startActivity(new Intent(this, PMTest.class));
    }
}
