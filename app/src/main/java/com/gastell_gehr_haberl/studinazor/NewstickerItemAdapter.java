package com.gastell_gehr_haberl.studinazor;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by lucas on 07.09.2017.
 */

public class NewstickerItemAdapter extends ArrayAdapter<NewstickerItem> {

    private List <NewstickerItem> items;
    private Context context;

    public NewstickerItemAdapter(Context context, List<NewstickerItem> items) {
        super(context, R.layout.newsticker_item, items);
        this.context = context;
        this.items = items;
    }
}
