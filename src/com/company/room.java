package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Benjamin on 9/4/15.
 */
public class room {
    Set<Character> vowels = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    int roomNum, timesVisited = 0;
    int[] directions = new int[6];  //Each int in this array = a room number to travel to

    ArrayList<item> hasItems = new ArrayList<item>();
    ArrayList<npc> hasNpcs = new ArrayList<npc>();

    public room(int rnum, int[] sentDirs) {   //Initializer, assigns a room name and number
        roomNum = rnum;
        directions = sentDirs;
    }

    public void visit() {
        timesVisited++;
        System.out.println(Main.roomMap.get(roomNum));
        itemPresent();
        npcPresent();
    }

    public void take(String someItem) {
        //System.out.println(Main.reverseItemMap.get(someItem));
        try {
            if (hasItems.contains(Main.items[Main.reverseItemMap.get(someItem)])) {
                Main.john.addItem(Main.reverseItemMap.get(someItem));
                hasItems.remove(Main.items[Main.reverseItemMap.get(someItem)]);
            }
        } catch (NullPointerException e) {
            //System.out.println(e.getMessage());
        }
        Main.john.calculateMpWep();
        stay();
    }

    public void drop(String someItem) {
        try {
            hasItems.add(Main.items[Main.reverseItemMap.get(someItem)]);
            Main.john.removeItem(Main.reverseItemMap.get(someItem));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        stay();
    }

    public void stay() {
        System.out.println(Main.roomMap.get(roomNum));
        itemPresent();
        npcAttack();
    }

    public void itemPresent() {
        if (!hasItems.isEmpty()) {
            for (item currentItem : hasItems) {
                if (hasItems.indexOf(currentItem) != 0)
                    System.out.println();    //If there's more than 1 item, a line will be printed
                if (!vowels.contains(Character.toLowerCase(Main.itemMap.get(currentItem.getNum()).charAt(0))))
                    System.out.print("A ");   //If the item does not begin with a vowel, an 'A' will be printed
                System.out.print(Main.itemMap.get(currentItem.getNum()) + " is here");
            }
            System.out.println();
        }
    }

    public void npcPresent() {
        if (!hasNpcs.isEmpty()) {
            for (npc currentNpc : hasNpcs) {
                if (hasNpcs.indexOf(currentNpc) != 0)
                    System.out.println();   //If there's more than 1 npc, a line will be printed
                if (currentNpc.isAlive()) {
                    if (!vowels.contains(Character.toLowerCase(Main.npcMap.get(currentNpc.getNum()).charAt(0))))
                        System.out.print("A ");   //If the npc does not begin with a vowel, an 'A' will be printed
                    System.out.print(Main.npcMap.get(currentNpc.getNum()) + " is here\n");
                }
            }
        }
    }

    public void npcAttack() {
        if (!hasNpcs.isEmpty()) {
            for (npc currentNpc : hasNpcs) {
                if (hasNpcs.indexOf(currentNpc) != 0)
                    System.out.println();   //If there's more than 1 npc, a line will be printed
                if (currentNpc.isAlive()) {
                    if (!vowels.contains(Character.toLowerCase(Main.npcMap.get(currentNpc.getNum()).charAt(0))))
                        System.out.print("A ");
                    else System.out.print("An ");
                    if (currentNpc.getDamage() > 0) {   //If the npc does damage do some damage
                        System.out.print(Main.npcMap.get(currentNpc.getNum()) + " attacks you with his " + currentNpc.getWepName() + "\n");
                        Main.john.takeDmg(currentNpc.getDamage());
                    }
                    else System.out.print(Main.npcMap.get(currentNpc.getNum()) + " is here\n"); //If not mention that it is here
                }
            }
        }
    }

    public void wipe() {
        hasItems.clear();
    }

    public void giveItem(item newItem) {
        hasItems.add(newItem);
    }

    public void giveNpc(npc newNpc) {
        hasNpcs.add(newNpc);
    }

    public int getRoomNum() {
        return roomNum;
    }

    public int getDirs(int dir) {
        return directions[dir];
    }

}