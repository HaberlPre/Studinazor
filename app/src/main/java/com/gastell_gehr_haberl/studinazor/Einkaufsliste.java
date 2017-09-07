package com.gastell_gehr_haberl.studinazor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lucas on 09.08.2017.
 */

public class Einkaufsliste extends AppCompatActivity {

    private ArrayList<ShopItem> shopItems;
    private EinkaufslisteAdapter shopItems_adapter;
    private EinkaufslisteDatabase shopDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkauf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initTaskList();
        initDatabase();
        initUI();
        refreshArrayList();
    }


    private void initDatabase() {
        shopDB = new EinkaufslisteDatabase(this);
        shopDB.open();
    }

    private void refreshArrayList(){
        ArrayList tempList = shopDB.getAllToDoItems();
        shopItems.clear();
        shopItems.addAll(tempList);
        shopItems_adapter.notifyDataSetChanged();
    }

    private void initTaskList() {
        shopItems = new ArrayList<ShopItem>();
        initListAdapter();
    }

    private void initUI() {
        initTaskButton();
        initListView();
    }

    private void initTaskButton() {
        Button addTaskButton = (Button) findViewById(R.id.add_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInputToList();
            }
        });
    }

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

    private void initListView() {
        final ListView list = (ListView) findViewById(R.id.shop_list);
        registerForContextMenu(list);
        /*list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                //registerForContextMenu(list);
                //removeTaskAtPosition(position);
                return true;
            }
        });*/
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info){
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_einkaufsliste_context, menu);
    }

    public void createEdit(final ShopItem item) {
        LayoutInflater inflater = LayoutInflater.from(Einkaufsliste.this);
        View dialogView = inflater.inflate(R.layout.todoliste_context, null);
        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(Einkaufsliste.this);
        alertDialog.setView(dialogView);
        final EditText edit = (EditText) dialogView.findViewById(R.id.edit_dialog_input);
        edit.setText(item.getName());
        final TextView message = (TextView) dialogView.findViewById(R.id.edit_old_task);

        alertDialog.setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                item.setName(edit.getText().toString());
                shopItems_adapter.notifyDataSetChanged();
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = (int) info.id;

        switch (item.getItemId()) {
            case R.id.item_delete:
                removeTaskAtPosition(item.getItemId());
                break;
            case R.id.item_change:
                changeItem();
                createEdit(shopItems.get(position));
                break;
        }
                return super.onContextItemSelected(item);
    }

    private void changeItem(){

    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.shop_list);
        shopItems_adapter = new EinkaufslisteAdapter(this, shopItems);
        list.setAdapter(shopItems_adapter);
    }

    private void addNewTask(String amount, String unit, String task) {

        ShopItem newTask = new ShopItem(amount, unit, task);

        shopDB.insertItem(newTask);
        refreshArrayList();
    }


    private void removeTaskAtPosition(int position) {
        if (shopItems.get(position) != null) {
            shopDB.removeToDoItem(shopItems.get(position));
            refreshArrayList();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_einkaufsliste, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_list:
                deleteList();
                return true;
            case R.id.action_change_list:
                changeItem();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteList(){
        shopItems.clear();
        shopItems_adapter.notifyDataSetChanged();
        shopDB.deleteList();
        refreshArrayList();
    }

    private void sortList() {
        Collections.sort(shopItems);
        shopItems_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopDB.close();
    }
}
