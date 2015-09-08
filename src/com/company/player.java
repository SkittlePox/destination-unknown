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

    int currentRoom, maxHealth, health, mostPowerWep = -1, mostPower = 0;
    boolean alive = true;

    public player(int[] playerInfo) {
        maxHealth = playerInfo[0];
        health = playerInfo[1];
        currentRoom = playerInfo[2];
    }

    public void addItem(int itemNum) {
        items.add(Main.items[itemNum]);
    }

    public void removeItem(int itemNum) {
        items.remove(Main.items[itemNum]);
    }

    public void goTo(String someDir) {  //From the direction name to the destination room number
        switch (someDir) {
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
                Main.rooms[currentRoom].stay();
        }
    }

    public void attack(String someNpc) {
        if (Main.rooms[currentRoom].hasNpcs.contains(Main.npcs[Main.reverseNpcMap.get(someNpc)])) {
            if (mostPowerWep != -1)
                Main.rooms[currentRoom].hasNpcs.get(Main.reverseNpcMap.get(someNpc)).damage(items.get(mostPowerWep).getDamage());
            else
                Main.rooms[currentRoom].hasNpcs.get(Main.reverseNpcMap.get(someNpc)).damage(1);
        }
        Main.rooms[currentRoom].stay();
    }

    public void takeDmg(int dmgToTake) {
        health -= dmgToTake;
        if (health <= 0) {
            alive = false;
            System.out.println("I'm afraid you have been killed, perhaps you should try again.");
        }
    }

    public void takeAll() {
        items.addAll(Main.rooms[currentRoom].hasItems);
        calculateMpWep();
        Main.rooms[currentRoom].wipe();
        Main.rooms[currentRoom].stay();
    }

    public void dropAll() {
        Main.rooms[currentRoom].hasItems.addAll(items);
        items.clear();
        Main.rooms[currentRoom].stay();
    }

    public void printInventory() {
        if (!items.isEmpty()) {
            System.out.println("You are carrying:");
            for (item currentItem : items) {
                System.out.println(Main.itemMap.get(currentItem.getNum()));
            }
            Main.rooms[currentRoom].stay();
        } else {
            System.out.println("You are carrying nothing");
            Main.rooms[currentRoom].stay();
        }
    }

    public void warpTo(int destinationRoom) {   //Does the actual moving
        currentRoom = destinationRoom;
        Main.rooms[destinationRoom].visit();
    }

    public void calculateMpWep() {
        for (item currentItem : items) {
            if (currentItem.getDamage() > mostPower) {
                mostPower = currentItem.getDamage();
                mostPowerWep = items.indexOf(currentItem);
            }
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public ArrayList getItems() {
        return items;
    }

    public int getRoomNum() {
        return currentRoom;
    }
}