package com.rustfisher.mp4.act;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.rustfisher.mp4.R;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final int REQ_PER = 10;
    private String[] mPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, mPermissions, REQ_PER);
        }
        initUI();
    }

    private void initUI() {
        findViewById(R.id.go_mp4_btn).setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_mp4_btn:
                startActivity(new Intent(getApplicationContext(), PlayMP4Activity.class));
                break;
        }
    }
}
