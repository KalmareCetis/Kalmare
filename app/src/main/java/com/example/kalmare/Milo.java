package com.example.kalmare;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Milo extends AppCompatActivity {

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.milo);
    }

    public void ron(View v) {
        mp = MediaPlayer.create(this, R.raw.ronroneo);
        mp.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mp != null && mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = null;
                }
            }
        }, 5000);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
            mp = null;
        }
    }
    public void volv(View v) {
        Intent volver = new Intent(Milo.this, MainActivity.class);
        startActivity(volver);
    }


}
