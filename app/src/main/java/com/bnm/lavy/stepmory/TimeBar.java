package com.bnm.lavy.stepmory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class TimeBar extends Sprite{
    private int color;
    public TimeBar(float x, float y, float w, float h, Bitmap pic, int color) {
        super(x, y, w, h, pic);
        this.color=color;

    }

     public void draw(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(this.color);
        canvas.drawRect(x,y, x + w, y + h, p);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
