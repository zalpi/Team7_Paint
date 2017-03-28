package com.csci4020.team7_paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

public class paint extends View {


    //for path
    private Path draw;

    //for painting
    private Paint pathPaint, viewPaint, linePaint, rectanglePaint;

    //startup color
    private int color = 0xFF000000;
    private int black = Color.parseColor("#000000");

    //canvas
    private Canvas drawingCanvas;

    //bitmap
    private Bitmap canvasB;

    //stroke size
    private float startSize = 4;
    private float strokeW;

    public enum TOOLS {BRUSH, LINE, RECTANGLE, ERASER, SAVE};
    private TOOLS currentSelected = TOOLS.BRUSH;

    public paint (Context context){
        super(context);
    }

    public paint (Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public paint (Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

}
