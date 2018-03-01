package com.appzelof.skurring.mediaPlayer;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.Util;

import javax.sql.DataSource;

/**
 * Created by daniel on 01/03/2018.
 */

public class ExoPlayz {

    ExoPlayer exoPlayer;
    private String string;
    private MediaSource mediaSource;
    private Context context;
    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int BUFFER_SEGMENT_COUNT = 256;

    public ExoPlayz(ExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;

    }

    public void play(String urlString){


    }
}
