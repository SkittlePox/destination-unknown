package com.company;

import java.util.ArrayList;

/**
 * Created by Benjamin on 9/5/15.
 */
public class player {
    ArrayList<Integer> items = new ArrayList<Integer>();
    int currentRoom = 0;

    public player() {}

    public void addItem(int item) {
        items.add(item);
    }

    public void goTo(String someDir) {
        switch(someDir) {
            case "n":
            case "north":

                break;
        }
    }

    public void warpTo(int destinationRoom) {
        currentRoom = destinationRoom;
        Main.rooms[destinationRoom].visit();
    }

    public ArrayList getItems() {
        return items;
    }

    public int getRoomNum() {return currentRoom;}
}