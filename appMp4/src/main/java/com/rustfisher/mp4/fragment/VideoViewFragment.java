package com.rustfisher.mp4.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.rustfisher.mp4.R;

import java.io.File;

/**
 * 使用 {@link VideoView} 播放MP4
 * Created by Rust on 2018/5/22.
 */
public class VideoViewFragment extends Fragment {
    private static final String TAG = "rustApp";
    private static String mMP4Path;
    VideoView mVideoView;
    MediaController mMediaController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        mMP4Path = dir.getAbsolutePath();
        Log.d(TAG, "onCreate: dir -> " + mMP4Path);
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                Log.d(TAG, " ----- " + f);
                if (f.getAbsolutePath().endsWith(".mp4")) {
                    mMP4Path = f.getAbsolutePath();
                    Log.d(TAG, "onCreate: mp4 -> " + mMP4Path);
                    break;
                }
            }
        } else {
            Log.e(TAG, "onCreate: DCIM not exist");
        }
        if (TextUtils.isEmpty(mMP4Path)) {
            Toast.makeText(getContext(), "MP4 not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_video_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView pathTv = view.findViewById(R.id.path_tv);
        mVideoView = view.findViewById(R.id.video_view);
        Log.d(TAG, "onViewCreated: mMP4Path: " + mMP4Path);
        mMediaController = new MediaController(getContext());
        if (!TextUtils.isEmpty(mMP4Path)) {
            mVideoView.setVideoPath(mMP4Path);
            mVideoView.setMediaController(mMediaController);
            mVideoView.seekTo(0);
            mVideoView.requestFocus();
            mVideoView.start();
            pathTv.setText(mMP4Path);
        }
    }

}
