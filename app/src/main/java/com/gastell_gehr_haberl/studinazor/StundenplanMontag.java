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

public class StundenplanMontag extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.stundenplan_montag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputFirstClass = (EditText) findViewById(R.id.input_first_class_monday);
        inputSecondClass = (EditText) findViewById(R.id.input_second_class_monday);
        inputThirdClass = (EditText) findViewById(R.id.input_third_class_monday);
        inputFourthClass = (EditText) findViewById(R.id.input_fourth_class_monday);
        inputFifthClass = (EditText) findViewById(R.id.input_fifth_class_monday);
        inputSixthClass = (EditText) findViewById(R.id.input_sixth_class_monday);
        inputFirstRoom = (EditText) findViewById(R.id.input_first_room_monday);
        inputSecondRoom = (EditText) findViewById(R.id.input_second_room_monday);
        inputThirdRoom = (EditText) findViewById(R.id.input_third_room_monday);
        inputFourthRoom = (EditText) findViewById(R.id.input_fourth_room_monday);
        inputFifthRoom = (EditText) findViewById(R.id.input_fifth_room_monday);
        inputSixthRoom = (EditText) findViewById(R.id.input_sixth_room_monday);
        saveInput = (Button) findViewById(R.id.input_button_monday);
        saveInput.setOnClickListener(this);
        savedPreferences();
    }

    private void savedPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String firstClassInput = pref.getString("firstClassMonday", "");
        String secondClassInput = pref.getString("secondClassMonday", "");
        String thirdClassInput = pref.getString("thirdClassMonday", "");
        String fourthClassInput = pref.getString("fourthClassMonday", "");
        String fifthClassInput = pref.getString("fifthClassMonday", "");
        String sixthClassInput = pref.getString("sixthClassMonday", "");
        String firstRoomInput = pref.getString("firstRoomMonday", "");
        String secondRoomInput = pref.getString("secondRoomMonday", "");
        String thirdRoomInput = pref.getString("thirdRoomMonday", "");
        String fourthRoomInput = pref.getString("fourthRoomMonday", "");
        String fifthRoomInput = pref.getString("fifthRoomMonday", "");
        String sixthRoomInput = pref.getString("sixthRoomMonday", "");
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
        savePreferences("firstClassMonday", inputFirstClass.getText().toString());
        savePreferences("secondClassMonday", inputSecondClass.getText().toString());
        savePreferences("thirdClassMonday", inputThirdClass.getText().toString());
        savePreferences("fourthClassMonday", inputFourthClass.getText().toString());
        savePreferences("fifthClassMonday", inputFifthClass.getText().toString());
        savePreferences("sixthClassMonday", inputSixthClass.getText().toString());
        savePreferences("firstRoomMonday", inputFirstRoom.getText().toString());
        savePreferences("secondRoomMonday", inputSecondRoom.getText().toString());
        savePreferences("thirdRoomMonday", inputThirdRoom.getText().toString());
        savePreferences("fourthRoomMonday", inputFourthRoom.getText().toString());
        savePreferences("fifthRoomMonday", inputFifthRoom.getText().toString());
        savePreferences("sixthRoomMonday", inputSecondRoom.getText().toString());

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

