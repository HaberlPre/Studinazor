package com.gastell_gehr_haberl.studinazor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juliane on 05.09.2017.
 */

public class ToDoListeAdapter extends ArrayAdapter<ToDoItem> {

    private ArrayList<ToDoItem> todoList;
    private Context context;
    //private TextView item;
    //private TextView date;

    public ToDoListeAdapter(Context context, ArrayList<ToDoItem> listItems) {
        super(context, R.layout.todolist_itemlist, listItems);
        this.context = context;
        this.todoList = listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.todolist_itemlist, null);

        }
        ToDoItem task = todoList.get(position);

        if (task != null) {
            TextView textTask = (TextView) v.findViewById(R.id.text_task);
            TextView textDate = (TextView) v.findViewById(R.id.text_date);

            textTask.setText(task.getName());
            textDate.setText(task.getFormattedDate()+task.getFormattedTime());
        }

        return v;
    }
}