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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View{

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float endX,endY;
    private float mX, mY;
    private int colorColor = Color.BLACK;   //initial color
    private static final float TOUCH_TOLERANCE = 5;
    Context context;

    protected boolean isRectangle = false; //planning to use this to determine if using the brush or not

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
    }

    public void setRect() {
        isRectangle = !isRectangle;
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

    public void setColor(String newC) {     //Makes sure the onDraw() method is called before changing
        invalidate();                       //colors to prevent the previously drawn line from changing
        colorColor = Color.parseColor(newC);//colors too.
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

        canvas.drawBitmap(mBitmap, 0, 0, null);     //AThe paint being null changes nothing noticeable.
        canvas.drawPath(mPath, mPaint);
    }

    private void startTouch(float x, float y) {    //updates the member values to reflect
        mPath.moveTo(x,y);                         //where the starting touch coords were.
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);            //If the difference in movement is too great on touch
        float dy = Math.abs(y - mY);            //treat it as if it didn't happen. Essentially.

        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {        //This is mostly used so the brush doesn't act funny when removing your finger.
            if(isRectangle) {
                endX = x;   //Used to draw the end
                endY = y;   //technically unnecessary since upTouch could just be changed to pass values into.
            } else {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);   //What allows brushwork.
                mX = x;     //Updates the member values since the path is already updated so by updating
                mY = y;     //the values of mX and mY allows TOUCH_TOLERANCE to work.
            }
        }

    }


    public void clearCanvas() { //Clears the canvas. User doesn't have to know it isn't a brand new canvas.
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);    /* Fill the entire canvas' bitmap (restricted
                                                        to the current clip) with the specified color and
                                                        porter-duff xfermode. */ /*PorterDuff is magic*/
        invalidate();
    }

    private void upTouch() {
        mPath.lineTo(mX,mY);
        if(isRectangle) {
            mCanvas.drawRect(mX,mY,endX,endY,mPaint);   //Draws the rectangle.
        } else {
            mCanvas.drawPath(mPath, mPaint);            //Draws the brushline based off the path.
        }
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
