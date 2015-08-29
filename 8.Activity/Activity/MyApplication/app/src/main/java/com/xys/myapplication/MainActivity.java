package com.xys.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Activity：", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Activity：", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Activity：", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Activity：", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Activity：", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Activity：", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Activity：", "onDestroy");
    }
}
