package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    Scanner scan = new Scanner(System.in);  //Scanner for taking input from the console

    public static HashMap<Integer, String> itemMap = new HashMap(); //Basically im setting integers that represent items to their corresponding item name (String)
    static ArrayList<String> fileRead = new ArrayList<String>();    //Assists in reading the file - an arrayList that holds the room data
    static String[][] roomData; //A 2d array where [room number][room data (including directional movements, room name, etc.)]
    static room[] rooms;    //An array of our room objects. See room.java.

    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader("/Users/Benjamin/MTG/Java/Destination_Unknown/src/com/company/mapread.csv"); //we need to fix this, right now it links a txt file that's specific to my filesystem
        BufferedReader br = new BufferedReader(in); //Its complicated to read a file and this is one of the components involved
        String bre = br.readLine(); //Another component
        while (bre != null) {   //fills the arrayList with room data
            fileRead.add(bre);
            bre = br.readLine();
        }
        in.close(); //Necessary when done using bufferedReader

        roomData = new String[fileRead.size()][];   //This and rooms will hold room data
        rooms  = new room[fileRead.size()];

        popRooms();

        listRooms();

    }

    static void popRooms() {    //Populates roomData and rooms with info from the text file
        String[] rawRoom = fileRead.toArray(new String[fileRead.size()]);   //A stepping stone to the data population
        for (int i = 0; i != fileRead.size(); i++) {    //Actually fills the variables
            //System.out.println(i + " " + fileRead.size());
            roomData[i] = rawRoom[i].split("[,]");  //The .split("[,]") separates each line into an array in which each spot contains values that were separated by commas
            rooms[i] = new room(Integer.parseInt(roomData[i][0]), roomData[i][1]);  //Creates a new room with the given data
        }
    }

    static void listRooms() {   //Lists the rooms
        for (int i = 0; i != rooms.length; i++) {
            System.out.println(rooms[i].getRoomNum() + " " + rooms[i].getRoomName());
        }
    }
}