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

    public room(int rnum, int[] sentDirs) {   //Initializer, assigns a room name and number
        roomNum = rnum;
        directions = sentDirs;
    }

    public void visit() {
        timesVisited++;
        System.out.println(Main.roomMap.get(roomNum));
        if(!hasItems.isEmpty()) {
            for(item currentItem : hasItems) {
                if(hasItems.indexOf(currentItem) != 0) System.out.println();
                if(!vowels.contains(Character.toLowerCase(Main.itemMap.get(currentItem.getNum()).charAt(0)))) System.out.print("A ");
                System.out.print(Main.itemMap.get(currentItem.getNum()) + " is here");
            }
        }
    }

    public void give(item newItem) {
        hasItems.add(newItem);
    }

    public int getRoomNum() {
        return roomNum;
    }

    public int getDirs(int dir) {
        return directions[dir];
    }

}