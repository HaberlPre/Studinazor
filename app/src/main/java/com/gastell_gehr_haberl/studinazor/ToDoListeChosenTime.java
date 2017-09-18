package com.gastell_gehr_haberl.studinazor;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Juliane on 10.09.2017.
 */

public class ToDoListeChosenTime extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {


    //final Calendar c = Calendar.getInstance();

    public ToDoListeChosenTime() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean is24Hour = true;

        return new TimePickerDialog(getActivity(), this, hour, minute, is24Hour);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) getActivity().findViewById(R.id.notification_time);
        ///*
        GregorianCalendar time = new GregorianCalendar(hourOfDay, minute, 0); //(hour, minute);
        DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG,
                Locale.GERMANY);
        String timeString = df.format(time.getTime());
        textView.setText(timeString);
        //*/
        /*
        String time_manual = hourOfDay+" : "+minute+" : "+0; //TODO?
        textView.setText(time_manual);
        */
    }
}
