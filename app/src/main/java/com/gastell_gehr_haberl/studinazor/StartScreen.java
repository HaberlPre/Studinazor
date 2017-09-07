package com.gastell_gehr_haberl.studinazor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartScreen extends AppCompatActivity {

    Button toDoButton;
    Button einkaufsButton;
    Button stundenplanButton;
    TextView newsticker;
    TextView poweredBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        setupUI();
        setListeners();
    }

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
    }

    private void setupUI() {
        toDoButton = (Button) findViewById(R.id.StartToToDoButton);
        einkaufsButton = (Button) findViewById(R.id.StartToEinkaufButton);
        stundenplanButton = (Button) findViewById(R.id.StartToStundenplanButton);
        setupNewsticker();
        setupPoweredBy();
    }

    private void setupNewsticker() {
    }

    private void setupPoweredBy() {
        poweredBy = (TextView) findViewById(R.id.poweredBy_id);
        poweredBy.setClickable(true);
        poweredBy.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='https://newsapi.org/'> Powered by News API </a>";
        poweredBy.setText(Html.fromHtml(text));
    }


}
