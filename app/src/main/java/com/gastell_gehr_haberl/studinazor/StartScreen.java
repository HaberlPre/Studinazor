package com.gastell_gehr_haberl.studinazor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartScreen extends AppCompatActivity {

    //Variabeln
    Button toDoButton;
    Button einkaufsButton;
    Button stundenplanButton;
    Button newsfeedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        setupUI();
        setListeners();
    }

    /**
     * Legt fest wohin die Buttons beim Anklicken führen
     */
    private void setListeners() {
        toDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startToDo = new Intent(StartScreen.this, ToDoListe.class); //ToDoListeDBActivity für mehr listen
                startActivity(startToDo);
            }
        });

        einkaufsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startEinkauf = new Intent(StartScreen.this, Einkaufsliste.class);
                startActivity(startEinkauf);
            }
        });

        stundenplanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startStundenplan = new Intent (StartScreen.this, Stundenplan.class);
                startActivity(startStundenplan);
            }
        });

        newsfeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewsfeed = new Intent (StartScreen.this, NewstickerActivity.class);
                startActivity(startNewsfeed);
            }
        });
    }

    /**
     * Initiiert das User Interface (Benutzeroberfläche)
     */
    private void setupUI() {
        toDoButton = (Button) findViewById(R.id.StartToToDoButton);
        einkaufsButton = (Button) findViewById(R.id.StartToEinkaufButton);
        stundenplanButton = (Button) findViewById(R.id.StartToStundenplanButton);
        newsfeedButton = (Button) findViewById(R.id.StartToNewsfeedButton);
    }
}
