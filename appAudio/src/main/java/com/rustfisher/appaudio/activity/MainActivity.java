package com.rustfisher.appaudio.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rustfisher.appaudio.R;

import java.io.File;
import java.io.IOException;

/**
 * Created on 2019-9-18
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQ_PER_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnClick(this, R.id.main_page_go_play_online, R.id.main_page_play_assets_audio,
                R.id.main_page_play_audio_file);
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_PER_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PER_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Please grant app to write SD card", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.main_page_go_play_online:
                startActivity(new Intent(this, MediaPlayerDemoAct.class));
                break;
            case R.id.main_page_play_assets_audio:
                playAssetsAudio("How are you.mp3", getApplicationContext());
                break;
            case R.id.main_page_play_audio_file:
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "banana.mp3");
                playAudioFile(file);
                break;
            default:
                break;
        }
    }

    /**
     * 尝试播放assets里面的发音
     */
    private void playAssetsAudio(final String name, Context context) {
        Log.d(TAG, "playAssetWordSound: try to play assets sound file. -> " + name);
        AssetFileDescriptor fd = null;
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            fd = context.getApplicationContext().getAssets().openFd(name);
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Log.d(TAG, "onPrepared: " + name);
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    Log.d(TAG, "onCompletion: " + name);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int i, int i1) {
                    mp.release();
                    return true;
                }
            });
        } catch (Exception e) {
            try {
                if (fd != null) {
                    fd.close();
                }
            } catch (Exception e1) {
                Log.e(TAG, "Exception close fd: ", e1);
            }
        } finally {
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                    Log.e(TAG, "Finally, close fd ", e);
                }
            }
        }
    }

    private void playAudioFile(final File file) {
        Log.d(TAG, "playAudioFile: " + file.getAbsolutePath());
        MediaPlayer mediaPlayer;
        try {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.fromFile(file));
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    Log.d(TAG, "onCompletion: " + file);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.d(TAG, "Play local sound onError: " + i + ", " + i1);
                    return true;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "playAudioFile: ", e);
        }
    }

}
