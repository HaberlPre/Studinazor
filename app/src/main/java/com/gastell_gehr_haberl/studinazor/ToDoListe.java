package com.gastell_gehr_haberl.studinazor;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by lucas on 09.08.2017.
 */

public class ToDoListe extends AppCompatActivity {

    private Button addButton;
    private ArrayList<ToDoItem> items;
    private ToDoListeAdapter todoItemsAdapter;
    private ToDoListeDatenbank todoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //TODO bundle von startendem intent
        //TODO holen, prüfen und dann verarbeiten bzw wenn leer kp
        setContentView(R.layout.activity_todo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initTaskList();
        initUI();
        initDataBase();
        updateList();
    }

    private void initDataBase() {
        todoDB = new ToDoListeDatenbank(this);
        todoDB.open();
    }

    private void updateList() {
        items.clear();
        items.addAll(todoDB.getAllToDoItems());
        todoItemsAdapter.notifyDataSetChanged();
    }

    private void initTaskList() {
        items = new ArrayList<ToDoItem>();
        initListAdapter();
    }

    private void initUI() {
        initTaskButton();
        initListView();
        initDateField();
        initTimeField();
    }


    private void initTaskButton() {
        addButton = (Button) findViewById(R.id.todo_text_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }
        });
    }

    private void buttonClicked() {
        EditText edit = (EditText) findViewById(R.id.todo_text_task);
        EditText dateEdit = (EditText) findViewById(R.id.notification_date);
        EditText timeEdit = (EditText) findViewById(R.id.notification_time);
        String task = edit.getText().toString();
        String date = dateEdit.getText().toString();
        String time = timeEdit.getText().toString();
        if (!task.equals("") && !date.equals("") && !time.equals("")) {
            edit.setText("");
            dateEdit.setText("");
            timeEdit.setText("");
            addNewTask(task, date, time);
        }
    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        registerForContextMenu(list);
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        todoItemsAdapter = new ToDoListeAdapter(this, items);
        list.setAdapter(todoItemsAdapter);
    }

    private void addNewTask(String task, String date, String time) {
        Date dueDate = getDateFromString(date);
        Date dueTime = getTimeFromString(time);
        GregorianCalendar chosenDate = new GregorianCalendar();
        GregorianCalendar chosenTime = new GregorianCalendar();
        chosenDate.setTime(dueDate);
        chosenTime.setTime(dueTime);

        ToDoItem newTask = new ToDoItem(task, chosenDate.get(Calendar.DAY_OF_MONTH),
                chosenDate.get(Calendar.MONTH), chosenDate.get(Calendar.YEAR), chosenTime.get(Calendar.HOUR), chosenTime.get(Calendar.MINUTE));

        todoDB.insertItem(newTask);
        updateList();
    }

    private void initDateField() {
        EditText dateEdit = (EditText) findViewById(R.id.notification_date);
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDatePickerDialog();
            }
        });

    }

    private void initTimeField() {
        EditText timeEdit = (EditText) findViewById(R.id.notification_time);
        timeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showTimePickerDialog();
            }
        });

    }

    private void removeTaskAtPosition(int position) {
        if (items.get(position) != null) {
            todoDB.removeToDoItem(items.get(position));
            updateList();
        }
    }

    public void showDatePickerDialog() {
        DialogFragment chosenDate = new ToDoListeChosenDate();
        chosenDate.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog() {
        DialogFragment chosenTime = new ToDoListeChosenTime();
        chosenTime.show(getFragmentManager(), "timePicker");
    }

    private Date getDateFromString(String dateString) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    private Date getTimeFromString(String timeString) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        try {
            return df.parse(timeString);
        } catch(ParseException e) {
            return new Date();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_todoliste, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_todo_sort:
                sortList();
                return true;
            case R.id.menu_delete_all:
                deleteAll();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void sortList() {
        Collections.sort(items);
        todoItemsAdapter.notifyDataSetChanged();
    }

    private void deleteAll() {
        for(int i = items.size() - 1; i >= 0; i--) {
            removeTaskAtPosition(i);
        }

    }

    public void onDestroy() {
        super.onDestroy();
        todoDB.close();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_todoliste_context, menu);
    }

    public void createEdit(final ToDoItem item) {
        LayoutInflater inflater = LayoutInflater.from(ToDoListe.this);
        View dialogView = inflater.inflate(R.layout.todoliste_context, null);
        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(ToDoListe.this);
        alertDialog.setView(dialogView);
        final EditText edit = (EditText) dialogView.findViewById(R.id.edit_dialog_input);
        edit.setText(item.getName());
        final TextView message = (TextView) dialogView.findViewById(R.id.edit_old_task);

        alertDialog.setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                item.setName(edit.getText().toString());
                todoItemsAdapter.notifyDataSetChanged();
            }
        }) .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alert = alertDialog.create();
        alert.show();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = (int) adapterInfo.id;

        switch(item.getItemId()) {
            case R.id.menu_delete_task:
                removeTaskAtPosition(position);
                break;
            case R.id.menu_edit_task:
                createEdit(items.get(position));
                break;
        }
        return super.onContextItemSelected(item);
    }
}