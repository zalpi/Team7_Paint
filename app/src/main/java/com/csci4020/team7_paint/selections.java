package com.csci4020.team7_paint;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;


public class selections extends AppCompatImageView implements View.OnClickListener {

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

    }

    public void onClick(View view) {}

}
