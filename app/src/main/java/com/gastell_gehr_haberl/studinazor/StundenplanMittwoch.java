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

public class StundenplanMittwoch extends AppCompatActivity implements View.OnClickListener{

    EditText inputFirstClass;
    EditText inputSecondClass;
    EditText inputThirdClass;
    EditText inputFourthClass;
    EditText inputFifthClass;
    EditText inputSixthClass;
    EditText inputFirstRoom;
    EditText inputSecondRoom;
    EditText inputThirdRoom;
    EditText inputFourthRoom;
    EditText inputFifthRoom;
    EditText inputSixthRoom;
    Button saveInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stundenplan_daily_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputFirstClass = (EditText) findViewById(R.id.input_first_class);
        inputSecondClass = (EditText) findViewById(R.id.input_second_class);
        inputThirdClass = (EditText) findViewById(R.id.input_third_class);
        inputFourthClass = (EditText) findViewById(R.id.input_fourth_class);
        inputFifthClass = (EditText) findViewById(R.id.input_fifth_class);
        inputSixthClass = (EditText) findViewById(R.id.input_sixth_class);
        inputFirstRoom = (EditText) findViewById(R.id.input_first_room);
        inputSecondRoom = (EditText) findViewById(R.id.input_second_room);
        inputThirdRoom = (EditText) findViewById(R.id.input_third_room);
        inputFourthRoom = (EditText) findViewById(R.id.input_fourth_room);
        inputFifthRoom = (EditText) findViewById(R.id.input_fifth_room);
        inputSixthRoom = (EditText) findViewById(R.id.input_sixth_room);
        saveInput = (Button) findViewById(R.id.input_button);
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

