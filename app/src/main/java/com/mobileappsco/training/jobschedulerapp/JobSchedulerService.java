package com.mobileappsco.training.jobschedulerapp;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class JobSchedulerService extends JobService {

    private final int JOB_ID = 123;

    private Handler mJobHandler = new Handler( new Handler.Callback() {

        @Override
        public boolean handleMessage( Message msg ) {
            Toast.makeText( getApplicationContext(),
                    "JobService task running", Toast.LENGTH_SHORT )
                    .show();
            jobFinished( (JobParameters) msg.obj, false );
            return true;
        }

    } );

    @Override
    public boolean onStartJob(JobParameters params) {
        mJobHandler.sendMessage( Message.obtain( mJobHandler, JOB_ID, params ) );
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mJobHandler.removeMessages( JOB_ID );
        return false;
    }

}
