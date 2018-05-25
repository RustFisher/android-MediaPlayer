package com.rustfisher.mp4.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rustfisher.mp4.R;
import com.rustfisher.mp4.fragment.VideoViewFragment;

/**
 * Play mp4
 * Created by Rust on 2018/5/24.
 */
public class PlayMP4Activity extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_frame);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new VideoViewFragment()).commit();
        }
    }

}
