package com.wv.timedtoggle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wv.timedtoggle.DaemonService;
import com.wv.timedtoggle.common.Constants;

/**
 * Created by wv on 16/1/25.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Constants.ALARM_ACTION)) {
            Intent i = new Intent();
            i.setClass(context, DaemonService.class);
            // 启动service
            // 多次调用startService并不会启动多个service 而是会多次调用onStart
            context.startService(i);
        }
    }
}