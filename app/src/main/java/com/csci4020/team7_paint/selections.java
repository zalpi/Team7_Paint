package com.csci4020.team7_paint;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class selections extends AppCompatImageView implements View.OnClickListener {

    int choice;
    TextView colorSelected;
    ImageView image;
    Button b;

    public selections(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public selections(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    public selections(Context context) {
        super(context);
        setUp();
    }

    public void setUp() {
        final ImageView im = this;

        im.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {


                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        Matrix invertMatrix = new Matrix();
                        im.getImageMatrix().invert(invertMatrix);

                        float[] mappedPoints = new float[]{event.getX(), event.getY()};
                        invertMatrix.mapPoints(mappedPoints);

                        if (im.getDrawable() != null && im.getDrawable() instanceof BitmapDrawable &&
                                mappedPoints[0] > 0 && mappedPoints[1] > 0 &&
                                mappedPoints[0] < im.getDrawable().getIntrinsicWidth() && mappedPoints[1] < im.getDrawable().getIntrinsicHeight())

                            choice = ((BitmapDrawable) im.getDrawable()).getBitmap().getPixel((int) mappedPoints[0], (int) mappedPoints[1]);

                        setColorOutput();
                        return true;
                }
                return false;
            }

        });

    }

    public void setOutput(TextView tv, ImageView im, Button b, int color) {
        this.colorSelected = tv;
        this.image = im;
        this.b = b;
        this.choice = color;

        setColorOutput();
        b.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(choice== Color.BLACK)
            choice = Color.WHITE;
        else
            choice=Color.BLACK;
        setColorOutput();
    }

    public void setColorOutput()
    {
        String text = "#";
        text = text + Integer.toHexString(choice);

        if (text.length() > 2)
            text = text.charAt(0) + text.substring(3);
        else
            text = "#000000";

        this.colorSelected.setText(text);
        image.setColorFilter(choice);
    }

}
