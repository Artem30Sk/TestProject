package com.example.molegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    TextView textRecord;
    SharedPreferences prefs;
    String score;
    public static int soundCode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        soundCode=1;
        stopService(new Intent(MenuActivity.this,SoundService.class));
        startService(new Intent(MenuActivity.this,SoundService.class));
        textRecord=(TextView) findViewById(R.id.textRecordCount);
        prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        textRecord.setText(prefs.getString("tag","0"));

    }
    public void onNextPage(View view){
        Intent intent= new Intent(MenuActivity.this,GameActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(MenuActivity.this,SoundService.class));
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        startService(new Intent(MenuActivity.this,SoundService.class).putExtra("pause",true));
        super.onPause();
    }

    @Override
    protected void onResume() {
        startService(new Intent(MenuActivity.this,SoundService.class).putExtra("pause",false));
        super.onResume();
    }
}