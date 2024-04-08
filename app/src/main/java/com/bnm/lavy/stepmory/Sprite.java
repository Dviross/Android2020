package com.bnm.lavy.stepmory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Implements basic sprite
 */
public class Sprite {
    protected float x,y; // upper left corner coordinates
    protected float w, h;// width and height
    protected Bitmap pic;
    protected Paint p;

    public Sprite(float x, float y, float w, float h, Bitmap pic) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.pic = pic;
        if ( pic != null)
            this.pic = Bitmap.createScaledBitmap(pic,(int)w, (int)h, true);
    }


    public boolean isInside(float x, float y)
    {
        return (x >= this.x && x<= this.x + this.w &&
                  y >= this.y && y <= this.y + this.h);
    }
    public void draw(Canvas canvas)
    {
        Paint p = new Paint();
        if( pic == null) //no picture
        {
            canvas.drawRect(x,y, x + w, y + h, p);
        }
        else // draw picture
        {
            canvas.drawBitmap(this.pic, x, y, p);
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }
}
