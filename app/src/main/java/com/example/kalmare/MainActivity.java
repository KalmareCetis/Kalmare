package com.example.kalmare;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    MediaPlayer musica = null;
    int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciar();
    }
    public void resp(View v) {
        Intent respirar = new Intent(MainActivity.this, Respira.class);
        startActivity(respirar);
    }

    public void inf(View v) {
        Intent informa = new Intent(MainActivity.this, InfoAns.class);
        startActivity(informa);
    }

    public void cat(View v) {
        Intent gatito = new Intent(MainActivity.this, Milo.class);
        startActivity(gatito);
    }

    public void music(View v) {
        Intent nota = new Intent(MainActivity.this, Musica.class);
        startActivity(nota);
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