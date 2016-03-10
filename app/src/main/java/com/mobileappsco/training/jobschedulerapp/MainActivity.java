package com.mobileappsco.training.jobschedulerapp;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private JobScheduler mJobScheduler;
    private final int JOB_ID = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJobScheduler = (JobScheduler)
                getSystemService( Context.JOB_SCHEDULER_SERVICE );
        JobInfo.Builder builder = new JobInfo.Builder( JOB_ID,
                new ComponentName( getPackageName(),
                        JobSchedulerService.class.getName() ) );
        builder.setPeriodic(3000);

        if( mJobScheduler.schedule( builder.build() ) <= 0 ) {
            //If something goes wrong
            Toast.makeText(this, "Error scheduling job", Toast.LENGTH_SHORT).show();
        }
    }
}
