package com.gastell_gehr_haberl.studinazor;

/**
 * Created by Tanja on 23.08.2017.
 */

public class ShopItem implements Comparable<ShopItem> {

    private String name;

    public ShopItem(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public int compareTo(ShopItem another) {
        return 0; //getDueDate().compareTo(another.getDueDate());
    }

    @Override
    public String toString() {
        return "Name: " + getName();
    }
}
