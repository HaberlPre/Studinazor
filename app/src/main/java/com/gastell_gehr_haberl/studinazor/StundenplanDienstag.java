package com.gastell_gehr_haberl.studinazor;

import android.content.SharedPreferences;
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

public class StundenplanDienstag extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.stundenplan_dienstag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputFirstClass = (EditText) findViewById(R.id.input_first_class_tuesday);
        inputSecondClass = (EditText) findViewById(R.id.input_second_class_tuesday);
        inputThirdClass = (EditText) findViewById(R.id.input_third_class_tuesday);
        inputFourthClass = (EditText) findViewById(R.id.input_fourth_class_tuesday);
        inputFifthClass = (EditText) findViewById(R.id.input_fifth_class_tuesday);
        inputSixthClass = (EditText) findViewById(R.id.input_sixth_class_tuesday);
        inputFirstRoom = (EditText) findViewById(R.id.input_first_room_tuesday);
        inputSecondRoom = (EditText) findViewById(R.id.input_second_room_tuesday);
        inputThirdRoom = (EditText) findViewById(R.id.input_third_room_tuesday);
        inputFourthRoom = (EditText) findViewById(R.id.input_fourth_room_tuesday);
        inputFifthRoom = (EditText) findViewById(R.id.input_fifth_room_tuesday);
        inputSixthRoom = (EditText) findViewById(R.id.input_sixth_room_tuesday);
        saveInput = (Button) findViewById(R.id.input_button_tuesday);
        saveInput.setOnClickListener(this);
        savedPreferences();
    }

    private void savedPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String firstClassInput = pref.getString("firstClassTuesday", "");
        String secondClassInput = pref.getString("secondClassTuesday", "");
        String thirdClassInput = pref.getString("thirdClassTuesday", "");
        String fourthClassInput = pref.getString("fourthClassTuesday", "");
        String fifthClassInput = pref.getString("fifthClassTuesday", "");
        String sixthClassInput = pref.getString("sixthClassTuesday", "");
        String firstRoomInput = pref.getString("firstRoomTuesday", "");
        String secondRoomInput = pref.getString("secondRoomTuesday", "");
        String thirdRoomInput = pref.getString("thirdRoomTuesday", "");
        String fourthRoomInput = pref.getString("fourthRoomTuesday", "");
        String fifthRoomInput = pref.getString("fifthRoomTuesday", "");
        String sixthRoomInput = pref.getString("sixthRoomTuesday", "");
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
        savePreferences("firstClassTuesday", inputFirstClass.getText().toString());
        savePreferences("secondClassTuesday", inputSecondClass.getText().toString());
        savePreferences("thirdClassTuesday", inputThirdClass.getText().toString());
        savePreferences("fourthClassTuesday", inputFourthClass.getText().toString());
        savePreferences("fifthClassTuesday", inputFifthClass.getText().toString());
        savePreferences("sixthClassTuesday", inputSixthClass.getText().toString());
        savePreferences("firstRoomTuesday", inputFirstRoom.getText().toString());
        savePreferences("secondRoomTuesday", inputSecondRoom.getText().toString());
        savePreferences("thirdRoomTuesday", inputThirdRoom.getText().toString());
        savePreferences("fourthRoomTuesday", inputFourthRoom.getText().toString());
        savePreferences("fifthRoomTuesday", inputFifthRoom.getText().toString());
        savePreferences("sixthRoomTuesday", inputSecondRoom.getText().toString());

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

