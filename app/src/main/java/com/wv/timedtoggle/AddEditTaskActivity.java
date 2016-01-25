package com.wv.timedtoggle;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wv.timedtoggle.database.DaoMaster;
import com.wv.timedtoggle.database.DaoSession;
import com.wv.timedtoggle.database.TaskBean;

import java.util.Calendar;

public class AddEditTaskActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout layoutTime;
    private RelativeLayout layoutDays;
    private SeekBar seekBar;
    private TextView textTime;
    private TextView day1;
    private TextView day2;
    private TextView day3;
    private TextView day4;
    private TextView day5;
    private TextView day6;
    private TextView day7;
    private TextView[] days;
    private Calendar mNow = Calendar.getInstance();
    private int mHour = 0;
    private int mMinute = 0;
    private Boolean[] selectDays = null;
    private Menu mMenu = null;
    private TaskBean mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_task);
        layoutTime = (LinearLayout) this.findViewById(R.id.layoutTime);
        layoutDays = (RelativeLayout) this.findViewById(R.id.layoutDays);
        layoutTime.setOnClickListener(this);
        layoutDays.setOnClickListener(this);
        textTime = (TextView) this.findViewById(R.id.textTime);
        days = new TextView[]{
                (TextView) this.findViewById(R.id.day_1),
                (TextView) this.findViewById(R.id.day_2),
                (TextView) this.findViewById(R.id.day_3),
                (TextView) this.findViewById(R.id.day_4),
                (TextView) this.findViewById(R.id.day_5),
                (TextView) this.findViewById(R.id.day_6),
                (TextView) this.findViewById(R.id.day_7)
        };
        seekBar = (SeekBar) this.findViewById(R.id.seekBar);

        mTask = new TaskBean();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("New Task");
        setSupportActionBar(myToolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    @Override
    public void onClick(View view) {
        long id = view.getId();
        if (id == R.id.layoutDays) {
            showDatePicker();
        } else if (id == R.id.layoutTime) {
            showTimerPicker();
        }
    }

    private void showTimerPicker() {
        int hour = mNow.get(Calendar.HOUR_OF_DAY);
        int minute = mNow.get(Calendar.MINUTE);
        new TimePickerDialog(AddEditTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                textTime.setText(mHour + (mMinute < 10 ? ":0" + mMinute : ":" + mMinute));
                /*mNow.setTimeInMillis(System.currentTimeMillis());
                mNow.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mNow.set(Calendar.MINUTE, minute);
                mNow.set(Calendar.SECOND, 0);
                mNow.set(Calendar.MILLISECOND, 0);*/
            }
        }, hour, minute, true).show();
    }

    private void showDatePicker() {
        selectDays = new Boolean[]{false, false, false, false, false, false, false};
        //String[] days = this.getResources().getStringArray(R.array.days);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle(R.string.label_days_to_run);
        builder.setMultiChoiceItems(R.array.days, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectDays[which] = true;
                } else {
                    selectDays[which] = false;
                }
            }
        });
        builder.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int j = 0; j < days.length; j++) {
                    if (selectDays[j]) {
                        days[j].setVisibility(View.VISIBLE);
                    } else {
                        days[j].setVisibility(View.GONE);
                    }
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_task, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_edit_pause) {
            MenuItem playMenu = mMenu.findItem(R.id.action_edit_play);
            playMenu.setVisible(true);
            item.setVisible(false);
            mTask.setEnable(false);
        } else if (id == R.id.action_edit_play) {
            MenuItem playMenu = mMenu.findItem(R.id.action_edit_pause);
            playMenu.setVisible(true);
            item.setVisible(false);
            mTask.setEnable(true);
        } else if (id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 保存task到数据库;
        DaoMaster daoMaster = new DaoMaster(App.getInstance().getDb());
        DaoSession daoSession = daoMaster.newSession();
        daoSession.getTaskBeanDao().insert(mTask);
    }
}
