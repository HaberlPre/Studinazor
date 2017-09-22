package com.gastell_gehr_haberl.studinazor;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucas on 07.09.2017.
 */

public class NewstickerActivity extends AppCompatActivity implements DownloadListener {

    /**
     * Orientiert an Übung 7 des Android-Kurses
     */

    //Variabeln
    private ArrayList<NewstickerItem> items;
    private boolean showLinks = false;
    private NewstickerItemAdapterNoLinks adapterNoLinks;
    private NewstickerItemAdapterWithLinks adapterWithLinks;
    private final static String ADDRESS = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=6b20d2bac2034606b24756716c03b72e";
    TextView poweredBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsticker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enableStartScreenButton();
        prepareListView();
        setupPoweredBy();
        new NewstickerDownloadTask(this, items).execute(ADDRESS);
    }

    /**
     * Initiiert die Liste
     */
    private void prepareListView() {
        items = new ArrayList<NewstickerItem>();
        if (showLinks) {
            adapterWithLinks = new NewstickerItemAdapterWithLinks(NewstickerActivity.this, items);
            ListView list = (ListView) findViewById(R.id.news_list);
            list.setAdapter(adapterWithLinks);

            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    openUrl(position);
                    return true;
                }
            });
        } else {
            adapterNoLinks = new NewstickerItemAdapterNoLinks(NewstickerActivity.this, items);
            ListView list = (ListView) findViewById(R.id.news_list);
            list.setAdapter(adapterNoLinks);

            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    openUrl(position);
                    return true;
                }
            });
        }
    }

    /**
     * Öffnet den Link an der gewählten Stelle
     * @param position
     */
    private void openUrl(int position) {
        NewstickerItem item = items.get(position);
        String s = item.getUrl();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        startActivity(browserIntent);
    }

    /**
     * Stellt die Referenz dar
     */
    private void setupPoweredBy() {
        poweredBy = (TextView) findViewById(R.id.poweredBy_id);
        poweredBy.setClickable(true);
        poweredBy.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='https://newsapi.org/'> Powered by News API </a>";
        poweredBy.setText(Html.fromHtml(text));
    }

    @Override
    public void onDownloadFinished() {
        if (showLinks) {
            adapterWithLinks.notifyDataSetChanged();
        } else {
            adapterNoLinks.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newsticker, menu);
        return true;
    }

    /**
     * Menü in der Actionbar
     * @param item
     * @return Welche Aktion ausgelöst werden soll: Entweder Zurück-Pfeil oder Ändern von Link sichtbar/nicht sichtbar
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newsticker_toggle_links:
                toggleURLs();
                prepareListView();
                new NewstickerDownloadTask(this, items).execute(ADDRESS);
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
     * Entscheidet ob Links gezeigt werden oder nicht
     */
    private void toggleURLs() {
        showLinks = ! showLinks;
    }

    /**
     * Macht im Landscape-Modus die Benutzung der StartScreen-Buttons möglich
     */
    private void enableStartScreenButton() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            Button startEinkauf = (Button) findViewById(R.id.StartToEinkaufButton);
            Button startToDo = (Button) findViewById(R.id.StartToToDoButton);
            Button startStundenplan = (Button) findViewById(R.id.StartToStundenplanButton);
            startEinkauf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent einkaufStart = new Intent(NewstickerActivity.this, Einkaufsliste.class);
                    startActivity(einkaufStart);
                }
            });
            startToDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDoStart = new Intent(NewstickerActivity.this, ToDoListe.class);
                    startActivity(toDoStart);
                }
            });
            startStundenplan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent stundenplanStart = new Intent(NewstickerActivity.this, Stundenplan.class);
                    startActivity(stundenplanStart);
                }
            });
        }
    }

}

