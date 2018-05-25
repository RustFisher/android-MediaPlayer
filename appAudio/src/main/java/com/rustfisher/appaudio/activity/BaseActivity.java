package com.rustfisher.appaudio.activity;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 基础Activity
 * Created on 2019-9-18
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = "rustApp";

    protected void setOnClick(View.OnClickListener onClickListener, int... resIDs) {
        for (int id : resIDs) {
            findViewById(id).setOnClickListener(onClickListener);
        }
    }
}
