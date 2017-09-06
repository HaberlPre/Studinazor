package com.gastell_gehr_haberl.studinazor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucas on 06.09.2017.
 */

public class ToDoListeDBDBAdapter extends ArrayAdapter<ToDoListeDatenbank> {

    private ArrayList<ToDoListeDatenbank> listList;
    private Context context;
    //private TextView item;
    //private TextView date;

    public ToDoListeDBDBAdapter(Context context, ArrayList<ToDoListeDatenbank> listList) {
        super(context, R.layout.todolistdb_listlist, listList);
        this.context = context;
        this.listList = listList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.todolistdb_listlist, null);

        }
        ToDoListeDatenbank task = listList.get(position);

        if (task != null) {
            TextView textTask = (TextView) v.findViewById(R.id.taskdb_name);

            textTask.setText(task.getName());
        }

        return v;
    }

}
