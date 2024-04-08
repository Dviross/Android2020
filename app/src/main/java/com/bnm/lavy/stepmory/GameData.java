package com.bnm.lavy.stepmory;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;

public class GameData {
    private int seconds, minutes, staticMin, staticSec ; // time to end game
    private TimeBar timeBar;
    private int lifeRemain;
    private int currentScore;
    private int hiScore;
    private float wScreen, hScreen; // screen dimentions
    private int level; // game level
    private Sprite spriteHeadline;
    private Sprite [] life;
    private Board board;
    private float deltaBar;
    private  int pointSuccess,heartSum,bonusSec;
    private  boolean isOkToGo;

    public GameData(float wScreen, float hScreen,int hiScore, int level, Bitmap headline, Bitmap heart, Board board)
    {
        this.wScreen = wScreen;
        this.hScreen = hScreen;
        this.board=board;
        this.level = level;
        this.hiScore = hiScore;
        currentScore = 0;

        if ( level == 1)
        {
            minutes = 1;
            seconds = 0;
            this.pointSuccess=2;
            this.heartSum=15;
            this.bonusSec=0;
        }
        else if( level == 2 )
        {
            minutes = 0;
            seconds = 40;
            this.pointSuccess=3;
            this.heartSum=30;
            this.bonusSec=1;
        }
        else if( level == 3 )
        {
            minutes = 0;
            seconds = 30;
            this.pointSuccess=4;
            this.heartSum=50;
            this.bonusSec=2;
        }

        lifeRemain = 3;
        spriteHeadline= new Sprite(wScreen/20,wScreen/20,wScreen/1.7f,wScreen/5, headline);
        life = new Sprite[lifeRemain];
        float x=board.getX();
        float wLife=wScreen/15;
        float y=board.getY()-wLife*1.5f;
        for( int i=0; i< life.length; i++){
            life[i]=new Sprite(x, y, wLife, wLife, heart);
            x+=wLife*1.1f;
        }
        float xTBar=board.getX();
        Paint TBp = new Paint();
        this.timeBar=new TimeBar(xTBar,hScreen*0.93f, board.getW(), wLife*0.7f, null, Color.GREEN);
        this.deltaBar=board.getW()/(seconds+60*minutes);
        this.isOkToGo= true;
    }

    public void incPoint(){
        currentScore+=pointSuccess;
    }

    public void updateHiscore(){
        if(currentScore>hiScore) {
            hiScore = currentScore;
        }
    }

    public void draw(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        float textS1 = hScreen/18;
       // p.setTextAlign(Paint.Align.CENTER);
        //canvas.drawText("Memory Game", wScreen/2, textS1 + 1, p);
        spriteHeadline.draw(canvas);
        float textS2 = hScreen / 20;
        p.setTextSize(textS1);
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(getTimeString(), wScreen/20, spriteHeadline.getH() + textS2* 2, p);
        canvas.drawText("Score: "+currentScore, wScreen/3, spriteHeadline.getH() + textS2* 2, p);
        canvas.drawText("high Score: "+hiScore, wScreen/3, spriteHeadline.getH() + textS2* 4, p);
        for( int i=0; i<lifeRemain; i++){
            life[i].draw(canvas);
        }
        this.timeBar.draw(canvas);
    }


    /**
     * Decreases one second of time remaining in the game
     * Return true if the time remain yet
     */

    public boolean timeDecreas()
    {
        int totalSecond=seconds+ 60*minutes;
        if ( minutes > 0 || seconds > 0 )
        {
            if (seconds == 0 && minutes > 0 )
            {
                minutes--;
                seconds=59;
            }
            seconds --;
            if(timeBar.getW() > 0)
                timeBar.setW(timeBar.getW()-deltaBar);
            if(totalSecond<=20 )
                timeBar.setColor(Color.YELLOW);
            if(totalSecond<=10 )
                timeBar.setColor(Color.RED);

            return true;
        }

        isOkToGo=false;
        return false;
    }

    public int calcTotalScore(){
        if ( seconds > 0 || minutes > 0)
            currentScore =+ ( lifeRemain * heartSum ) + bonusSec*(seconds+60*minutes) ;

        else
            currentScore =+  bonusSec*(seconds+60*minutes) ;
        return currentScore;
    }

    public String getTimeString()
    {
        String st = String.valueOf(seconds);
        if ( seconds < 10)
            st = "0" + st;
        return   minutes + ":" + st;

    }
    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getLifeRemain() {
        return lifeRemain;
    }

    public void decLife(){
        lifeRemain--;
        isOkToGo=lifeRemain>0;
    }

    public boolean isOkGame(){
        return isOkToGo;
    }

    public void setLifeRemain(int lifeRemain) {
        this.lifeRemain = lifeRemain;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getHiScore() {
        return hiScore;
    }

    public void setHiScore(int hiScore) {
        this.hiScore = hiScore;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
