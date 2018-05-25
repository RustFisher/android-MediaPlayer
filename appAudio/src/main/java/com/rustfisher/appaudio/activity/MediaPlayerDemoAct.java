package com.rustfisher.appaudio.activity;

import android.os.Bundle;

import com.rustfisher.appaudio.R;
import com.rustfisher.appaudio.fragment.PlayOnlineAudioFragment;

/**
 * Created on 2019-9-18
 */
public class MediaPlayerDemoAct extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_play_online_audio);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.online_audio_page_container, new PlayOnlineAudioFragment())
                    .commitAllowingStateLoss();
        }
    }
}
