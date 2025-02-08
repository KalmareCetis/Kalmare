package com.example.kalmare;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Musica extends AppCompatActivity {

    ImageView play, prev, next, imageView;
    TextView songTitle;
    SeekBar mSeekBarTime;
    static MediaPlayer mMediaPlayer;
    int Indexnow = 0;
    private final ArrayList<Integer> canciones = new ArrayList<>();
    private final Handler handler = new SeekBarHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musica);

        play = findViewById(R.id.play);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        imageView = findViewById(R.id.imagen);
        songTitle = findViewById(R.id.titulo);
        mSeekBarTime = findViewById(R.id.barra);

        populateSongList();
        initializeMediaPlayer();
        play.setOnClickListener(v -> togglePlayback());
        next.setOnClickListener(v -> playNextSong());
        prev.setOnClickListener(v -> playPreviousSong());
        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mMediaPlayer != null) {
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        startSeekBarUpdater();
    }

    private void populateSongList() {
        canciones.add(R.raw.aheartbreakingplace);
        canciones.add(R.raw.calm);
        canciones.add(R.raw.drawonthetree);
        canciones.add(R.raw.dreaming);
        canciones.add(R.raw.justrelax);
        canciones.add(R.raw.kalimbasoftbackground);
        canciones.add(R.raw.kalimbasong);
        canciones.add(R.raw.lofi);
        canciones.add(R.raw.safeandsound);
        canciones.add(R.raw.silentlybesideyou);
        canciones.add(R.raw.sinkingdreams);
    }

    private void initializeMediaPlayer() {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, canciones.get(Indexnow));
        mSeekBarTime.setMax(mMediaPlayer.getDuration());
        updateSongDetails();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void togglePlayback() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                play.setImageResource(R.drawable.play);
            } else {
                mMediaPlayer.start();
                play.setImageResource(R.drawable.pause);
            }
        }
    }

    private void playNextSong() {
        Indexnow = (Indexnow + 1) % canciones.size();
        initializeMediaPlayer();
        mMediaPlayer.start();
        play.setImageResource(R.drawable.pause);
    }

    private void playPreviousSong() {
        Indexnow = (Indexnow - 1 + canciones.size()) % canciones.size();
        initializeMediaPlayer();
        mMediaPlayer.start();
        play.setImageResource(R.drawable.pause);
    }

    private void updateSongDetails() {
        String[] songTitles = {
                "A Heartbreaking Place", "Calm", "Draw On The Tree", "Dreaming",
                "Just Relax", "Kalimba Soft Background", "Kalimba Song", "Lo Fi",
                "Safe And Sound", "Silently Beside You", "Sinking Dreams"
        };

        int[] imageResources = {
                R.drawable.girluno, R.drawable.girldos, R.drawable.girltres,
                R.drawable.girlcuatro, R.drawable.girlcinco, R.drawable.landscape,
                R.drawable.moonlit, R.drawable.park, R.drawable.trees,
                R.drawable.sunrise, R.drawable.island
        };

        if (Indexnow >= 0 && Indexnow < songTitles.length) {
            songTitle.setText(songTitles[Indexnow]);
            imageView.setImageResource(imageResources[Indexnow]);
        }
    }

    private void startSeekBarUpdater() {
        new Thread(() -> {
            while (true) {
                try {
                    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                        Message message = new Message();
                        message.what = mMediaPlayer.getCurrentPosition();
                        handler.sendMessage(message);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static class SeekBarHandler extends Handler {
        private final WeakReference<Musica> activity;

        SeekBarHandler(Musica musica) {
            activity = new WeakReference<>(musica);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Musica musica = activity.get();
            if (musica != null && musica.mMediaPlayer != null) {
                musica.mSeekBarTime.setProgress(msg.what);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    public void volv(View v) {
        Intent volver = new Intent(Musica.this, MainActivity.class);
        startActivity(volver);
    }
}
