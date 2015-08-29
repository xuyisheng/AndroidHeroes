package com.imooc.myapplication;

import android.app.Activity;
import android.os.Bundle;

public class ReflectViewTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ReflectView(this));
    }
}
