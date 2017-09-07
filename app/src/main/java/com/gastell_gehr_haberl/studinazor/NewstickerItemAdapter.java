package com.gastell_gehr_haberl.studinazor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.newsticker_item, null);
        }

        NewstickerItem item = items.get(position);

        if (item != null) {

            TextView title = (TextView) v.findViewById(R.id.newsticker_title);
            TextView description = (TextView) v.findViewById(R.id.newsticker_description);
            TextView url = (TextView) v.findViewById(R.id.newsticker_source);

        //NewstickerItem item = items.get(position);
            title.setText(String.valueOf(item.getTitle()));
            description.setText(String.valueOf(item.getDescription()));
            url.setText(String.valueOf(item.getUrl()));
        }

        return v;
    }

}
