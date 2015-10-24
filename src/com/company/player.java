package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class player {
    ArrayList<item> hasItems = new ArrayList<item>();
    Set<Character> vowels = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    int currentRoom, lastRoom, maxHealth, health, mostPowerWep = -1, mostPower = 0;
    boolean alive = true;

    public player(int[] playerInfo) {
        maxHealth = playerInfo[0];
        health = playerInfo[1];
        currentRoom = playerInfo[2];
        lastRoom = playerInfo[2];
    }

    public void addItem(int itemNum) {
        hasItems.add(Main.items[itemNum]);
        calculateMpWep();
    }

    public void removeItem(int itemNum) {
        hasItems.remove(Main.items[itemNum]);
    }

    public void useItem(String name) {
        int removeIndex = -1;
        for (item currentItem : hasItems) {
            if (currentItem.getName().equals(name) && currentItem.getHeal() > 0) {
                health += currentItem.getHeal();
                health = health > maxHealth ? maxHealth : health;
                System.out.print("You have healed from ");
                if (!currentItem.isUnique()) System.out.print("the ");
                System.out.print(currentItem.getName() + ".\n");
                removeIndex = hasItems.indexOf(currentItem);
                break;
            }
        }
        if (removeIndex != -1)
            hasItems.remove(removeIndex);
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
                System.out.println("I don't know what that means.");
        }
    }

    public void attack(String someNpc) {
        lastRoom = currentRoom;
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
    }

    public void takeDmg(int dmgToTake) {
        health -= dmgToTake;
        if (health <= 0) {
            alive = false;
            System.out.println("I'm afraid you have been killed, perhaps you should try again.");
        }
    }

    public void takeAll() {
        lastRoom = currentRoom;
        if (Main.rooms[currentRoom].hasItems.isEmpty()) System.out.println("There is nothing here to take.");
        else {
            hasItems.addAll(Main.rooms[currentRoom].hasItems);
            calculateMpWep();
            Main.rooms[currentRoom].wipe();
            System.out.println("Taken.");
        }
    }

    public void take(String someItem) {
        lastRoom = currentRoom;
        try {
            if (Main.rooms[currentRoom].hasItems.contains(Main.items[Main.checkItemMap.get(someItem)])) {
                addItem(Main.checkItemMap.get(someItem));
                Main.rooms[currentRoom].hasItems.remove(Main.items[Main.checkItemMap.get(someItem)]);
                System.out.println("Taken.");
            }
        } catch (NullPointerException e) {
            System.out.println("You can't take an item that is not here.");
        }
        calculateMpWep();
    }

    public void drop(String someItem) {
        lastRoom = currentRoom;
        try {
            Main.rooms[currentRoom].hasItems.add(Main.items[Main.checkItemMap.get(someItem)]);
            removeItem(Main.checkItemMap.get(someItem));
        } catch (NullPointerException e) {
            System.out.println("You can't drop an item you do not have.");
        }
        Main.rooms[currentRoom].visit();
    }

    public void dropAll() {
        lastRoom = currentRoom;
        Main.rooms[currentRoom].hasItems.addAll(hasItems);
        hasItems.clear();
    }

    public void printInventory() {
        lastRoom = currentRoom;
        if (!hasItems.isEmpty()) {
            System.out.println("You are carrying:");
            for (item currentItem : hasItems) {
                if (!currentItem.isUnique())
                    if (vowels.contains(currentItem.getName().charAt(0))) System.out.print("An ");
                    else System.out.print("A ");
                System.out.println(currentItem.getName());
            }
        } else {
            System.out.println("You are carrying nothing.");
        }
    }

    public void warpTo(int destinationRoom) {   //Does the actual moving
        lastRoom = currentRoom;
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