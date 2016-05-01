package com.example.titos.movieguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by TITOS on 3/27/2016.
 */
public class splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Thread myProgress = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(2500);
                    Intent startMainScreen =new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(startMainScreen);
                    finish();
                }catch (InterruptedException g){
                    g.printStackTrace();
                }

            }


        };
        myProgress.start();
    }
}

