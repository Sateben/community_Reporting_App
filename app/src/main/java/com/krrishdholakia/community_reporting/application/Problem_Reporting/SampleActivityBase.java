package com.krrishdholakia.community_reporting.application.Problem_Reporting;

/**
 * Created by community_reporting on 14/10/16.
 */
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.krrishdholakia.community_reporting.application.logger.Log;
import com.krrishdholakia.community_reporting.application.logger.LogWrapper;

/**
 * Base launcher activity, to handle most of the common plumbing for samples.
 */
public class SampleActivityBase extends FragmentActivity {

    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
    }

    /** Set up targets to receive log data */
    public void initializeLogging() {
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);

        Log.i(TAG, "Ready");
    }
}
