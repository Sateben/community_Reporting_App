package com.krrishdholakia.community_reporting.application.App_Sign_In;

/**
 * Created by community_reporting on 13/10/16.
 */
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.krrishdholakia.community_reporting.application.R;
import com.google.android.gms.common.ConnectionResult;

public abstract class BaseActivity extends AppCompatActivity {

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public abstract void onConnectionFailed(@NonNull ConnectionResult connectionResult);
}
