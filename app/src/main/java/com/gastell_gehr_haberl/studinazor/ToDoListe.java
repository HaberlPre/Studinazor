package com.gastell_gehr_haberl.studinazor;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
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
        super.onCreate(savedInstanceState);
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
        EditText dateEdit = (EditText) findViewById(R.id.todo_text_date);
        String task = edit.getText().toString();
        String date = dateEdit.getText().toString();

        if (!task.equals("") && !date.equals("")) {
            edit.setText("");
            dateEdit.setText("");
            addNewTask(task, date);
        }
    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                removeTaskAtPosition(position);
                return true;
            }
        });
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        todoItemsAdapter = new ToDoListeAdapter(this, items);
        list.setAdapter(todoItemsAdapter);
    }

    private void addNewTask(String task, String date) {
        Date dueDate = getDateFromString(date);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dueDate);

        ToDoItem newTask = new ToDoItem(task, cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
        todoDB.insertItem(newTask);
        updateList();
    }

    private void initDateField() {
        EditText dateEdit = (EditText) findViewById(R.id.todo_text_date);
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDatePickerDialog();
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

    private Date getDateFromString(String dateString) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
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
            case R.id.todo_sort:
                sortList();
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

    public void onDestroy() {
        super.onDestroy();
        todoDB.close();

    }


}