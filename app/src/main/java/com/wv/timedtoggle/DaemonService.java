package com.wv.timedtoggle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by wv on 16/1/25.
 */
public class DaemonService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("=========", "***** DaemonService *****: onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("=========", "***** DaemonService *****: onStart");
        // 这里可以做Service该做的事
        return START_REDELIVER_INTENT;
    }
}