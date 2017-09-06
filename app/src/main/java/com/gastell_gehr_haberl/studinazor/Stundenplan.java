package com.gastell_gehr_haberl.studinazor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.CollapsibleActionView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lucas on 09.08.2017.
 */

public class Stundenplan extends AppCompatActivity {

    private TextView textView; //Stundenplan "namenstag"
    private ArrayList<StundenplanItem> courses;
    private StundenplanAdapter courses_adapter;
    private StundenplanDatabase coursesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stundenplan);
        initCourseList();
        initDatabase();
        initUI();
    }

    private void initDatabase() {
        coursesDB = new StundenplanDatabase(this);
        coursesDB.open();
    }

    private void refreshArrayList() {
        ArrayList tempList = coursesDB.getAllStundenplanItems();
        courses.clear();
        courses.addAll(tempList);
        courses_adapter.notifyDataSetChanged();
    }

    private void initCourseList() {
        courses = new ArrayList<StundenplanItem>();
        initListAdapter();
    }

    private void initUI() { //TODO in verbing mit layout - buttons nur wip
        initCourseButton();
        initListView();
        initDataField();
    }

    private void initDataField() {
    }

    private void initCourseButton() {
    }

    private void addInputToList() {
    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.course_list); //TODO eig kein listview
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeTaskAtPosition(position);
                return true;
            }
        });
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.course_list);
        courses_adapter = new StundenplanAdapter(this, courses);
        list.setAdapter(courses_adapter);
    }

    private void addNewCourse (String course, String day, String start, String end) {
        StundenplanItem newIem = new StundenplanItem(course, day, start, end);

        coursesDB.insertStundenplanItem(newIem);
        refreshArrayList();
    }

    private void removeTaskAtPosition(int position) { //TODO listview?!?
        if (courses.get(position) != null) {
            coursesDB.removeStundenplanItem(courses.get(position));
            refreshArrayList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stundenplan, menu);
        //TODO stundenplan menu xml wip
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_list:
                deleteList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteList() { //TODO
    }

    private void sortList() {
        Collections.sort(courses);
        courses_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        coursesDB.close();
    }

}
