package com.csci4020.team7_paint;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/*IMPORTANT: Color picker and size thickness display, but will not change the color or size when drawing.*/


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int defaultColor = Color.BLACK;
    int drawThickness = 40;
    drawing draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_activity);

        draw = (drawing) findViewById(R.id.canvas);
        draw.list.setCurrent(defaultColor);
        draw.list.setCurrentThick(drawThickness);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.colorIB) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.color_picker);
            final selections cw = (selections) dialog.findViewById(R.id.colorView);
            TextView tv = (TextView) dialog.findViewById(R.id.what_color);
            ImageView im = (ImageView) dialog.findViewById(R.id.display);
            Button b = (Button) dialog.findViewById(R.id.black);
            cw.setOutput(tv, im, b, defaultColor);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    defaultColor = cw.choice;
                    draw.list.setCurrent(defaultColor);
                }
            });

            dialog.show();
        } else if (v.getId() == R.id.brushIB) {

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.size);

            SeekBar seek = (SeekBar) dialog.findViewById(R.id.sizes);
            seek.setMax(6);

            seek.setProgress(drawThickness);
            seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    drawThickness = progress;
                }
            });

            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.about)
        {
            Intent intent = new Intent(this,about_app.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
