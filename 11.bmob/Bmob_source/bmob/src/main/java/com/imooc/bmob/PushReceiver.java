package com.imooc.bmob;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;

public class PushReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "";
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            String msg = intent.getStringExtra(
                    PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            JSONTokener jsonTokener = new JSONTokener(msg);
            try {
                JSONObject object =
                        (JSONObject) jsonTokener.nextValue();
                message = object.getString("alert");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // 获取系统Notification服务
            NotificationManager manager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            // 设置Notification相关属性
            Notification notification = new Notification(
                    R.drawable.ic_launcher,
                    "TestBmob",
                    System.currentTimeMillis());
            notification.setLatestEventInfo(
                    context,
                    "Bmob Test",
                    message,
                    null);
            manager.notify(R.drawable.ic_launcher, notification);
        }
    }
}
