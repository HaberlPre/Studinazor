package com.gastell_gehr_haberl.studinazor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by lucas on 09.08.2017.
 */

public class Stundenplan extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stundenplan);

        setupUi();
        setupListeners();
    }

    private void setupListeners() {
    }

    private void setupUi() {
        textView = (TextView) findViewById(R.id.stundenplan_text_wip);
        textView.setText("fuck");
    }
}
