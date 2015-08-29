package com.imooc.activitystack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mShowClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mShowClass = (TextView) findViewById(R.id.show_class);
        mShowClass.setText(this.toString());
    }

    public void btnStandard(View view) {
        startActivity(new Intent(this, MainActivity.class));
        mShowClass.setText(this.toString());
    }

    public void btnSingleTop(View view) {
        startActivity(new Intent(this, ActivitySingleTop.class));
        mShowClass.setText(this.toString());
    }

    public void btnSingleTask(View view) {
        startActivity(new Intent(this, ActivitySingleTop.class));
        mShowClass.setText(this.toString());
    }
}
