package com.gastell_gehr_haberl.studinazor;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Juliane on 05.09.2017.
 */
public class ToDoItem implements Comparable<ToDoItem> {

    private String name;
    private GregorianCalendar date;
    private String timeString;
    private int hour;
    private int minute;


    public ToDoItem (String name, int year, int month, int day, int hour, int minute, String time) {
        this.name = name;
        date = new GregorianCalendar(year, month, day);
        timeString = time;
        this.hour = hour;
        this.minute = minute;
        date.set(Calendar.HOUR_OF_DAY, hour);
        date.set(Calendar.MINUTE, minute);
    }

    public String getHour() { //int auch mgl
        return hour+"";
    }

    public String getMinute() {
        return minute+"";
    }

    public String getName() {
        return name;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getFormattedDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        return df.format(date.getTime());
    }

    public Date getDueDate() {
        return date.getTime();
    }

    @Override
    public int compareTo(ToDoItem newItem) {
        return getDueDate().compareTo(newItem.getDueDate());
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Date: " + getFormattedDate() + ", Time: "+ timeString;
    }

}