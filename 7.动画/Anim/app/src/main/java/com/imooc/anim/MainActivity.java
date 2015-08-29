package com.imooc.anim;

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

    public void btnDrop(View view) {
        startActivity(new Intent(this, DropTest.class));
    }

    public void btnProperty(View view) {
        startActivity(new Intent(this, PropertyTest.class));
    }

    public void btnTimer(View view) {
        startActivity(new Intent(this, TimerTest.class));
    }
}
