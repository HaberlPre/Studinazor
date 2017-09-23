package com.gastell_gehr_haberl.studinazor;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Juliane on 05.09.2017.
 */
public class ToDoItem implements Comparable<ToDoItem> {

    private String name;
    private GregorianCalendar date;
    private String dateString;
    private GregorianCalendar time;
    private String timeString;


    public ToDoItem(String name, int day, int month, int year, int seconds, int minute, int hourOfDay) {
        this.name = name;
        this.date = new GregorianCalendar(year, month, day);
        this.time = new GregorianCalendar(hourOfDay, minute, seconds);
    }

    public ToDoItem(String name, int year, int month, int day, String time) {
        this.name = name;
        date = new GregorianCalendar(year, month, day);
        timeString = time;
    }

    public ToDoItem(String name, String date, String time) {
        this.name = name;
        dateString = date;
        timeString = time;
    }


    public String getName() {
        return name;
    }

    public String getDateString() {
        return dateString;
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
        return "Name: " + getName() + ", Date: " + getFormattedDate() + ", Time: ";
    }

}

