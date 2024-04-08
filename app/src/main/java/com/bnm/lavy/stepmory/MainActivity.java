package com.bnm.lavy.stepmory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bnm.lavy.lavyfinalproject.R;

public class  MainActivity extends AppCompatActivity {
    CustomView cv;
    boolean isPaused;
    BroadcastBattery broadcastBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int hiScore=intent.getIntExtra("key_hi", 0);
        int level =intent.getIntExtra("key_level" ,1);
        boolean isSoundOn= intent.getBooleanExtra("Key_sound", false);
        broadcastBattery = new BroadcastBattery(this);

        cv = new CustomView(this, hiScore ,isSoundOn, level);
         setContentView(cv);


    }

    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mane_action, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.mnSound)
            Toast.makeText(this, "Sound", Toast.LENGTH_SHORT).show();
        else if ( id == R.id.mnPause) {
            isPaused = !isPaused;
            cv.setTimer(isPaused);
        }
        else if ( id == R.id.mnEx)
            Toast.makeText(this, "show Explanation", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastBattery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastBattery);
    }
}

