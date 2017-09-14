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
    private GregorianCalendar time;
    //private String time;
    private boolean hasReminder;


    ///*
    public ToDoItem(String name, int day, int month, int year, int hourOfDay, int minute) {
        this.name = name;
        this.date = new GregorianCalendar(year, month, day, hourOfDay, minute);
        //this.time = new GregorianCalendar(hourOfDay, minute);
        //this.time = ""+hour+":"+minute;
    }
    //*/
    /*
    public ToDoItem(String name, int day, int month, int year) {
        this.name = name;
        this.date = new GregorianCalendar(year, month, day);
    }
    */


    /*public ToDoItem(String name, int i, int i1, int i2) {
        this.name = name;
    }*/

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

    /*public String getFormattedTime() {

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        return df.format(time.getTime());
        //return time;
    }*/

    public Date getDueDate() {
        return date.getTime();
    }

    public Date getDueTime() {
        //return time.getTime();

        /*SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
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
    /*
    @Override
    public String toString() {
        return "Name: " + getName() + ", Date: " + getFormattedDate() + "Time: " + getFormattedTime();
    }
    */
    ///*
    @Override
    public String toString() {
        return "Name: " + getName() + ", Date: " + getFormattedDate();
    }
    //*/
}

