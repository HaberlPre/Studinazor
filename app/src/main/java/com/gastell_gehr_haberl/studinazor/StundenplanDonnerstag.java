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

public class StundenplanDonnerstag extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.stundenplan_donnerstag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputFirstClass = (EditText) findViewById(R.id.input_first_class_thursday);
        inputSecondClass = (EditText) findViewById(R.id.input_second_class_thursday);
        inputThirdClass = (EditText) findViewById(R.id.input_third_class_thursday);
        inputFourthClass = (EditText) findViewById(R.id.input_fourth_class_thursday);
        inputFifthClass = (EditText) findViewById(R.id.input_fifth_class_thursday);
        inputSixthClass = (EditText) findViewById(R.id.input_sixth_class_thursday);
        inputFirstRoom = (EditText) findViewById(R.id.input_first_room_thursday);
        inputSecondRoom = (EditText) findViewById(R.id.input_second_room_thursday);
        inputThirdRoom = (EditText) findViewById(R.id.input_third_room_thursday);
        inputFourthRoom = (EditText) findViewById(R.id.input_fourth_room_thursday);
        inputFifthRoom = (EditText) findViewById(R.id.input_fifth_room_thursday);
        inputSixthRoom = (EditText) findViewById(R.id.input_sixth_room_thursday);
        saveInput = (Button) findViewById(R.id.input_button_thursday);
        saveInput.setOnClickListener(this);
        savedPreferences();
    }

    private void savedPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String firstClassInput = pref.getString("firstClass", "");
        String secondClassInput = pref.getString("secondClass", "");
        String thirdClassInput = pref.getString("thirdClass", "");
        String fourthClassInput = pref.getString("fourthClass", "");
        String fifthClassInput = pref.getString("fifthClass", "");
        String sixthClassInput = pref.getString("sixthClass", "");
        String firstRoomInput = pref.getString("firstRoom", "");
        String secondRoomInput = pref.getString("secondRoom", "");
        String thirdRoomInput = pref.getString("thirdRoom", "");
        String fourthRoomInput = pref.getString("fourthRoom", "");
        String fifthRoomInput = pref.getString("fifthRoom", "");
        String sixthRoomInput = pref.getString("sixthRoom", "");
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
        savePreferences("firstClass", inputFirstClass.getText().toString());
        savePreferences("secondClass", inputSecondClass.getText().toString());
        savePreferences("thirdClass", inputThirdClass.getText().toString());
        savePreferences("fourthClass", inputFourthClass.getText().toString());
        savePreferences("fifthClass", inputFifthClass.getText().toString());
        savePreferences("sixthClass", inputSixthClass.getText().toString());
        savePreferences("firstRoom", inputFirstRoom.getText().toString());
        savePreferences("secondRoom", inputSecondRoom.getText().toString());
        savePreferences("thirdRoom", inputThirdRoom.getText().toString());
        savePreferences("fourthRoom", inputFourthRoom.getText().toString());
        savePreferences("fifthRoom", inputFifthRoom.getText().toString());
        savePreferences("sixthRoom", inputSecondRoom.getText().toString());

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

