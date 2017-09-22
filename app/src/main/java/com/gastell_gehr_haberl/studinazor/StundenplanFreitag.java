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

public class StundenplanFreitag extends AppCompatActivity implements View.OnClickListener{

    //Variabeln
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
        setContentView(R.layout.stundenplan_freitag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputFirstClass = (EditText) findViewById(R.id.input_first_class_friday);
        inputSecondClass = (EditText) findViewById(R.id.input_second_class_friday);
        inputThirdClass = (EditText) findViewById(R.id.input_third_class_friday);
        inputFourthClass = (EditText) findViewById(R.id.input_fourth_class_friday);
        inputFifthClass = (EditText) findViewById(R.id.input_fifth_class_friday);
        inputSixthClass = (EditText) findViewById(R.id.input_sixth_class_friday);
        inputFirstRoom = (EditText) findViewById(R.id.input_first_room_friday);
        inputSecondRoom = (EditText) findViewById(R.id.input_second_room_friday);
        inputThirdRoom = (EditText) findViewById(R.id.input_third_room_friday);
        inputFourthRoom = (EditText) findViewById(R.id.input_fourth_room_friday);
        inputFifthRoom = (EditText) findViewById(R.id.input_fifth_room_friday);
        inputSixthRoom = (EditText) findViewById(R.id.input_sixth_room_friday);
        saveInput = (Button) findViewById(R.id.input_button_friday);
        saveInput.setOnClickListener(this);
        enableDaysButton();
        savedPreferences();
    }

    /**
     * Alle gespeicherten Einträge
     */
    private void savedPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String firstClassInput = pref.getString("firstClassFriday", "");
        String secondClassInput = pref.getString("secondClassFriday", "");
        String thirdClassInput = pref.getString("thirdClassFriday", "");
        String fourthClassInput = pref.getString("fourthClassFriday", "");
        String fifthClassInput = pref.getString("fifthClassFriday", "");
        String sixthClassInput = pref.getString("sixthClassFriday", "");
        String firstRoomInput = pref.getString("firstRoomFriday", "");
        String secondRoomInput = pref.getString("secondRoomFriday", "");
        String thirdRoomInput = pref.getString("thirdRoomFriday", "");
        String fourthRoomInput = pref.getString("fourthRoomFriday", "");
        String fifthRoomInput = pref.getString("fifthRoomFriday", "");
        String sixthRoomInput = pref.getString("sixthRoomFriday", "");
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

    /**
     * Speichert die Einträge
     * @param key
     * @param value
     */
    private void savePreferences(String key, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(key, value);
        edit.commit();
    }

    @Override
    public void onClick(View v) {
        savePreferences("firstClassFriday", inputFirstClass.getText().toString());
        savePreferences("secondClassFriday", inputSecondClass.getText().toString());
        savePreferences("thirdClassFriday", inputThirdClass.getText().toString());
        savePreferences("fourthClassFriday", inputFourthClass.getText().toString());
        savePreferences("fifthClassFriday", inputFifthClass.getText().toString());
        savePreferences("sixthClassFriday", inputSixthClass.getText().toString());
        savePreferences("firstRoomFriday", inputFirstRoom.getText().toString());
        savePreferences("secondRoomFriday", inputSecondRoom.getText().toString());
        savePreferences("thirdRoomFriday", inputThirdRoom.getText().toString());
        savePreferences("fourthRoomFriday", inputFourthRoom.getText().toString());
        savePreferences("fifthRoomFriday", inputFifthRoom.getText().toString());
        savePreferences("sixthRoomFriday", inputSecondRoom.getText().toString());

    }

    /**
     * Menü in der Actionbar
     * @param item
     * @return Welche Aktion ausgelöst werden soll: Zurück-Pfeil
     *
     */
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

    /**
     * Macht im Landscape-Modus die Benutzung der StartScreen-Buttons möglich
     */
    private void enableDaysButton() {
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Button startMonday = (Button) findViewById(R.id.button_monday);
            Button startTuesday = (Button) findViewById(R.id.button_tuesday);
            Button startWednesday = (Button) findViewById(R.id.button_wednesday);
            Button startThursday = (Button) findViewById(R.id.button_thursday);
            startMonday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startMondayIntent = new Intent(StundenplanFreitag.this, StundenplanMontag.class);
                    startActivity(startMondayIntent);
                }
            });
            startTuesday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startTuesdayIntent = new Intent(StundenplanFreitag.this, StundenplanDienstag.class);
                startActivity(startTuesdayIntent);
                }
            });
            startWednesday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startWednesdayIntent = new Intent(StundenplanFreitag.this, StundenplanMittwoch.class);
                    startActivity(startWednesdayIntent);
                }
            });
            startThursday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startThursdayIntent = new Intent(StundenplanFreitag.this, StundenplanDonnerstag.class);
                    startActivity(startThursdayIntent);
                }
            });
        }
    }

}

