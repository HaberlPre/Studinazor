package com.gastell_gehr_haberl.studinazor;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lucas on 09.08.2017.
 */

public class Einkaufsliste extends AppCompatActivity {

    /**
     * Orientiert an Übung 5 des Android-Kurses
     */

    //Variablen
    private ArrayList<ShopItem> shopItems;
    private EinkaufslisteAdapter shopItems_adapter;
    private EinkaufslisteDatabase shopDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkauf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enableStartScreenButton();
        initTaskList();
        initDatabase();
        initUI();
        refreshList();
    }


    /**
     * Initiiert die Datenbank
     */
    private void initDatabase() {
        shopDB = new EinkaufslisteDatabase(this);
        shopDB.open();
    }

    /**
     * Updated die Liste
     */
    private void refreshList(){
        ArrayList tempList = shopDB.getAllShopItems();
        shopItems.clear();
        shopItems.addAll(tempList);
        shopItems_adapter.notifyDataSetChanged();
    }

    /**
     * Initiiert den Inhalt der Liste und führt weiter zum Initiieren des Adapters
     */
    private void initTaskList() {
        shopItems = new ArrayList<ShopItem>();
        initListAdapter();
    }

    /**
     * Initiiert das User Interface (Benutzeroberfläche)
     */
    private void initUI() {
        initTaskButton();
        initListView();
    }

    /**
     * Initiiert den "Hinzufügen"-Button
     */
    private void initTaskButton() {
        Button addTaskButton = (Button) findViewById(R.id.add_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInputToList();
            }
        });
    }

    /**
     * Liest die Elemente aus und fügt einen neuen Artikel hinzu
     */
    private void addInputToList() {
        EditText num = (EditText) findViewById(R.id.item_edit_amount);
        String amount = num.getText().toString();

        EditText item = (EditText) findViewById(R.id.item_edit_input);
        String task = item.getText().toString();

        Spinner spinner = (Spinner) findViewById(R.id.item_spinner_unit);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Einkaufsliste.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        String unit = spinner.getSelectedItem().toString();

        if (!task.equals("") && !amount.equals("")) {
            item.setText("");
            num.setText("");
            addNewTask(amount, unit, task);
        }
    }

    /**
     * Initiiert das Listenlayout
     */
    private void initListView() {
        final ListView list = (ListView) findViewById(R.id.shop_list);
        registerForContextMenu(list);
    }


    /**
     * Initiiert den Adapter
     */
    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.shop_list);
        shopItems_adapter = new EinkaufslisteAdapter(this, shopItems);
        list.setAdapter(shopItems_adapter);
    }

    /**
     * Fügt einen neuen Artikel hinzu
     * @param amount
     * @param unit
     * @param task
     */
    private void addNewTask(String amount, String unit, String task) {
        ShopItem newTask = new ShopItem(amount, unit, task);
        shopDB.insertItem(newTask);
        refreshList();
    }

    /**
     * Löscht den Artikel an der gewählten Position
     * @param position
     */
    private void removeTaskAtPosition(int position) {
        if (shopItems.get(position) != null) {
            shopDB.removeShopItem(shopItems.get(position));
            refreshList();
        }
    }

    /**
     * Erstellt das Context Menü
     * @param menu
     * @param v
     * @param info
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info){
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_einkaufsliste_context, menu);
    }

    /**
     * Menü, welches beim Longklick geöffnet wird mit zwei Wahlmöglichkeiten
     * @param item
     * @return Zwei Auswahlmöglichkeiten: Item löschen oder bearbeiten
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = (int) info.id;

        switch (item.getItemId()) {
            case R.id.item_delete:
                removeTaskAtPosition(position);
                break;
            case R.id.item_change:
                createEdit(shopItems.get(position));
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Öffnet Fenster zum Bearbeiten des Eintrags
     * @param item
     */
    public void createEdit(final ShopItem item) {
        LayoutInflater inflater = LayoutInflater.from(Einkaufsliste.this);
        View dialogView = inflater.inflate(R.layout.einkaufsliste_context, null);
        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(Einkaufsliste.this);
        alertDialog.setView(dialogView);
        final EditText amountEdit = (EditText) dialogView.findViewById(R.id.edit_dialog_input_amount);
        amountEdit.setText(item.getAmount());
        final Spinner unitNew = (Spinner) dialogView.findViewById(R.id.edit_dialog_spinner);
        unitNew.setPrompt(item.getUnit());
        final EditText itemEdit = (EditText) dialogView.findViewById(R.id.edit_dialog_input_item);
        itemEdit.setText(item.getName());
        final TextView message = (TextView) dialogView.findViewById(R.id.edit_item_head);

        alertDialog.setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                String newAmount = amountEdit.getText().toString();
                String newUnit = unitNew.getSelectedItem().toString();
                String newName = itemEdit.getText().toString();

                shopDB.updateShopItem(newAmount,newUnit,newName,item);
                shopItems_adapter.notifyDataSetChanged();
                refreshList();

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
        getMenuInflater().inflate(R.menu.menu_einkaufsliste, menu);
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
            case R.id.action_delete_list:
                deleteList();
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
     * Löscht die ganze Liste
     */
    private void deleteList(){
        shopDB.removeAllItems();
        shopItems_adapter.notifyDataSetChanged();
        refreshList();
    }

    /**
     * Macht im Landscape-Modus die Benutzung der StartScreen-Buttons möglich
     */
    private void enableStartScreenButton() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            Button startNewsfeed = (Button) findViewById(R.id.StartToNewsfeedButton);
            Button startToDo = (Button) findViewById(R.id.StartToToDoButton);
            Button startStundenplan = (Button) findViewById(R.id.StartToStundenplanButton);
            startNewsfeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newsfeedStart = new Intent(Einkaufsliste.this, NewstickerActivity.class);
                    startActivity(newsfeedStart);
                }
            });
            startToDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDoStart = new Intent(Einkaufsliste.this, ToDoListe.class);
                    startActivity(toDoStart);
                }
            });
            startStundenplan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent stundenplanStart = new Intent(Einkaufsliste.this, Stundenplan.class);
                    startActivity(stundenplanStart);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopDB.close();
    }
}
