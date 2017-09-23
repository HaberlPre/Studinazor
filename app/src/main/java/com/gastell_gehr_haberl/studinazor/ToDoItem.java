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
    private boolean hasReminder;


    public ToDoItem(String name, int day, int month, int year, int seconds, int minute, int hourOfDay) {
        this.name = name;
        this.date = new GregorianCalendar(year, month, day);
        //this.date = new GregorianCalendar(year, month, day, hourOfDay, minute, seconds);
        this.time = new GregorianCalendar(hourOfDay, minute, seconds);
        //this.time = ""+hourOfDay+":"+minute;
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
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, //TODO long?
                Locale.GERMANY);
        return df.format(date.getTime());
    }

    public String getFormattedTime() {

        DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG,
                Locale.GERMANY);
        //return df.format(date.getTime());

        return df.format(time.getTime());
    }

    public Date getDueDate() {
        return date.getTime();
    }

    public Date getDueTime() {
        return time.getTime();
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

    @Override //crashed die app überraschenderweise nicht
    public String toString() {
        return "Name: " + getName() + ", Date: " + getFormattedDate() + ", Time: ";
    }

    /*
    @Override
    public String toString() {
        return "Name: " + getName() + ", Date: " + getFormattedDate();
    }
    */
}

