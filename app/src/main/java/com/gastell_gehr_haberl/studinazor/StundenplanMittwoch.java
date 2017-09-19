package com.gastell_gehr_haberl.studinazor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Juliane on 14.09.2017.
 */

public class StundenplanMittwoch extends AppCompatActivity implements View.OnClickListener{

    private EditText inputFirstClass;
    private EditText inputSecondClass;
    private EditText inputThirdClass;
    private EditText inputFourthClass;
    private EditText inputFifthClass;
    private EditText inputSixthClass;
    private EditText inputFirstRoom;
    private EditText inputSecondRoom;
    private EditText inputThirdRoom;
    private EditText inputFourthRoom;
    private EditText inputFifthRoom;
    private EditText inputSixthRoom;
    private Button saveInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stundenplan_mittwoch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputFirstClass = (EditText) findViewById(R.id.input_first_class_wednesday);
        inputSecondClass = (EditText) findViewById(R.id.input_second_class_wednesday);
        inputThirdClass = (EditText) findViewById(R.id.input_third_class_wednesday);
        inputFourthClass = (EditText) findViewById(R.id.input_fourth_class_wednesday);
        inputFifthClass = (EditText) findViewById(R.id.input_fifth_class_wednesday);
        inputSixthClass = (EditText) findViewById(R.id.input_sixth_class_wednesday);
        inputFirstRoom = (EditText) findViewById(R.id.input_first_room_wednesday);
        inputSecondRoom = (EditText) findViewById(R.id.input_second_room_wednesday);
        inputThirdRoom = (EditText) findViewById(R.id.input_third_room_wednesday);
        inputFourthRoom = (EditText) findViewById(R.id.input_fourth_room_wednesday);
        inputFifthRoom = (EditText) findViewById(R.id.input_fifth_room_wednesday);
        inputSixthRoom = (EditText) findViewById(R.id.input_sixth_room_wednesday);
        saveInput = (Button) findViewById(R.id.input_button_wednesday);
        saveInput.setOnClickListener(this);
        enableDaysButton();
        savedPreferences();
    }

    private void savedPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String firstClassInput = pref.getString("firstClassWednesday", "");
        String secondClassInput = pref.getString("secondClassWednesday", "");
        String thirdClassInput = pref.getString("thirdClassWednesday", "");
        String fourthClassInput = pref.getString("fourthClassWednesday", "");
        String fifthClassInput = pref.getString("fifthClassWednesday", "");
        String sixthClassInput = pref.getString("sixthClassWednesday", "");
        String firstRoomInput = pref.getString("firstRoomWednesday", "");
        String secondRoomInput = pref.getString("secondRoomWednesday", "");
        String thirdRoomInput = pref.getString("thirdRoomWednesday", "");
        String fourthRoomInput = pref.getString("fourthRoomWednesday", "");
        String fifthRoomInput = pref.getString("fifthRoomWednesday", "");
        String sixthRoomInput = pref.getString("sixthRoomWednesday", "");
        inputFirstClass.setText(firstClassInput);
        inputSecondClass.setText(secondClassInput);
        inputThirdClass.setText(thirdClassInput);
        inputFourthClass.setText(fourthClassInput);
        inputFifthClass.setText(fifthClassInput);
        inputSixthClass.setText(sixthClassInput);
        inputFirstRoom.setText(firstRoomInput);
        inputSecondRoom.setText(secondRoomInput);
        inputThirdRoom.setText(thirdRoomInput);
        inputFourthRoom.setText(fourthRoomInput);
        inputFifthRoom.setText(fifthRoomInput);
        inputSixthRoom.setText(sixthRoomInput);
    }

    private void savePreferences(String key, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(key, value);
        edit.commit();
    }

    @Override
    public void onClick(View v) {
        savePreferences("firstClassWednesday", inputFirstClass.getText().toString());
        savePreferences("secondClassWednesday", inputSecondClass.getText().toString());
        savePreferences("thirdClassWednesday", inputThirdClass.getText().toString());
        savePreferences("fourthClassWednesday", inputFourthClass.getText().toString());
        savePreferences("fifthClassWednesday", inputFifthClass.getText().toString());
        savePreferences("sixthClassWednesday", inputSixthClass.getText().toString());
        savePreferences("firstRoomWednesday", inputFirstRoom.getText().toString());
        savePreferences("secondRoomWednesday", inputSecondRoom.getText().toString());
        savePreferences("thirdRoomWednesday", inputThirdRoom.getText().toString());
        savePreferences("fourthRoomWednesday", inputFourthRoom.getText().toString());
        savePreferences("fifthRoomWednesday", inputFifthRoom.getText().toString());
        savePreferences("sixthRoomWednesday", inputSecondRoom.getText().toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), Stundenplan.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void enableDaysButton() {
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Button startMonday = (Button) findViewById(R.id.button_monday);
            Button startTuesday = (Button) findViewById(R.id.button_tuesday);
            Button startThursday = (Button) findViewById(R.id.button_thursday);
            Button startFriday = (Button) findViewById(R.id.button_friday);
            startMonday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startMondayIntent = new Intent(StundenplanMittwoch.this, StundenplanMontag.class);
                    startActivity(startMondayIntent);
                }
            });
            startTuesday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startTuesdayIntent = new Intent(StundenplanMittwoch.this, StundenplanDienstag.class);
                    startActivity(startTuesdayIntent);
                }
            });
            startThursday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startThursdayIntent = new Intent(StundenplanMittwoch.this, StundenplanDonnerstag.class);
                    startActivity(startThursdayIntent);
                }
            });
            startFriday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startFridayIntent = new Intent(StundenplanMittwoch.this, StundenplanFreitag.class);
                    startActivity(startFridayIntent);
                }
            });
        }
    }

}

