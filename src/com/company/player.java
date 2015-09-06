package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Benjamin on 9/5/15.
 */
public class player {
    ArrayList<item> items = new ArrayList<item>();

    static Set<Character> vowels = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    int currentRoom = 0;

    public player() {}

    public void addItem(int itemNum) {
        items.add(Main.items[itemNum]);
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

    public void takeAll() {
        items.addAll(Main.rooms[currentRoom].hasItems);
        Main.rooms[currentRoom].wipe();
        Main.rooms[currentRoom].stay();
    }

    public void printInventory() {
        System.out.println("You are carrying:");
        for (item currentItem : items) {
            if(items.indexOf(currentItem) != 0) System.out.println();
            if(!vowels.contains(Character.toLowerCase(Main.itemMap.get(currentItem.getNum()).charAt(0)))) System.out.print("A ");
            System.out.print(Main.itemMap.get(currentItem.getNum()));
        }
        System.out.println();
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