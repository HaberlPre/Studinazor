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

    public String url;

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

        TextView title = (TextView) v.findViewById(R.id.newsticker_start_id);

        NewstickerItem item = items.get(position);

        title.setText(item.getTitle());
        url = item.getUrl();

        return v;
    }

    public String getUrl() {
        return url;
    }
}
