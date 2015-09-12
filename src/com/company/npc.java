package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Benjamin on 9/6/15.
 */
public class npc {
    int number, maxHealth, health, damage = 0, mostPower = 0, mostPowerWep = 0;

    Set<Character> vowels = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    ArrayList<item> hasItems = new ArrayList<item>();
    ArrayList<item> itemDropList = new ArrayList<item>();

    boolean alive = true, unique;

    npc(int[] npcInfo, boolean givenUniqueness) {
        number = npcInfo[0];
        maxHealth = npcInfo[1];
        health = maxHealth;
        unique = givenUniqueness;
    }

    public void damage(int dmg) {
        health -= dmg;
        if (health <= 0) {
            alive = false;
            System.out.print("You have slain ");
            if (!unique) {
                System.out.print("the ");
            }
            System.out.print(Main.npcMap.get(number));
            if (itemDropList.size() > 0) {
                if (!unique) {
                    System.out.println("\nThe " + Main.npcMap.get(number) + " has dropped:");
                } else {
                    System.out.println("\n" + Main.npcMap.get(number) + " has dropped:");
                }
                for (item currentDropItem : itemDropList) {
                    if (!currentDropItem.isUnique()) {
                        if (!vowels.contains(Character.toLowerCase(Main.itemMap.get(currentDropItem.getNum()).charAt(0)))) {
                            System.out.print("A ");
                        } else System.out.print("An ");
                    }
                    System.out.println(Main.itemMap.get(currentDropItem.getNum()));
                    Main.rooms[Main.john.getRoomNum()].giveItem(currentDropItem);
                }
            }
        }
    }

    public void calculateMpWep() {
        for (item currentItem : hasItems) {
            if (currentItem.getDamage() > mostPower) {
                mostPower = currentItem.getDamage();
                mostPowerWep = hasItems.indexOf(currentItem);
            }
        }
        damage = mostPower;
    }

    public void giveItem(item newItem) {
        hasItems.add(newItem);
        calculateMpWep();
    }

    public void giveDropItem(item newDropItem) {
        itemDropList.add(newDropItem);
    }

    public String getWepName() {
        return Main.itemMap.get(hasItems.get(mostPowerWep).getNum());
    }

    public boolean isUnique() {
        return unique;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getNum() {
        return number;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }
}