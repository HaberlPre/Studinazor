package com.gastell_gehr_haberl.studinazor;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

    private ArrayList<NewstickerItem> items;
    private boolean showLinks = false;
    private NewstickerItemAdapterNoLinks adapterNoLinks;
    private NewstickerItemAdapterWithLinks adapterWithLinks;
    private String ADDRESS ="";
    private final static String ADDRESS_GOOGLE = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=6b20d2bac2034606b24756716c03b72e";
    private final static String ADDRESS_NYT = "https://newsapi.org/v1/articles?source=the-new-york-times&sortBy=top&apiKey=6b20d2bac2034606b24756716c03b72e";
    private final static String ADDRESS_BBC = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=6b20d2bac2034606b24756716c03b72e";
    private final static String ADDRESS_focus = "https://newsapi.org/v1/articles?source=focus&sortBy=top&apiKey=6b20d2bac2034606b24756716c03b72e";
    private final static String ADDRESS_zeit = "https://newsapi.org/v1/articles?source=die-zeit&sortBy=latest&apiKey=6b20d2bac2034606b24756716c03b72e";
    private final static String ADDRESS_stern = "https://newsapi.org/v1/articles?source=spiegel-online&sortBy=top&apiKey=6b20d2bac2034606b24756716c03b72e";

    TextView poweredBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsticker);
        ADDRESS = ADDRESS_GOOGLE;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enableStartScreenButton();
        prepareListView();
        setupPoweredBy();
        new NewstickerDownloadTask(this, items).execute(ADDRESS);
    }

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

    private void openUrl(int position) {
        NewstickerItem item = items.get(position);
        String s = item.getUrl();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        startActivity(browserIntent);
    }

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
            case R.id.newsticker_newspaper:
                changeNewspaper(item);
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

    public void changeNewspaper(MenuItem item) {
        LayoutInflater inflater = LayoutInflater.from(NewstickerActivity.this);
        final View dialogView = inflater.inflate(R.layout.newsticker_context, null);
        final AlertDialog.Builder alertDialog =  new AlertDialog.Builder(NewstickerActivity.this);
        alertDialog.setView(dialogView);
        alertDialog.setCancelable(true);
        final AlertDialog alert = alertDialog.create();
        alert.show();
        final TextView google = (TextView) dialogView.findViewById(R.id.newsticker_google);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDRESS = ADDRESS_GOOGLE;
                prepareListView();
                new NewstickerDownloadTask(NewstickerActivity.this, items).execute(ADDRESS);
                alert.cancel();
            }
        });

        final TextView nyt = (TextView) dialogView.findViewById(R.id.newsticker_NYT);
        nyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDRESS = ADDRESS_NYT;
                prepareListView();
                new NewstickerDownloadTask(NewstickerActivity.this, items).execute(ADDRESS);
                alert.cancel();
            }
        });

        final TextView bbc = (TextView) dialogView.findViewById(R.id.newsticker_BBC);
        bbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDRESS = ADDRESS_BBC;
                prepareListView();
                new NewstickerDownloadTask(NewstickerActivity.this, items).execute(ADDRESS);
                alert.cancel();
            }
        });

        final TextView zeit = (TextView) dialogView.findViewById(R.id.newsticker_zeit);
        zeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDRESS = ADDRESS_zeit;
                prepareListView();
                new NewstickerDownloadTask(NewstickerActivity.this, items).execute(ADDRESS);
                alert.cancel();
            }
        });

        final TextView stern = (TextView) dialogView.findViewById(R.id.newsticker_stern);
        stern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDRESS = ADDRESS_stern;
                prepareListView();
                new NewstickerDownloadTask(NewstickerActivity.this, items).execute(ADDRESS);
                alert.cancel();
            }
        });

        final TextView focus = (TextView) dialogView.findViewById(R.id.newsticker_focus);
        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDRESS = ADDRESS_focus;
                prepareListView();
                new NewstickerDownloadTask(NewstickerActivity.this, items).execute(ADDRESS);
                alert.cancel();
            }
        });
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

