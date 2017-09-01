package com.gastell_gehr_haberl.studinazor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Tanja on 23.08.2017.
 */

public class EinkaufslisteAdapter extends ArrayAdapter<ShopItem> {
    
    private ArrayList<ShopItem> shopList;
    private Context context;

    public EinkaufslisteAdapter(Context context, ArrayList<ShopItem> listItems) {
        super(context, R.layout.shoplist_itemlist, listItems);
        this.context = context;
        this.shopList = listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.shoplist_itemlist, null);

        }

        ShopItem item = shopList.get(position);

        if (item != null) {
            //neu
            TextView itemAmount = (TextView) v.findViewById(R.id.item_amount);
            TextView itemUnit = (TextView) v.findViewById(R.id.item_unit);
            TextView itemName = (TextView) v.findViewById(R.id.item_name);

            itemAmount.setText(item.getAmount());
            itemUnit.setText(item.getUnit());
            itemName.setText(item.getName());
        }

        return v;
    }
}
