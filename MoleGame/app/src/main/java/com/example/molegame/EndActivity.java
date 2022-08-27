package com.example.molegame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import static com.example.molegame.MenuActivity.soundCode;
public class EndActivity extends AppCompatActivity {
    Intent intent;
    TextView textScore,textRecord;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        soundCode=3;
        stopService(new Intent(EndActivity.this,SoundService.class));
        startService(new Intent(EndActivity.this,SoundService.class));
        textScore=(TextView)findViewById(R.id.textEndScoreCount);
        textRecord=(TextView)findViewById(R.id.textEndRecordCount);
        prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        String Score="";
        intent=getIntent();
        textScore.setText(intent.getStringExtra("Score"));
        textRecord.setText(prefs.getString("tag","0"));
    }
    public void onAgainPlay(View view){
        intent=new Intent(EndActivity.this,GameActivity.class);
        startActivity(intent);
    }
    public void onMainMenu(View view){
        intent=new Intent(EndActivity.this,MenuActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        stopService(new Intent(EndActivity.this,SoundService.class));
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        startService(new Intent(EndActivity.this,SoundService.class).putExtra("pause",true));
        super.onPause();
    }

    @Override
    protected void onResume() {
        startService(new Intent(EndActivity.this,SoundService.class).putExtra("pause",false));
        super.onResume();
    }
}