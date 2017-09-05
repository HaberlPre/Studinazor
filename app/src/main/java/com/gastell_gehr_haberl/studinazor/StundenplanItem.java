package com.gastell_gehr_haberl.studinazor;

import android.support.annotation.NonNull;

/**
 * Created by lucas on 05.09.2017.
 */

public class StundenplanItem implements Comparable<StundenplanItem> {

    private String course;
    private String day;
    private String start;
    private String end;

    public StundenplanItem(String course, String day, String start, String end) {
        this.course = course;
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public String getCourse() {
        return course;
    }

    public String getDay() {
        return day;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public int compareTo(@NonNull StundenplanItem o) { //nonnull?
        return 0;
    }

    @Override
    public String toString() {
        return "Course: " + getCourse() + ", Day: " + getDay()
                + ", Start: " + getStart() + " ,End: " +getEnd();
    }
}
