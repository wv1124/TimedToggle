package com.wv.timedtoggle;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.wv.timedtoggle.database.DaoMaster;

/**
 * Created by wv on 16/1/25.
 */
public class App extends Application {

    private static SQLiteDatabase mDb;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public static SQLiteDatabase getDb() {
        DaoMaster.DevOpenHelper helper
                = new DaoMaster.DevOpenHelper(instance, "timedtoggle-db", null);
        mDb =  helper.getWritableDatabase();
        return mDb;
    }

}
