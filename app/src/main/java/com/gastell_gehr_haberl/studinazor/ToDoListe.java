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

    //Variablen
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
    public int tHour, tMinute;


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

    /**
     * Initiiert die Datenbank
     */
    private void initDataBase() {
        todoDB = new ToDoListeDatenbank(this);
        todoDB.open();
    }

    /**
     * Updated die Liste
     */
    private void updateList() {
        ArrayList tempList = todoDB.getAllToDoItems();
        items.clear();
        items.addAll(tempList);
        todoItemsAdapter.notifyDataSetChanged();
    }

    /**
     * Initiiert den Inhalt der Liste und führt weiter zum Initiieren des Adapters
     */
    private void initTaskList() {
        items = new ArrayList<ToDoItem>();
        initListAdapter();
    }

    /**
     * Initiiert das User Interface (Benutzeroberfläche)
     */
    private void initUI() {
        initTaskButton();
        switchButton();
        initListView();
        initDateField();
        initTimeEditText();
    }

    /**
     * Initiiert das Feld in dem das Datum eingegeben/gewählt werden kann
     */
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


    /**
     * Initiiert den Hinzufügen-("+")-Button
     */
    private void initTaskButton() {
        addButton = (Button) findViewById(R.id.todo_text_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }
        });
    }

    /**
     * Methode die aufgerufen wird wenn der Button gedrückt wurde:
     * Liest alle Felder aus, fügt sie der Liste hinzu, frägt ab ob eine Erinnerung eingestellt wurde
     * und setzt zuletzt die Felder wieder auf leer.
     */
    private void buttonClicked() {

        todoText = (EditText) findViewById(R.id.todo_text_task);
        dateEdit = (EditText) findViewById(R.id.notification_date);
        timeEdit = (EditText) findViewById(R.id.notification_time);
        String task = todoText.getText().toString();
        String date = dateEdit.getText().toString();
        String time = timeEdit.getText().toString();

        if (!task.equals("") && !date.equals("") && !time.equals("")) {
            addNewTask(task, date, time, tHour, tMinute);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Notifaction) {
                scheduleNoti(getNoti(task), getTime());
            }
            todoText.setText("");
            dateEdit.setText("");
            timeEdit.setText("");
        }
    }

    /**
     * Verschiebt die Erinnerung bis auf 12 Uhr mittags
     * @param notification
     * @param time
     */
    private void scheduleNoti(Notification notification, long time) {
        Intent notiIntent = new Intent(this, NotiPublisher.class);
        notiIntent.putExtra(NotiPublisher.NOTI_ID, 1);
        notiIntent.putExtra(NotiPublisher.NOTI, notification);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+time, pIntent);
    }

    /**
     * Berechnet die Zeit wie lange die Notification warten muss, bis sie erscheint
     * @return die Zeit in Millisekunden
     */
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

    /**
     * Erstellt die Notification
     * @param content
     * @return die Notification
     */
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

    /**
     * Switch-Button der kontrolliert ob der Benutzer eine Erinnerung haben will oder nicht
     */
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

    /**
     * Intiiert das Listenlayout
     */
    private void initListView() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        registerForContextMenu(list);
    }

    /**
     * Initiiert den Adapter
     */
    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        todoItemsAdapter = new ToDoListeAdapter(this, items);
        list.setAdapter(todoItemsAdapter);
    }

    /**
     * Fügt eine neue Aufgabe hinzu
     * @param task
     * @param date
     * @param time
     */
    private void addNewTask(String task, String date, String time, int hour, int minute) {
        Date dueDate = getDateFromString(date);
        Calendar c = new GregorianCalendar();
        c.setTime(dueDate);

        ToDoItem newTask = new ToDoItem(task, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH), hour, minute, time);

        todoDB.insertItem(newTask);
        updateList();
    }

    /**
     * Initiiert das Feld, in welchem man das Datum auswählen in einem Kalender auswählen kann
     */
    private void initDateField() {
        EditText dateEdit = (EditText) findViewById(R.id.notification_date);
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDatePickerDialog();
            }
        });

    }

    /**
     * Löscht die Aufgabe an der gewählten Position
     * @param position
     */
    private void removeTaskAtPosition(int position) {
        if (items.get(position) != null) {
            todoDB.removeToDoItem(items.get(position));
            updateList();
        }
    }

    /**
     * Öffnet den Kalender zum auswählen des Datums
     */
    public void showDatePickerDialog() {
        DialogFragment chosenDate = new ToDoListeChosenDate();
        chosenDate.show(getFragmentManager(), "datePicker");
    }

    /**
     * Extrahiert das Datum von dem übergebenen String
     * @param dateString
     * @return das Datum im Datumformat xx.xx.xxxx
     */
    private Date getDateFromString(String dateString) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * Erstellt das Context Menü
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_todoliste_context, menu);
    }

    /**
     * Menü, welches beim Longklick geöffnet wird mit zwei Wahlmöglichkeiten
     * @param item
     * @return Zwei Auswahlmöglichkeiten: Item löschen oder bearbeiten
     */
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

    /**
     * Öffnet Fenster zum Bearbeiten des Eintrags
     * @param item
     */
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

    /**
     * Erstellt das Menü in der Actionbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_todoliste, menu);
        return true;
    }

    /**
     * Menü in der Actionbar
     * @param item
     * @return Welche Aktion ausgelöst werden soll: Entweder Zurück-Pfeil oder Löschen der Liste
     *
     */
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

    /**
     * Sortiert die Liste
     */
    private void sortList() {
        Collections.sort(items);
        todoItemsAdapter.notifyDataSetChanged();
    }

    /**
     * Löscht die ganze Liste
     */
    private void deleteAll() {
        todoDB.removeAllItems();
        todoItemsAdapter.notifyDataSetChanged();
        updateList();
    }

    /**
     * Macht im Landscape-Modus die Benutzung der StartScreen-Buttons möglich
     */
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
    protected void onDestroy() {
        super.onDestroy();
        todoDB.close();

    }
}