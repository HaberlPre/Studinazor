package com.gastell_gehr_haberl.studinazor;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucas on 05.09.2017.
 */

public class StudenplanAdapter extends ArrayAdapter<StundenplanItem> {
    private ArrayList<StundenplanItem> courseList;
    private Context context;

    public StudenplanAdapter(Context context, ArrayList<StundenplanItem> listItems) {
        super(context, R.layout.stundenplanlist_itemlist, listItems);
        this.context = context;
        this.courseList = listItems;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {

        View v = contentView;

        if (v ==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.stundenplanlist_itemlist, null);
        }

        StundenplanItem course = courseList.get(position);

        if (course!=null) {
            TextView courseName = (TextView) v.findViewById(R.id.course_name);
            TextView courseDay = (TextView) v.findViewById(R.id.course_day);
            TextView courseStart = (TextView) v.findViewById(R.id.course_start);
            TextView courseEnd = (TextView) v.findViewById(R.id.course_end);

            courseName.setText(course.getCourse());
            courseDay.setText(course.getDay());
            courseStart.setText(course.getStart());
            courseEnd.setText(course.getEnd());
        }
        return v;
    }

}
