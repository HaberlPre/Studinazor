package com.gastell_gehr_haberl.studinazor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
        //neu
        EditText num = (EditText) findViewById(R.id.item_edit_amount);
        String amount = num.getText().toString();

        EditText item = (EditText) findViewById(R.id.item_edit_input);
        String task = item.getText().toString();

        //neu
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
        //neu zum teil
        if (!task.equals("") && !amount.equals("")) {
            item.setText("");
            num.setText("");
            addNewTask(amount, unit, task);
        }
    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.shop_list);
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


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                sortList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

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
