package com.csci4020.zachary_paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Pigott on 4/3/2017.
 */

public class CanvasView extends View{

    public int width; //never used
    public int height; //never used
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint, mCanvasPaint;
    private float mX, mY;
    private int colorColor = Color.BLACK;   //initial color
    private static final float TOUCH_TOLERANCE = 5;
    Context context;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        setUpCanvas();
    }

    private void setUpCanvas() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(colorColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(8f);

        mCanvasPaint = new Paint(Paint.DITHER_FLAG); //Paint flag that enables dithering when blitting. TT
    }

    public float getStrokeW() {     //Since mPaint is private, use this to determine which image to use.
        return mPaint.getStrokeWidth();
    }

    public void growBigger() {      //It does what it says. This method is used to cycle between thickness.
        invalidate();
        if(mPaint.getStrokeWidth() == 8f) {
            mPaint.setStrokeWidth(16f);
        } else if(mPaint.getStrokeWidth() == 16f) {
            mPaint.setStrokeWidth(32f);
        } else if(mPaint.getStrokeWidth() == 32f) {
            mPaint.setStrokeWidth(8f);
        }
    }

    public void setColor(String newC) {
        invalidate();
        colorColor = Color.parseColor(newC);
        mPaint.setColor(colorColor);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, mCanvasPaint);
        canvas.drawPath(mPath, mPaint);
    }

    private void startTouch(float x, float y) {
        mPath.moveTo(x,y);
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
            mX = x;
            mY = y;
        }
    }


    public void clearCanvas() { //Clears the canvas. User doesn't have to know it isn't a brand new canvas.
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    private void upTouch() {
        mPath.lineTo(mX,mY);
        mCanvas.drawPath(mPath,mPaint);
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();               //invalidate calls onDraw() to execute.
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}
