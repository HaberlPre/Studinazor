package com.gastell_gehr_haberl.studinazor;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by lucas on 06.09.2017.
 */

public class ToDoListeDBActivity extends AppCompatActivity

    {

        private Button addButton;
        private ArrayList<ToDoListeDatenbank> lists;
        private ToDoListeDBDBAdapter todoListDBA;
        private ToDoListeDBDB todoDB;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolistedb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initTaskList();
        initUI();
        initDataBase();
        updateList();
    }

    private void initDataBase() {
        todoDB = new ToDoListeDBDB(this);
        todoDB.open();
    }

    private void updateList() {
        lists.clear();
        lists.addAll(todoDB.getAllToDoListDB());
        todoListDBA.notifyDataSetChanged();
    }

    private void initTaskList() {
        lists = new ArrayList<ToDoListeDatenbank>();
        initListAdapter();
    }

    private void initUI() {
        initTaskButton();
        initListView();
    }


    private void initTaskButton() {
        addButton = (Button) findViewById(R.id.tododb_edit_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }
        });
    }

    private void buttonClicked() {
        EditText edit = (EditText) findViewById(R.id.tododb_edit_input);
        String task = edit.getText().toString();

        if (!task.equals("")) {
            edit.setText("");
            addNewTask(task);
        }
    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.todo_listdb);
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
        ListView list = (ListView) findViewById(R.id.todo_listdb);
        todoListDBA = new ToDoListeDBDBAdapter(this, lists);
        list.setAdapter(todoListDBA);
    }

    private void addNewTask(String task) {

        //ToDoListeDatenbank newDB = new ToDoListeDatenbank(task); //TODO end//

        //todoDB.insertLists(newDB);
        updateList();
    }

    private void removeTaskAtPosition(int position) {
        if (lists.get(position) != null) {
            todoDB.removeToDoListDB(lists.get(position));
            updateList();
        }
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

        getMenuInflater().inflate(R.menu.menu_todolistedb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
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
        Collections.sort(lists);
        todoListDBA.notifyDataSetChanged();
    }

    public void onDestroy() {
        super.onDestroy();
        todoDB.close();

    }
}
