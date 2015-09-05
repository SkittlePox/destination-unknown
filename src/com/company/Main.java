package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);  //Scanner for taking input from the console

    public static HashMap<Integer, String> itemMap = new HashMap(); //Basically im setting integers that represent items to their corresponding item name (String)
    public static HashMap<Integer, String> roomMap = new HashMap(); //Same thing but for the rooms

    static ArrayList<String> fileRead = new ArrayList<String>();    //Assists in reading the file - an arrayList that holds the room data
    static String[][] roomData; //A 2d array where [room number][room data (including directional movements, room name, etc.)]
    static room[] rooms;    //An array of our room objects. See room.java.

    static player john = new player();  //You become self aware but will remain nameless

    static String input;
    static String[] parsedCommand;

    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()+ "com/company/mapread.csv"); //Links to csv
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

        //listRooms();

        System.out.println("You are in a destination unknown\nSurrounded by on open field you wake up wearing nothing but leather pants and a tunic\nType 'syntax' for the command syntax");

        while (true) {update();parse();}    //Runs parse() and update() until quit
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
        }
    }

    static void update() {  //Prints room info like name
        System.out.println(roomMap.get(john.getRoomNum())); //Retrieves the name of the room given the room number and prints it
    }

    static void popRooms() {    //Populates roomData and rooms with info from the text file
        String[] rawRoom = fileRead.toArray(new String[fileRead.size()]);   //A stepping stone to the data population
        for (int i = 0; i != fileRead.size(); i++) {    //Actually fills the variables
            //System.out.println(i + " " + fileRead.size());
            roomData[i] = rawRoom[i].split("[,]");  //The .split("[,]") separates each line into an array in which each spot contains values that were separated by commas
            int[] subDir = new int[6];  //subDir contains the direction information for each room that is sent to each room
            for(int b = 0; b < 6; b++) {
                subDir[b]= Integer.valueOf(roomData[i][b+2]);
            }
            rooms[i] = new room(Integer.parseInt(roomData[i][0]), subDir);  //Creates a new room with the given data
            roomMap.put(Integer.parseInt(roomData[i][0]), roomData[i][1]);
        }
    }

    static void listRooms() {   //Lists the rooms
        for (int i = 0; i != roomMap.size(); i++) {
            System.out.println(i + " " + roomMap.get(i));
        }
    }
}