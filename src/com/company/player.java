package com.company;

import java.util.ArrayList;

/**
 * Created by Benjamin on 9/5/15.
 */
public class player {
    ArrayList<item> hasItems = new ArrayList<item>();

    int currentRoom, maxHealth, health, mostPowerWep = -1, mostPower = 0;
    boolean alive = true;

    public player(int[] playerInfo) {
        maxHealth = playerInfo[0];
        health = playerInfo[1];
        currentRoom = playerInfo[2];
    }

    public void addItem(int itemNum) {
        hasItems.add(Main.items[itemNum]);
        calculateMpWep();
    }

    public void removeItem(int itemNum) {
        hasItems.remove(Main.items[itemNum]);
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
        int tempDamage;
        if (Main.checkNpcMap.containsKey(someNpc)) {
            if (Main.rooms[currentRoom].hasNpcs.contains(Main.npcs[Main.checkNpcMap.get(someNpc)])) {
                if (mostPowerWep != -1)
                    tempDamage = hasItems.get(mostPowerWep).getDamage();
                else
                    tempDamage = 1;
                Main.rooms[currentRoom].hasNpcs.get(Main.rooms[currentRoom].hasNpcs.indexOf(Main.npcs[Main.checkNpcMap.get(someNpc)])).damage(tempDamage);
            }
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
        hasItems.addAll(Main.rooms[currentRoom].hasItems);
        calculateMpWep();
        Main.rooms[currentRoom].wipe();
        Main.rooms[currentRoom].stay();
    }

    public void take(String someItem) {
        try {
            if (Main.rooms[currentRoom].hasItems.contains(Main.items[Main.checkItemMap.get(someItem)])) {
                addItem(Main.checkItemMap.get(someItem));
                Main.rooms[currentRoom].hasItems.remove(Main.items[Main.checkItemMap.get(someItem)]);
            }
        } catch (NullPointerException e) {
            System.out.println("You can't take an item that is not here");
        }
        calculateMpWep();
        Main.rooms[currentRoom].stay();
    }

    public void drop(String someItem) {
        try {
            Main.rooms[currentRoom].hasItems.add(Main.items[Main.checkItemMap.get(someItem)]);
            removeItem(Main.checkItemMap.get(someItem));
        } catch (NullPointerException e) {
            System.out.println("You can't drop an item you do not have");
        }
        Main.rooms[currentRoom].stay();
    }

    public void dropAll() {
        Main.rooms[currentRoom].hasItems.addAll(hasItems);
        hasItems.clear();
        Main.rooms[currentRoom].stay();
    }

    public void printInventory() {
        if (!hasItems.isEmpty()) {
            System.out.println("You are carrying:");
            for (item currentItem : hasItems) {
                System.out.println(currentItem.getName());
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
        for (item currentItem : hasItems) {
            if (currentItem.getDamage() > mostPower) {
                mostPower = currentItem.getDamage();
                mostPowerWep = hasItems.indexOf(currentItem);
            }
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getRoomNum() {
        return currentRoom;
    }
}