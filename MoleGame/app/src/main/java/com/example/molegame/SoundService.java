package com.example.molegame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import static com.example.molegame.MenuActivity.soundCode;
public class SoundService extends Service {
     MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        if(soundCode==1){
            player=MediaPlayer.create(SoundService.this,R.raw.backmenu);

        }
        if(soundCode==2){
            player=MediaPlayer.create(SoundService.this,R.raw.gamesound);

        }
        if (soundCode==3){
            player=MediaPlayer.create(SoundService.this,R.raw.endsound);

        }
        player.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getBooleanExtra("pause",false)==true)
        {
            player.pause();
        }
        else
        {
            player.start();
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }


}
