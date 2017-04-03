package com.csci4020.zachary_paint;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.github.veritas1.verticalslidecolorpicker.VerticalSlideColorPicker;

public class MainActivity extends AppCompatActivity {

    private  CanvasView canvasView;
    private ImageButton currColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = (CanvasView) findViewById(R.id.canvas);


        //used to retrieve the first, non-color-picker, color. Which will be green since CanvasView has it set to black. Also personal tastes
        LinearLayout colorLayout = (LinearLayout)findViewById(R.id.linearlay_colors);
        currColor = (ImageButton)colorLayout.getChildAt(4); //Should be green, if ir's not green I have no idea what I'm doing.
        //insert a way to show that the current color is chosen to the user here. Paid dlc idk



        //copy-pasta'd from https://github.com/veritas1/vertical-slide-color-picker since that's the library we're using.
        final VerticalSlideColorPicker colorPicker = (VerticalSlideColorPicker) findViewById(R.id.color_picker);
        final View selectedColorView = findViewById(R.id.selected_color);

        colorPicker.setOnColorChangeListener(new VerticalSlideColorPicker.OnColorChangeListener() {
            @Override
            public void onColorChange(int selectedColor) {
                selectedColorView.setBackgroundColor(selectedColor);
            }
        });

    }

    public void clearCanvas(View v) {
        //Warning dialog informing the user that the current drawing will be erased if they hit 'confirm'.
        AlertDialog.Builder warnDialog = new AlertDialog.Builder(this);
        warnDialog.setTitle("Confirmation");
        warnDialog.setMessage("Are you sure you want to start a new drawing? Current drawing will not be saved.");
        warnDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                canvasView.clearCanvas();;
                dialog.dismiss();
            }
        });
        warnDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        warnDialog.show();

    }

    public void colorClicked(View v) { //method that goes off when the user clicks on the image buttons.
        if (v != currColor) { //make sure that the user hasn't changed the same color or something.
            ImageButton imgColor = (ImageButton) v;
            String color = v.getTag().toString();   //retrieve the image button's tags that hold their color.
            canvasView.setColor(color);
        }
    }
}
