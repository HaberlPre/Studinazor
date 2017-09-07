package com.gastell_gehr_haberl.studinazor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucas on 07.09.2017.
 */

public class NewstickerActivity extends StartScreen implements DownloadListener {

    private ArrayList<NewstickerItem> items;
    private NewstickerItemAdapter adapter;
    private final static String ADDDRESS = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=6b20d2bac2034606b24756716c03b72e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen); //TODO evtl falsch, in bundesliga eig datei
        prepareTextView();
        new NewstickerDownloadTask(this, items).execute(ADDDRESS);
    }

    private void prepareTextView() {
        items = new ArrayList<NewstickerItem>();
        adapter = new NewstickerItemAdapter(NewstickerActivity.this, items);
        TextView text = (TextView) findViewById(R.id.newsticker_start_id); //TODO evtl newsticker_id
        View header = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.newsticker_item, null);
        /** //TODO bs
         * text.addHeader(header); //in bundesliga code weil ListView statt TextView
         * text.setAdapter(adapter);
         */
        text.setText((CharSequence) adapter); //?
    }


    @Override
    public void onDownloadFinished() {
        adapter.notifyDataSetChanged();
    }
}

