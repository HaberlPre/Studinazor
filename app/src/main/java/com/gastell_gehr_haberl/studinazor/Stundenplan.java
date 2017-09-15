package com.gastell_gehr_haberl.studinazor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                Intent startMonday = new Intent(Stundenplan.this, StundenplanMontag.class);
                startActivity(startMonday);
            }
        });

        tuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startTuesday = new Intent(Stundenplan.this, StundenplanDienstag.class);
                startActivity(startTuesday);
            }
        });

        wednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startWednesday = new Intent (Stundenplan.this, StundenplanMittwoch.class);
                startActivity(startWednesday);
                }
            });

        thursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent startThursday = new Intent (Stundenplan.this, StundenplanDonnerstag.class);
                    startActivity(startThursday);
                }
            });

        fridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startFriday = new Intent (Stundenplan.this, StundenplanFreitag.class);
                startActivity(startFriday);
            }
        });

        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

  }