package com.example.kalmare;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CincoUno extends AppCompatActivity {

    private int Indexact = 0;
    private String[] texto;
    private final Handler handler = new Handler();
    MediaPlayer musica = null;
    int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cincouno);
        iniciar();

        texto = getResources().getStringArray(R.array.cinco);

        TextView fadingText = findViewById(R.id.texto_fade);

        updateTextPeriodically(fadingText, 6000);
    }


    private void updateTextPeriodically(TextView textView, int delayMillis) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                fadeOut.setDuration(1000);
                textView.startAnimation(fadeOut);

                fadeOut.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(android.view.animation.Animation animation) {}

                    @Override
                    public void onAnimationEnd(android.view.animation.Animation animation) {
                        textView.setText(texto[Indexact]);

                        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                        fadeIn.setDuration(1000);
                        textView.startAnimation(fadeIn);

                        Indexact = (Indexact + 1) % texto.length;
                    }

                    @Override
                    public void onAnimationRepeat(android.view.animation.Animation animation) {}
                });
                handler.postDelayed(this, delayMillis);
            }
        }, delayMillis);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    public void vuelve(View v) {
        Intent volver = new Intent(CincoUno.this, Respira.class);
        startActivity(volver);
    }

    public void iniciar() {
        destruir();
        musica = MediaPlayer.create(this, R.raw.kalimba);
        musica.start();
        musica.setLooping(true);
    }
    public void pausar(View v){
        if (musica != null && musica.isPlaying())
        {
            posicion = musica.getCurrentPosition();
            musica.pause();
        }
    }
    public void continuar(View v){
        if (musica != null && musica.isPlaying() == false)
        {
            musica.seekTo(posicion);
            musica.start();
        }
    }
    public void detener(View v){
        if(musica != null)
        {
            musica.stop();
            posicion = 0;
        }
    }
    public void destruir()
    {
        if(musica != null)
            musica.release();
    }

    @Override
    protected void onPause(){
        if (musica!=null&& musica.isPlaying()){
            posicion=musica.getCurrentPosition();
            musica.pause();
        }
        super.onPause();
    }

    @Override
    protected void onStop(){
        if(musica!=null&&musica.isPlaying()){
            posicion=musica.getCurrentPosition();
            musica.pause();
        }
        super.onStop();
    }

    @Override
    protected void onResume(){
        if(musica!=null&&musica.isPlaying()==false){
            musica.seekTo(posicion);
            musica.start();
        }
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("pos", musica.getCurrentPosition());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        destruir();
        musica=MediaPlayer.create(this, R.raw.kalimba);
        musica.seekTo(savedInstanceState.getInt("pos"));
        musica.start();
    }
}
