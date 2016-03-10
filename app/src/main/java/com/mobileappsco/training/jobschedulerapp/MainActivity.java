package com.mobileappsco.training.jobschedulerapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private JobScheduler mJobScheduler;
    private final int JOB_ID = 123;
    private AlarmManager mAlarmManager;
    JobInfo.Builder builder;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MYTAG", "onStop() scheduling app reopening every 5 seconds");
        if( mJobScheduler.schedule( builder.build() ) <= 0 ) {
            //If something goes wrong
            Toast.makeText(this, "Error scheduling job", Toast.LENGTH_SHORT).show();
            Log.e("MYTAG", "onStop() ERROR scheduling app reopening");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // JOBSCHEDULER
        mJobScheduler = (JobScheduler)
                getSystemService( Context.JOB_SCHEDULER_SERVICE );
        builder = new JobInfo.Builder( JOB_ID,
                new ComponentName( getPackageName(),
                        JobSchedulerService.class.getName() ) );
        builder.setPeriodic(5000);

        // ALARMMANAGER
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        int interval = 5000;
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }
}
