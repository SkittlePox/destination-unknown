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

    String genderPossess, name, killSound;

    Set<Character> vowels = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    ArrayList<item> hasItems = new ArrayList<item>();
    ArrayList<item> itemDropList = new ArrayList<item>();

    boolean alive = true, unique;

    npc(int[] npcInfo, boolean givenUniqueness, String givenGenderPossess, String givenName, String givenKill) {
        number = npcInfo[0];
        maxHealth = npcInfo[1];
        health = maxHealth;
        unique = givenUniqueness;
        genderPossess = givenGenderPossess;
        name = givenName;
        killSound = givenKill;
    }

    public void damage(int dmg) {
        health -= dmg;
        if (health <= 0) {
            alive = false;
            System.out.print(killSound + " ");
            if (!unique) {
                System.out.print("the ");
            }
            System.out.print(name + "\n");
            if (itemDropList.size() > 0) {
                if (!unique) System.out.print("The");
                System.out.print(name + " has dropped:\n");
                for (item currentDropItem : itemDropList) {
                    if (!currentDropItem.isUnique()) {
                        if (!vowels.contains(Character.toLowerCase(currentDropItem.getName().charAt(0)))) {
                            System.out.print("A ");
                        } else System.out.print("An ");
                    }
                    System.out.println(currentDropItem.getName());
                    Main.rooms[Main.john.getRoomNum()].giveItem(currentDropItem);
                }
            }
        }
    }

    public String getGenderPosses() {
        return genderPossess;
    }

    public String getName() {
        return name;
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
        return hasItems.get(mostPowerWep).getName();
    }

    public item getWep() {
        return hasItems.get(mostPowerWep);
    }

    ;

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
