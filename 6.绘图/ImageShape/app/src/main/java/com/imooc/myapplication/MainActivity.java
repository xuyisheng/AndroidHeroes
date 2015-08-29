package com.imooc.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void btnReflect(View view) {
        startActivity(new Intent(this, ReflectViewTest.class));
    }

    public void btnImageMatrix(View view) {
        startActivity(new Intent(this, ImageMatrixTest.class));
    }

    public void btnPorterDuffXfermode(View view) {
        startActivity(new Intent(this, XfermodeViewTest.class));
    }

    public void btnFlag(View view) {
        startActivity(new Intent(this, FlagBitmapMeshTest.class));
    }

    public void btnRoundRect(View view) {
        startActivity(new Intent(this, RoundRectTest.class));
    }
}
