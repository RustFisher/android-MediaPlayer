package com.rustfisher.appaudio.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rustfisher.appaudio.R;
import com.rustfisher.appaudio.widget.SimpleTextReAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Locale;

/**
 * 用来播放在线音频
 * Created on 2019-9-18
 */
public class PlayOnlineAudioFragment extends BaseFragment {
    private String audioUrl = "https://downdb.51voa.com/201909/combination-pill-tested-in-us-for-better-health-results.mp3";

    private MediaPlayer mediaPlayer;
    private boolean prepared = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPlayer();
    }

    private void initPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onPrepared");
                prepared = true;
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion: play sound.");
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Log.d(TAG, "Play online sound onError: " + i + ", " + i1);
                return true;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_play_online_audio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnClick(onClickListener, view, R.id.play, R.id.pause, R.id.stop);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.play:
                    if (!prepared) {
                        Log.w(TAG, "Not prepared");
                        break;
                    }
                    try {
                        mediaPlayer.start();
                    } catch (Exception e) {
                        Log.e(TAG, "Play error: ", e);
                    }
                    break;
                case R.id.pause:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                    break;
                case R.id.stop:
                    mediaPlayer.stop();
                    prepared = false;
                    mediaPlayer.prepareAsync();
                    break;
            }
        }
    };

    private void playOnlineSound(String soundUrlDict) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(soundUrlDict);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mp != null) {
                        mp.release();
                    }
                    Log.d(TAG, "onCompletion: play sound.");
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.d(TAG, "Play online sound onError: " + i + ", " + i1);
                    return false;
                }
            });
        } catch (IOException e1) {
            Log.e(TAG, "url: ", e1);
        }
    }

    /**
     * 下载音频流
     * Created on 2019-9-18
     */
    public class DownloadStreamThread extends Thread {
        String urlStr;
        final String targetFileAbsPath;

        public DownloadStreamThread(String urlStr, String targetFileAbsPath) {
            this.urlStr = urlStr;
            this.targetFileAbsPath = targetFileAbsPath;
        }

        @Override
        public void run() {
            super.run();
            int count;
            File targetFile = new File(targetFileAbsPath);
            try {
                boolean n = targetFile.createNewFile();
                Log.d(TAG, "Create new file: " + n + ", " + targetFile);
            } catch (IOException e) {
                Log.e(TAG, "run: ", e);
            }
            try {
                URL url = new URL(urlStr);
                URLConnection connection = url.openConnection();
                connection.connect();
                int contentLength = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(targetFileAbsPath);

                byte[] buffer = new byte[1024];
                long total = 0;
                while ((count = input.read(buffer)) != -1) {
                    total += count;
                    Log.d(TAG, String.format(Locale.CHINA, "Download progress: %.2f%%", 100 * (total / (double) contentLength)));
                    output.write(buffer, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e(TAG, "run: ", e);
            }
        }
    }

    protected void setOnClick(View.OnClickListener onClickListener, View root, int... resIDs) {
        for (int id : resIDs) {
            root.findViewById(id).setOnClickListener(onClickListener);
        }
    }
}
