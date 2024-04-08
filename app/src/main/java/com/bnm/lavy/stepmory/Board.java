package com.bnm.lavy.stepmory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Implements game board
 */
public class Board {
    private float x,y; // upper left corner
    private float w, h; // width and height
    private int rows, cols; // number of rows and columns
    private float wScreen, hScreen; // screen dimentions
    private ArrayList<Sprite> source; // all sprites collection
    private ArrayList<Sprite> dest; // on board sprites collection
    private boolean [][] cells; // if true - the cell is not empty
    private Bitmap[] pic;
    Random rnd = new Random();
    Sprite selected = null;

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

    // The size of the picArr must be equals to rows * cols
    public Board( float wScreen, float hScreen, Bitmap[] picArr, int rows, int cols)
    {
        this.wScreen = wScreen;
        this.hScreen = hScreen;
        this.rows = rows;
        this.cols = cols;
        this.pic = picArr;
        this.w = wScreen * 0.8f;
        this.h = w;
        this.x = wScreen/2 - this.w/2;
        this.y = hScreen * 0.4f;
        initBoard();

    }
    public void draw(Canvas canvas)
    {
        Paint p = new Paint();
        // draw background
        p.setColor( Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(x,y, x+w, y +h, p);
        // frame
        p.setColor(Color.rgb( 150,150,150));
        p.setStrokeWidth(10);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x-6,y -6, x+w + 6, y +h + 6, p);
        for ( Sprite s: dest)
        {
            s.draw(canvas);
        }
    }

    public void initBoard()
    {
        source = new ArrayList<>();
        int numSprites = rows * cols;
        float wSprite = w / cols;
        float x = this.x;
        float y = this.y;
        for(int i = 0; i < numSprites; i++)
        {
            source.add(new Sprite(x,y, wSprite, wSprite, pic[i]));
            x += wSprite;
            if ( (i + 1) % cols == 0)
            {
                x = this.x;
                y += wSprite;
            }
        }
        dest = new ArrayList<>();
        cells = new boolean [rows][cols];
        addSprite();
    }

    public void cleanBoard(){

        for(int i=0; i<rows; i++)
        {
            for(int j=0;j<cols;j++)
              cells[i][j]=false;
        }
    }
    private void setRandomLocation ( Sprite s)
    {
        int row, col;
        do {
            row = rnd.nextInt(cells.length);
            col = rnd.nextInt(cells[0].length);
        } while ( cells[row][col] );
        cells[row][col] = true;
        s.setX( this.x + col * s.getW());
        s.setY( this.y + row * s.getH());
    }

    public void mix()
    {
        for(Sprite s: dest)
        {
            setRandomLocation(s);
        }
    }



    public boolean isLast(float x, float y)
    {
        for (Sprite s: dest)
        {
            if(s.isInside(x,y)&& s==selected)
                return true;
        }
          return false;
    }

    /**
     * Returns true if (x,y ) is inside of one sprite from dest
     * @param x
     * @param y
     * @return
     */
    public boolean isInside(float x, float y)
    {
        for(Sprite s: dest)
        {
            if (s.isInside(x,y))
            {
                return true;
            }
        }
        return false;
    }
    public void addSprite()
    {
        // select randomal sprite from soure
        int sel = rnd.nextInt(source.size());
        Sprite newS = source.get(sel);
        dest.add(newS);
        source.remove(newS);

        // select randomal location on the board

        setRandomLocation(newS);
        selected = newS;

    }
}
