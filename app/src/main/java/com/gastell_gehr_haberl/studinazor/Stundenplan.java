package com.gastell_gehr_haberl.studinazor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.CollapsibleActionView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;


/**
 * Created by lucas on 09.08.2017.
 */

public class Stundenplan extends AppCompatActivity {

    Button mondayButton;
    Button tuesdayButton;
    Button wednesdayButton;
    Button thursdayButton;
    Button fridayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stundenplan);
        initUI();
        initCourseButtons();
    }



    private void initUI() {
        mondayButton = (Button) findViewById(R.id.button_monday);
        tuesdayButton = (Button) findViewById(R.id.button_tuesday);
        wednesdayButton = (Button) findViewById(R.id.button_wednesday);
        thursdayButton = (Button) findViewById(R.id.button_thursday);
        fridayButton = (Button) findViewById(R.id.button_friday);
    }


    private void initCourseButtons() {
        mondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startMonday = new Intent(Stundenplan.this, StundenplanMonday.class);
                startActivity(startMonday);
            }
        });

      /*  tuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startEinkauf = new Intent(Stundenplan.this, Einkaufsliste.class);
                startActivity(startEinkauf);
            }
        });

        wednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startStundenplan = new Intent (Stundenplan.this, Stundenplan.class);
                startActivity(startStundenplan);
                }
            });

        thursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent startNewsfeed = new Intent (Stundenplan.this, NewstickerActivity.class);
                    startActivity(startNewsfeed);
                }
            });

        fridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewsfeed = new Intent (Stundenplan.this, NewstickerActivity.class);
                startActivity(startNewsfeed);
            }
        });
        */
        }

  }