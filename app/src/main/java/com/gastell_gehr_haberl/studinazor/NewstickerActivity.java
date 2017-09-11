package com.gastell_gehr_haberl.studinazor;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucas on 07.09.2017.
 */

public class NewstickerActivity extends StartScreen implements DownloadListener {

    private ArrayList<NewstickerItem> items;
    private NewstickerItemAdapter adapter;
    private final static String ADDDRESS = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=6b20d2bac2034606b24756716c03b72e";
    TextView poweredBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsticker);
        prepareListView();
        new NewstickerDownloadTask(this, items).execute(ADDDRESS);
        //initNewsList();
        //initUI();
        setupPoweredBy();
    }

    private void initNewsList() {
        items = new ArrayList<NewstickerItem>();
        initListAdapter();
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.news_list);
        adapter = new NewstickerItemAdapter(this, items);
        list.setAdapter(adapter);
    }

    private void initUI() {
        initListView();
        initPoweredByLink();
    }

    private void initPoweredByLink() {
        poweredBy = (TextView) findViewById(R.id.poweredBy_id);
        poweredBy.setClickable(true);
        poweredBy.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='https://newsapi.org/'> Powered by News API </a>";
        poweredBy.setText(Html.fromHtml(text));
    }

    private void initListView() {
        /*final ListView list = (ListView) findViewById(R.id.news_list); //FINAL!!!!!!!!!!!!!
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item = (TextView) list.getItemAtPosition(position); //evtl falsch, würde source hyperlink ersetzen
                NewstickerItem news = (NewstickerItem) list.getItemAtPosition(position);
                item.setClickable(true);
                item.setMovementMethod(LinkMovementMethod.getInstance());
                String url = news.getUrl();
                String text = "<a href='url'"
                return false;
            }
        });*/
        ListView list = (ListView) findViewById(R.id.news_list);
    }

    private void prepareListView() {
        items = new ArrayList<NewstickerItem>();
        adapter = new NewstickerItemAdapter(NewstickerActivity.this, items);
        ListView list = (ListView) findViewById(R.id.news_list);
        View header = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.newsticker_item, null);
        list.addHeaderView(header);
        list.setAdapter(adapter);
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

