package com.company;

/**
 * Created by Benjamin on 9/4/15.
 */
public class room {
    String roomName;
    int roomNum, timesVisited = 0;

    public room(int rnum, String rname) {   //Initializer
        roomNum = rnum;
        roomName = rname;
    }

    public void visit() {timesVisited++;}

    public String getRoomName() {return roomName;}

    public int getRoomNum() {return roomNum;}

}
