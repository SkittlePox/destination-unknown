package com.company;

/**
 * Created by Benjamin on 9/4/15.
 */
public class room {
    int roomNum, timesVisited = 0;
    int[] directions = new int[6];

    public room(int rnum, int[] sentDirs) {   //Initializer, assigns a room name and number
        roomNum = rnum;
        directions = sentDirs;
    }

    public void visit() {timesVisited++;}

    public int getRoomNum() {return roomNum;}

    public int getDirs(int dir) {
        return directions[dir];
    }

}