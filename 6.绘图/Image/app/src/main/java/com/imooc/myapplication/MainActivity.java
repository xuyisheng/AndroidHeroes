package com.imooc.myapplication;

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

    public void btnPixelsEffect(View view) {
        startActivity(new Intent(this, PixelsEffect.class));
    }

    public void btnColorMatrix(View view) {
        startActivity(new Intent(this, ColorMatrix.class));
    }

    public void btnPrimaryColor(View view) {
        startActivity(new Intent(this, PrimaryColor.class));
    }
}
