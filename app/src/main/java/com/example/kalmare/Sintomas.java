package com.example.kalmare;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Sintomas extends AppCompatActivity {
    MediaPlayer musica = null;
    int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sintomas);
        iniciar();
    }
    public void vuelve(View v) {
        Intent volver = new Intent(Sintomas.this, InfoAns.class);
        startActivity(volver);
    }
    public void iniciar() {
        destruir();
        musica = MediaPlayer.create(this, R.raw.lluvia);
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
        musica=MediaPlayer.create(this, R.raw.lluvia);
        musica.seekTo(savedInstanceState.getInt("pos"));
        musica.start();
    }
}