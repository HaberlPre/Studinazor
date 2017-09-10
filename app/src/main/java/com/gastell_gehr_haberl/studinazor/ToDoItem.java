package com.gastell_gehr_haberl.studinazor;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Juliane on 05.09.2017.
 */
public class ToDoItem implements Comparable<ToDoItem> {

    private String name;
    private GregorianCalendar date;
    private GregorianCalendar time;
    private boolean hasReminder;



    public ToDoItem(String name, int day, int month, int year, int hour, int minute) {
        this.name = name;
        this.date = new GregorianCalendar(year, month, day);
        this.time = new GregorianCalendar();

    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    public String getFormattedDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        return df.format(date.getTime());
    }

    public String getFormattedTime() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        return df.format(time.getTime());
    }

    public Date getDueDate() {
        return date.getTime();
    }

    public Date getDueTime() {
        return date.getTime();
    }

    public boolean hasReminder() {
        return hasReminder;
    }

    public void setHasReminder(boolean hasReminder) {
        this.hasReminder = hasReminder;
    }

    @Override
    public int compareTo(ToDoItem newItem) {
        return getDueDate().compareTo(newItem.getDueDate());
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Date: " + getFormattedDate() + "Time: " + getFormattedTime();
    }
}

