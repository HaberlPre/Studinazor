package com.gastell_gehr_haberl.studinazor;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TimePicker;

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
    private Switch switchButton;
    private EditText todoText;
    private EditText dateEdit;
    private EditText timeEdit;
    private ArrayList<ToDoItem> items;
    private ToDoListeAdapter todoItemsAdapter;
    private ToDoListeDatenbank todoDB;
    private EditText mTimeEditText;
    private boolean Notifaction = false;

    private int mHour, mMinute;
    private int tHour, tMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enableStartScreenButton();
        initTaskList();
        initDataBase();
        initUI();
        updateList();
    }

    private void initDataBase() {
        todoDB = new ToDoListeDatenbank(this);
        todoDB.open();
    }

    private void updateList() {
        ArrayList tempList = todoDB.getAllToDoItems();
        items.clear();
        items.addAll(tempList);
        todoItemsAdapter.notifyDataSetChanged();
    }

    private void initTaskList() {
        items = new ArrayList<ToDoItem>();
        initListAdapter();
    }

    private void initUI() {
        initTaskButton();
        switchButton();
        initListView();
        initDateField();
        initTimeEditText();
    }

    private void initTimeEditText() {
        mTimeEditText = (EditText) findViewById(R.id.notification_time);
        mTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(ToDoListe.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        tHour = hourOfDay;
                        mMinute = minute;
                        tMinute = minute;
                        String hourString;
                        String minuteString;
                        if (hourOfDay < 10) {
                            hourString = "0"+hourOfDay;
                        }
                        else {
                            hourString = hourOfDay+"";
                        }

                        if (minute < 10) {
                            minuteString = "0"+minute;
                        } else {
                            minuteString = minute+"";
                        }

                        mTimeEditText.setText(hourString+":"+minuteString);
                    }
                }, mHour, mMinute, true);
                mTimePicker.show();
            }
        });
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

        todoText = (EditText) findViewById(R.id.todo_text_task);
        dateEdit = (EditText) findViewById(R.id.notification_date);
        timeEdit = (EditText) findViewById(R.id.notification_time);
        String task = todoText.getText().toString();
        String date = dateEdit.getText().toString();
        String time = timeEdit.getText().toString();

        if (!task.equals("") && !date.equals("") && !time.equals("")) {
            addNewTask(task, date, time);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Notifaction) {
                scheduleNoti(getNoti(task), getTime());
            }
            todoText.setText("");
            dateEdit.setText("");
            timeEdit.setText("");
        }
    }

    private void scheduleNoti(Notification notification, long time) {
        Intent notiIntent = new Intent(this, NotiPublisher.class);
        notiIntent.putExtra(NotiPublisher.NOTI_ID, 1);
        notiIntent.putExtra(NotiPublisher.NOTI, notification);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+time, pIntent);
    }

    private long getTime() {
        dateEdit = (EditText) findViewById(R.id.notification_date);
        timeEdit = (EditText) findViewById(R.id.notification_time);
        String date = dateEdit.getText().toString();

        Date dueDate = getDateFromString(date);
        GregorianCalendar chosenDate = new GregorianCalendar();
        chosenDate.setTime(dueDate);
        chosenDate.set(Calendar.HOUR_OF_DAY, tHour);
        chosenDate.set(Calendar.MINUTE, tMinute);

        Date currDate = new Date();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(currDate);

        long notiTime = chosenDate.getTimeInMillis() - System.currentTimeMillis();
        return notiTime;
    }

    private Notification getNoti(String content) {
        Intent i = new Intent(this, ToDoListe.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pIntent = TaskStackBuilder.create(ToDoListe.this).addNextIntent(i).addParentStack(StartScreen.class).getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //todo startscreen in backtast
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Studinazor");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_today_black_48dp);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);
        return builder.build();
    }

    private void switchButton() {
        switchButton = (Switch) findViewById(R.id.notification_switch);
        switchButton.setChecked(false);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Notifaction = isChecked;
            }
        });
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

    private void addNewTask(String task, String date, String time) {
        ToDoItem newTask = new ToDoItem(task, date, time);
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


    private void enableStartScreenButton() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            Button startNewsfeed = (Button) findViewById(R.id.StartToNewsfeedButton);
            Button startEinkauf = (Button) findViewById(R.id.StartToEinkaufButton);
            Button startStundenplan = (Button) findViewById(R.id.StartToStundenplanButton);
            startNewsfeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newsfeedStart = new Intent(ToDoListe.this, NewstickerActivity.class);
                    startActivity(newsfeedStart);
                }
            });
            startEinkauf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent einkaufStart = new Intent(ToDoListe.this, Einkaufsliste.class);
                    startActivity(einkaufStart);
                }
            });
            startStundenplan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent stundenplanStart = new Intent(ToDoListe.this, Stundenplan.class);
                    startActivity(stundenplanStart);
                }
            });
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
                Intent intent = new Intent(getApplicationContext(), StartScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
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
        todoDB.removeAllItems();
        todoItemsAdapter.notifyDataSetChanged();
        updateList();
    }

    @Override
    protected void onDestroy() {
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

        alertDialog.setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                String newName = edit.getText().toString();

                todoDB.updateToDoItem(newName,item);
                todoItemsAdapter.notifyDataSetChanged();
                updateList();
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