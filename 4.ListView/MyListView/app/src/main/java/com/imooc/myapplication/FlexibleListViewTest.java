package com.imooc.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class FlexibleListViewTest extends Activity {

    private FlexibleListView mFlexibleListView;
    private String[] data = new String[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flexible);
        for (int i = 0; i < 30; i++) {
            data[i] = "" + i;
        }
        mFlexibleListView = (FlexibleListView) findViewById(R.id.flexible_listview);
        mFlexibleListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                data));
    }
}
