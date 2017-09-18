package com.gastell_gehr_haberl.studinazor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucas on 07.09.2017.
 */

public class NewstickerActivity extends Activity implements DownloadListener {

    private ArrayList<NewstickerItem> items;
    private NewstickerItemAdapter adapter;
    private final static String ADDRESS = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=6b20d2bac2034606b24756716c03b72e";
    TextView poweredBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsticker);
        prepareListView();
        setupPoweredBy();
        new NewstickerDownloadTask(this, items).execute(ADDRESS);
    }

    private void prepareListView() {
        items = new ArrayList<NewstickerItem>();
        adapter = new NewstickerItemAdapter(NewstickerActivity.this, items);
        ListView list = (ListView) findViewById(R.id.news_list);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openUrl(position);
                return true;
            }
        });
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
        adapter.notifyDataSetChanged();
    }

}

