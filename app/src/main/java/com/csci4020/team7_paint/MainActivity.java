package com.csci4020.team7_paint;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener {

    private int button;
    private boolean buttonChosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.start);
        b.setOnClickListener(this);
        RadioGroup rg = (RadioGroup) findViewById(R.id.selections);
        rg.setOnClickListener(this);

        findViewById(R.id.about).setOnClickListener(new informationDialog());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start) {
            if (buttonChosen == false) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Please load a project or start a new one");
                builder.setPositiveButton("OK", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }

            else {
                Intent intent = new Intent(getApplicationContext(), DrawingActivity.class);
                        intent.putExtra("button", button);
                startActivity(intent);
            }
        }
    }

    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        if(checkId == R.id.new_file) {
            buttonChosen = true;
            button = 1;
        }
        if (checkId == R.id.load) {
            buttonChosen = true;
            button = 2;
        }
    }

    private class informationDialog implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String message = "<html>" + "<h1>About App: <br></h1>" +
                    "<h2>Programmers: <b>Briana Schmidt and Zachary Pigott</b>" +
                    "</h2>" + "</html>";
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("OK", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
