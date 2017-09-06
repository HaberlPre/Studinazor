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

    public ToDoItem(String name, int day, int month, int year) {
        this.name = name;
        this.date = new GregorianCalendar(year, month, day);
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

    public Date getDueDate() {
        return date.getTime();
    }

    @Override
    public int compareTo(ToDoItem newItem) {
        return getDueDate().compareTo(newItem.getDueDate());
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Date: " + getFormattedDate();
    }
}

