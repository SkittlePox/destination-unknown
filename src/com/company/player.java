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

    public void goTo(String someDir) {  //From the direction name to the destination room number
        switch(someDir) {
            case "n":
            case "north":
                warpTo(Main.rooms[currentRoom].getDirs(0));
                break;
            case "s":
            case "south":
                warpTo(Main.rooms[currentRoom].getDirs(1));
                break;
            case "e":
            case "east":
                warpTo(Main.rooms[currentRoom].getDirs(2));
                break;
            case "w":
            case "west":
                warpTo(Main.rooms[currentRoom].getDirs(3));
                break;
            case "u":
            case "up":
                warpTo(Main.rooms[currentRoom].getDirs(4));
                break;
            case "d":
            case "down":
                warpTo(Main.rooms[currentRoom].getDirs(5));
                break;
            default:
                System.out.println("nope");
                break;
        }
    }

    public void warpTo(int destinationRoom) {   //Does the actual moving
        currentRoom = destinationRoom;
        Main.rooms[destinationRoom].visit();
    }

    public ArrayList getItems() {
        return items;
    }

    public int getRoomNum() {return currentRoom;}
}