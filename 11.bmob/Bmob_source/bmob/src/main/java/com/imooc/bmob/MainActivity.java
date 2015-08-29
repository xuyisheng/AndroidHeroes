package com.imooc.bmob;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity {

    private EditText mName, mFeedback,mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化Bmob
        Bmob.initialize(this,"8aeccc5216731a9f029ef1b1e0f12840");
        BmobInstallation.getCurrentInstallation(this).save();
        BmobPush.startWork(this,"8aeccc5216731a9f029ef1b1e0f12840");
        mName = (EditText) findViewById(R.id.name);
        mFeedback = (EditText) findViewById(R.id.feedback);
        mQuery = (EditText) findViewById(R.id.query_et);
    }

    public void pushAll(View view) {
        BmobPushManager push = new BmobPushManager(MainActivity.this);
        push.pushMessageAll("Test");
    }

    public void queryFeedback(View view) {
        String str = mQuery.getText().toString();
        if (str.equals("")) {
            return;
        }
        // 通过BmobQuery创建一个查询对象
        BmobQuery<Feedback> query = new BmobQuery<Feedback>();
        // 设置查询条件
        query.addWhereEqualTo("name", str);
        query.findObjects(MainActivity.this,new FindListener<Feedback>() {
            @Override
            public void onSuccess(List<Feedback> feedbacks) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                builder.setTitle("Query");
                String str = "";
                for (Feedback feedback : feedbacks) {
                    str += feedback.getName() + ":" +
                            feedback.getFeedback() + "\n";
                }
                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void queryAll(View view) {
        // 通过BmobQuery创建一个查询对象
        BmobQuery<Feedback> query = new BmobQuery<Feedback>();
        query.findObjects(MainActivity.this,new FindListener<Feedback>() {
            @Override
            public void onSuccess(List<Feedback> feedbacks) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                builder.setTitle("Query");
                String str = "";
                for (Feedback feedback : feedbacks) {
                    str += feedback.getName() + ":" +
                            feedback.getFeedback() + "\n";
                }
                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void submit(View view) {
        String name = mName.getText().toString();
        String feedback = mFeedback.getText().toString();
        if (name.equals("") || feedback.equals("")) {
            return;
        }
        // 创建BmobObject对象
        Feedback feedbackObj = new Feedback();
        feedbackObj.setName(name);
        feedbackObj.setFeedback(feedback);
        feedbackObj.save(MainActivity.this,new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(
                        MainActivity.this,
                        "submit success",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(
                        MainActivity.this,
                        "submit failure",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
