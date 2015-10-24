package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class room {
    Set<Character> vowels = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    int roomNum, timesVisited = 0;
    int[] directions = new int[6];  //Each int in this array = a room number to travel to

    String name, description;

    ArrayList<item> hasItems = new ArrayList<item>();
    ArrayList<npc> hasNpcs = new ArrayList<npc>();
    ArrayList<event> hasEvents = new ArrayList<event>();

    public room(int rnum, int[] sentDirs, String givenName, String givenVisit) {   //Initializer, assigns a room name and number
        roomNum = rnum;
        directions = sentDirs;
        name = givenName;
        description = givenVisit;
    }

    public void visit() {
        if (timesVisited == 0) {
            if (description.equals("")) System.out.println(name);
            else System.out.println(description);    //First visit case
            npcPresent();
            itemPresent();
            timesVisited++;
        } else {
            System.out.println(name);
            npcPresent();
            itemPresent();
            if (Main.john.lastRoom == Main.john.currentRoom) npcAttack();
            else timesVisited++;
        }
    }

    public void look() {
        System.out.println(description);
        npcPresent();
        itemPresent();
    }

    public void itemPresent() {
        if (!hasItems.isEmpty()) {
            for (item currentItem : hasItems) {
                if (hasItems.indexOf(currentItem) != 0)
                    System.out.println();    //If there's more than 1 item, a line will be printed
                if (!currentItem.isUnique()) {
                    if (!vowels.contains(Character.toLowerCase(currentItem.getName().charAt(0)))) {
                        System.out.print("A ");   //If the item does not begin with a vowel, an 'A' will be printed
                    } else System.out.print("An ");
                }
                System.out.print(currentItem.getName() + " is here");
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
                    if (!currentNpc.isUnique()) {
                        if (!vowels.contains(Character.toLowerCase(currentNpc.getName().charAt(0)))) {
                            System.out.print("A ");   //If the npc does not begin with a vowel, an 'A' will be printed
                        } else System.out.print("An ");
                    }
                    System.out.print(currentNpc.getName() + " is here\n");
                }
            }
        }
    }

    public void eventPresent() {
        if (!hasEvents.isEmpty()) {
            for (event currentEvent : hasEvents) {
                currentEvent.test();
            }
        }
    }

    public void npcAttack() {
        if (!hasNpcs.isEmpty()) {
            for (npc currentNpc : hasNpcs) {
                if (hasNpcs.indexOf(currentNpc) != 0)
                    System.out.println();   //If there's more than 1 npc, a line will be printed
                if (currentNpc.isAlive()) {
                    if (!currentNpc.isUnique()) {
                        if (!vowels.contains(Character.toLowerCase(currentNpc.getName().charAt(0))))
                            System.out.print("A ");
                        else System.out.print("An ");
                    }
                    if (currentNpc.getDamage() > 0) {   //If the npc does damage do some damage
                        System.out.print(currentNpc.getName() + " attacks you with ");
                        if (!currentNpc.getWep().isUnique())
                            System.out.print(currentNpc.getGenderPosses() + " ");
                        System.out.print(currentNpc.getWepName() + "\n");
                        Main.john.takeDmg(currentNpc.getDamage());
                    } else
                        System.out.print(currentNpc.getName() + " is here\n"); //If not mention that it is here
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

    public void giveEvent(event newEvent) {
        hasEvents.add(newEvent);
    }

    public int getDirs(int dir) {
        return directions[dir];
    }

}