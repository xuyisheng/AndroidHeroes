package com.imooc.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NotifyTest extends Activity {

    private List<String> mData;
    private ListView mListView;
    private NotifyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify);
        mData = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mData.add("" + i);
        }
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new NotifyAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        for (int i = 0; i < mListView.getChildCount(); i++) {
            View view = mListView.getChildAt(i);
        }
    }

    public void btnAdd(View view) {
        mData.add("new");
        mAdapter.notifyDataSetChanged();
        mListView.setSelection(mData.size() - 1);
    }
}
