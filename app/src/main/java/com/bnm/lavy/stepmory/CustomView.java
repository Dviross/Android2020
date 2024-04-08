package com.bnm.lavy.stepmory;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;

import com.bnm.lavy.lavyfinalproject.R;

public class CustomView extends View {
    Context context;
    //private Sprite s;
    private float wScreen, hScreen;// screen dimentions
    private Bitmap [] picArr;
    private Board board;
    private int size; // number of rows = cols
    private GameData gd; // current game data
    private CountDownTimer timer;
    private Bitmap headline;
    private Bitmap heartPhoto;
    private int level;
    private boolean sound,getActive; //getActive - start the clock
    private boolean toContinue;
    private SoundPool ms;
    private int fSound,sSound;// int-?
    private boolean isPaused;
    private int hiScore;

    public CustomView(Context context, int hiScore, boolean isSoundOn, int level) {
        super(context);  //  ask lavy about the context
        this.context =context;
        size = 5;
        picArr = new Bitmap[size * size];
        picArr[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo6);
        picArr[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo7);
        picArr[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo8);
        picArr[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo9);
        picArr[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo10);
        picArr[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo11);
        picArr[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo12);
        picArr[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo13);
        picArr[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo14);
        picArr[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo15);
        picArr[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo16);
        picArr[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo17);
        picArr[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo18);
        picArr[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo19);
        picArr[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo20);
        picArr[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo21);
        picArr[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo22);
        picArr[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo23);
        picArr[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo24);
        picArr[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo25);
        picArr[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo26);
        picArr[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo27);
        picArr[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo28);
        picArr[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo29);
        picArr[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo30);
        headline = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_headline);
        heartPhoto = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_photo);
        this.level=level;

        this.getActive=false;
        this.toContinue=false;
//        AudioAttributes aa = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();// aa=?
//        ms=new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(aa).build();
//        fSound = ms.load(context.getResources(), R.raw.failure_sound,1);
//
//        sSound = ms.load(context, R.raw.secsses_sound,1);
        this.hiScore=hiScore;

        this.sound=isSoundOn;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //s.draw(canvas);
        board.draw(canvas);
        gd.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ex = event.getX();
        float ey = event.getY();
        if ( event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (board.isInside(ex, ey) && gd.isOkGame() && !isPaused) {
                if(board.isLast(ex, ey)) {
                    board.addSprite();
                    board.cleanBoard();
                    board.mix();
                    gd.incPoint();
                    getActive=true;
                }
                else{
                    // life down
                    if (board.isInside(ex, ey) && !isPaused) {
                        if(!board.isLast(ex, ey)) {
                            gd.decLife();
                        }
                    }
                }
            }

        }
        invalidate();
        return true;
    }

    public void startGame(){
        board = new Board(wScreen, hScreen, picArr, size,size );
        gd = new GameData(wScreen, hScreen, gd.getHiScore(), level, headline, heartPhoto, board);
        timer.start();
    }


    @Override
    protected void onSizeChanged(int wS, int hS, int oldw, int oldh) {
        super.onSizeChanged(wS, hS, oldw, oldh);
        wScreen = wS;
        hScreen = hS;
        float w = wScreen/4;
        float h = w/2;
        //s = new Sprite(wScreen/2,hScreen/2, w, h, picFlag);
        //s = new Sprite(wScreen - w,hScreen - h, w, h, null);
        board = new Board(wS, hS, picArr, size,size );
        gd = new GameData(wS, hS, hiScore, level, headline, heartPhoto, board);
        timer = new CountDownTimer(100000,1000 ) {
            @Override

            public void onTick(long l) {

                if (getActive) {
                    gd.timeDecreas();
                      invalidate();
                    if( !gd.isOkGame() ){
                        timer.cancel();
                        gd.calcTotalScore();
                        Toast.makeText(context, "You've earned " + gd.getCurrentScore() + " points", Toast.LENGTH_LONG).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("you ended the game");
                        builder.setMessage("do you want to play again");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Yes", new HandleAlertDialogListener());
                        builder.setNegativeButton("No", new HandleAlertDialogListener());
                        AlertDialog dialog=builder.create();
                        dialog.show();

                        gd.updateHiscore();
                        saveHiScore();
                    }
                }
            }

            @Override
            public void onFinish() {
                //Toast.makeText(context, "finished", Toast.LENGTH_SHORT).show();
            }
        };
        timer.start();
    }

    private void saveHiScore() {
        SharedPreferences sp= context.getSharedPreferences("data",0);
        SharedPreferences.Editor editor= sp.edit();
        editor.putInt("key_hi", gd.getHiScore());
        editor.apply();
    }


    public void setTimer(boolean paused) {
        this.isPaused=paused;
        if(paused)
            timer.cancel();
        else
            timer.start();
    }

    private class  HandleAlertDialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int button) {
            if ( button == Dialog.BUTTON_POSITIVE)
            {
                startGame();
            }
            else if ( button == Dialog.BUTTON_NEGATIVE)
            {
                Intent intent = new Intent(context , FirstActivity.class );
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        }
    }
}
