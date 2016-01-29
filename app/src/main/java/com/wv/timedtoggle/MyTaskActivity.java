package com.wv.timedtoggle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wv.timedtoggle.adapter.CommonAdapter;
import com.wv.timedtoggle.adapter.ViewHolder;
import com.wv.timedtoggle.database.DaoMaster;
import com.wv.timedtoggle.database.DaoSession;
import com.wv.timedtoggle.database.ScheduleBean;

import java.util.List;

public class MyTaskActivity extends BaseActivity {

    private CommonAdapter mAdapter;
    private List<ScheduleBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_task_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(MyTaskActivity.this, AddEditTaskActivity.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        DaoMaster daoMaster = new DaoMaster(App.getInstance().getDb());
        DaoSession daoSession = daoMaster.newSession();
        mDatas = daoSession.loadAll(ScheduleBean.class);
        mAdapter = new CommonAdapter<ScheduleBean>(this.getApplicationContext(),
                mDatas, R.layout.my_task_list_item) {
            @Override
            public void convert(ViewHolder viewHolder, ScheduleBean item) {
                viewHolder.setChecked(R.id.checkSelect, item.getEnable());
                viewHolder.setText(R.id.task, item.getTask());
                String dates = item.getDate();
            }
        };
    }
}
