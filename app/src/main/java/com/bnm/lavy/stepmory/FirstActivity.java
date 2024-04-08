package com.bnm.lavy.stepmory;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.bnm.lavy.lavyfinalproject.R;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{
    Button btNext;
    RadioGroup rg1,rg2;
    RadioButton RBLevlExraHard,RBLevlHard,RBLevlEasy,RBMoff,RBMon;
    private int level, hiScore;
    private boolean soundStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        btNext=findViewById(R.id.btNext);
        rg1=findViewById(R.id.rg1);
        rg2=findViewById(R.id.rg2);
        RBLevlExraHard=findViewById(R.id.RBLevlExraHard);
        RBLevlHard=findViewById(R.id.RBLevlHard);
        RBLevlEasy=findViewById(R.id.RBLevlYeasy);
        RBMoff=findViewById(R.id.RBMoff);
        RBMon=findViewById(R.id.RBMon);

        rg1.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
        btNext.setOnClickListener(this);
        this.soundStatus=true;

        // ---- load data
        SharedPreferences sp= getSharedPreferences("data",0);
        level=sp.getInt("key_level",1);
        soundStatus=sp.getBoolean("key_sound",false);
        hiScore=sp.getInt("key_hi",0);
        if(level==0)
            RBLevlEasy.setChecked(true);
        else if(level==1)
            RBLevlHard.setChecked(true);
        else if(level==2)
            RBLevlExraHard.setChecked(true);
        //-------- sound
        if(soundStatus)
            RBMon.setChecked(true);
        else
            RBMoff.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class );
        intent.putExtra("key_level",level);
        intent.putExtra("Key_sound",soundStatus);
        intent.putExtra("key_hi",hiScore);
        startActivity(intent);
        //----save data
        SharedPreferences sp= getSharedPreferences("data",0);
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean("key_sound",soundStatus);
        editor.putInt("key_level",level);
        editor.apply();
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //------level
        level=1;
        if(checkedId==R.id.RBLevlHard)
            level=2;
        else if(checkedId==R.id.RBLevlExraHard)
            level=3;

        //--------sound

        if(checkedId==R.id.RBMoff)
            soundStatus=false;
        else {
            soundStatus=true;
        }
    }
}
