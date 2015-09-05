package com.company;

import java.util.ArrayList;

/**
 * Created by Benjamin on 9/5/15.
 */
public class player {
    String name;
    ArrayList<Integer> items = new ArrayList<Integer>();

    public player(String pName) {
        name = pName;
    }

    public void addItem(int item) {
        items.add(item);
    }

    public ArrayList getItems() {
        return items;
    }

    public String getName() {
        return name;
    }
}
