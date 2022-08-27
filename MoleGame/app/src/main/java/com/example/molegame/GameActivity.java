package com.example.molegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.logging.Handler;
import static com.example.molegame.MenuActivity.soundCode;
public class GameActivity extends AppCompatActivity {
    ImageView imageHole1,imageHole2,imageHole3,imageHole4,imageHole5,imageHole6,imageMole;
    TextView textScore,textTimer;
    SharedPreferences prefs;
    boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        soundCode=2;
        stopService(new Intent(GameActivity.this,SoundService.class));
        startService(new Intent(GameActivity.this,SoundService.class));
         prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        ImageView images[]= new ImageView[6];
        imageHole1=(ImageView) findViewById(R.id.moleholeFirst);
        imageHole2=(ImageView) findViewById(R.id.moleholeSec);
        imageHole3=(ImageView) findViewById(R.id.moleholeThird);
        imageHole4=(ImageView) findViewById(R.id.moleholeFourth);
        imageHole5=(ImageView) findViewById(R.id.moleholeFifth);
        imageHole6=(ImageView) findViewById(R.id.moleholeSixth);
        textScore=(TextView) findViewById(R.id.textScoreCount);
        textTimer=(TextView) findViewById(R.id.textTimerCount);
        imageMole=(ImageView) findViewById(R.id.imageMole);
        images[0]=imageHole1;
        images[1]=imageHole2;
        images[2]=imageHole3;
        images[3]=imageHole4;
        images[4]=imageHole5;
        images[5]=imageHole6;

        imageMole.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final MediaPlayer mp = MediaPlayer.create(GameActivity.this,R.raw.hitsound);
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        textScore.setText(Integer.toString(Integer.parseInt(textScore.getText().toString())+1));
                        mp.start();
                        check=true;
                    }
                }
                return false;
            }
        });
        new CountDownTimer(30000,1000){

            int x=0;
            final Random random = new Random();
            @Override
            public void onTick(long l) {
                x=Integer.parseInt(textTimer.getText().toString());
                x-=1;
                textTimer.setText(Integer.toString(x));
                int j = random.nextInt(6);
                new CountDownTimer(500,500){
                    @Override
                    public void onTick(long l) {
                        imageMole.setVisibility(View.VISIBLE);
                        for (int i = 0; i < images.length; i++) {
                            if(i==j){
                                int[] location = new int[2];
                                images[i].getLocationOnScreen(location);
                                imageMole.setX(location[0]);
                                imageMole.setY(location[1]-270);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        if(check==true){
                            new CountDownTimer(400,400){
                                @Override
                                public void onTick(long l) {
                                    imageMole.setImageResource(R.drawable.molehit);
                                }

                                @Override
                                public void onFinish() {
                                    imageMole.setImageResource(R.drawable.mole);
                                    check=false;
                                }
                            }.start();
                        }
                        else {
                            imageMole.setVisibility(View.GONE);
                        }
                    }
                }.start();
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(GameActivity.this,EndActivity.class);
                String Score=textScore.getText().toString();
                intent.putExtra("Score",Score);
                startActivity(intent);
            }
        }.start();
    }
    @Override
    protected void onStop() {
            if(Integer.parseInt(prefs.getString("tag","0"))<(Integer.parseInt(textScore.getText().toString()))){
                prefs.edit().putString("tag",textScore.getText().toString()).apply();
            }
        super.onStop();
    }
    @Override
    protected void onDestroy() {

        stopService(new Intent(GameActivity.this,SoundService.class));
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        startService(new Intent(GameActivity.this,SoundService.class).putExtra("pause",true));
        super.onPause();
    }

    @Override
    protected void onResume() {
        startService(new Intent(GameActivity.this,SoundService.class).putExtra("pause",false));
        super.onResume();
    }
}