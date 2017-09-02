package com.gastell_gehr_haberl.studinazor;

/**
 * Created by Tanja on 23.08.2017.
 */

public class ShopItem implements Comparable<ShopItem> {

    private String amount;
    private String unit;
    private String name;

    public ShopItem(String amount, String unit, String name){
        this.amount = amount;
        this.unit = unit;
        this.name = name;
    }

    public String getAmount(){
        return amount;
    }

    public String getUnit(){
        return unit;
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
        return "Menge: " + getAmount() + ", Einheit: " + getUnit() + ", Name: " + getName();
    }
}
