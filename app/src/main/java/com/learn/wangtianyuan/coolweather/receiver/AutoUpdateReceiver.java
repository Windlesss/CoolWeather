package com.learn.wangtianyuan.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.learn.wangtianyuan.coolweather.service.AutoUpdateService;

/**
 * Created by wangtianyuan on 16/5/18.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test", "onReceive  BroadcastReceiver start service again");
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
