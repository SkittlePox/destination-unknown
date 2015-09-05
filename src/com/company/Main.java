package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);  //Scanner for taking input from the console
    static Scanner mapFile;

    public static HashMap<Integer, String> itemMap = new HashMap(); //Basically im setting integers that represent items to their corresponding item name (String)
    public static HashMap<Integer, String> roomMap = new HashMap(); //Same thing but for the rooms

    static String[][] roomData; //A 2d array where [room number][room data (including directional movements, room name, etc.)]
    static room[] rooms;    //An array of our room objects. See room.java.
    static item[] items;    //An array of item object.

    static player john = new player();  //You become self aware but will remain nameless

    static String input;
    static String[] parsedCommand;

    static int[][] itemInfo;

    public static void main(String[] args) throws IOException {
        mapFile = new Scanner(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/company/mapread.csv"));

        popRooms();

        //listRooms();

        System.out.println("You are in a destination unknown\nSurrounded by on open field you wake up wearing nothing but leather pants and a tunic\nType 'syntax' for the command syntax");

        while (true) {
            update();
            parse();
        }    //Runs parse() and update() until quit
    }

    static void parse() {   //The body of the UI
        input = scan.nextLine().toLowerCase();    //Sets a String to the input command
        parsedCommand = input.split("[ ]+");    //Splits that String into a String array so you can examine each word in the input

        //for(int i = 0; i < parsedCommand.length; i++) System.out.println(parsedCommand[i]);

        switch (parsedCommand[0]) {
            case "syntax":
                System.out.println("To navigate this world, you may move in 6 directions:\nnorth\nsouth\neast\nwest\nup\ndown");
                System.out.println("You may type 'north', 'n', 'go north', 'go n' to go north for example");
                break;
            case "go":
                john.goTo(parsedCommand[1]);    //parsedCommand[x] is a String that will be a direction (ie: north) that is sent to player.goTo() for translating into a destination room number
                break;
            default:
                john.goTo(parsedCommand[0]);
                break;
        }
    }

    static void update() {  //Prints room info like name
        System.out.println(roomMap.get(john.getRoomNum())); //Retrieves the name of the room given the room number and prints it
    }

    static void popItems() {

    }

    static void popRooms() {
        int lines = mapFile.nextInt();
        roomData = new String[lines][];   //This and rooms will hold room data
        rooms = new room[lines];

        System.out.print(mapFile.nextLine());   //finishes off first line of csv
        for (int i = 0; i != lines; i++) {
            roomData[i] = mapFile.nextLine().split("[,]");
            int[] subDir = new int[6];  //subDir contains the direction information for each room that is sent to each room
            for (int b = 0; b < 6; b++) {
                subDir[b] = Integer.valueOf(roomData[i][b+2]);
            }
            rooms[i] = new room(Integer.parseInt(roomData[i][0]), subDir);  //Creates a new room with the given data
            roomMap.put(Integer.parseInt(roomData[i][0]), roomData[i][1]);  //Inputs data into the HashMap roomMap
        }
    }

    static void listRooms() {   //Lists the rooms
        for (int i = 0; i != roomMap.size(); i++) {
            System.out.println(i + " " + roomMap.get(i));   //Testing out the functionality of the HashMap roomMap
        }
    }
}