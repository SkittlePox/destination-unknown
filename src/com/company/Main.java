package com.company;

import java.io.*;
import java.util.*;

public class Main {

    static Scanner scan = new Scanner(System.in);  //Scanner for taking input from the console
    static Scanner mapFile;
    static Scanner itemFile;
    static Scanner npcFile;
    static Scanner playerFile;

    public static HashMap<Integer, String> itemMap = new HashMap<>(); //Basically im setting integers that represent items to their corresponding item name (String)
    public static HashMap<String, Integer> reverseItemMap = new HashMap<>();
    public static HashMap<Integer, String> npcMap = new HashMap<>();  //Same thing but for the npcs
    public static HashMap<Integer, String> npcWepMap = new HashMap<>();
    public static HashMap<String, Integer> reverseNpcMap = new HashMap<>();
    public static HashMap<Integer, String> roomMap = new HashMap<>(); //Same thing but for the rooms

    static String[][] roomData; //A 2d array where [room number][room data (including directional movements, room name, etc.)]
    static String[][] itemData;
    static String[][] npcData;
    static String[] playerData;
    static room[] rooms;    //An array of our room objects. See room.java.
    static item[] items;    //An array of item object.
    static npc[] npcs;

    static player john;     //You become self aware but will remain nameless

    static String input;
    static String[] parsedCommand;

    public static void main(String[] args) throws IOException {
        mapFile = new Scanner(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/company/mapread.csv"));
        itemFile = new Scanner(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/company/itemread.csv"));
        npcFile = new Scanner(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/company/npcread.csv"));
        playerFile = new Scanner(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/company/playerread.csv"));

        popItems();
        popNpcs();
        popRooms();
        createPlayer();

        //listRooms();

        rooms[john.currentRoom].visit();

        while (john.isAlive()) {  //Runs parse() until quit
            parse();
            //update();
        }
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
            case "take":
                if(parsedCommand[1].equals("all")) john.takeAll();
                else rooms[john.currentRoom].take(input.substring(parsedCommand[0].length() + 1));  //Sends rest of command to take()
                break;
            case "i":
            case "inventory":
                john.printInventory();
                break;
            case "drop":
                if(parsedCommand[1].equals("all")) john.dropAll();
                else rooms[john.currentRoom].drop(input.substring(parsedCommand[0].length() + 1));  //Sends rest of command to take()
                break;
            case "l":
            case "look":
                rooms[john.currentRoom].stay();
                break;
            case "kill":
                john.attack(parsedCommand[1]);
                break;
            case "quit":
            case "exit":
                john.alive = false;
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
        int lines = itemFile.nextInt();
        itemData = new String[lines][];
        items = new item[lines];

        System.out.print(itemFile.nextLine());   //finishes off first line of csv
        for (int i = 0; i != lines; i++) {
            itemData[i] = itemFile.nextLine().split("[,]");
            items[i] = new item(new int[]{Integer.valueOf(itemData[i][0]), Integer.valueOf(itemData[i][2]), Integer.valueOf(itemData[i][3])});  //Creates a new item with the given data
            itemMap.put(Integer.parseInt(itemData[i][0]), itemData[i][1]);  //Inputs data into the HashMap itemMap
            reverseItemMap.put(itemData[i][1].toLowerCase(), Integer.parseInt(itemData[i][0]));
        }
    }

    static void popNpcs() {
        int lines = npcFile.nextInt();
        npcData = new String[lines][];
        npcs = new npc[lines];

        System.out.println(npcFile.nextLine());   //finishes off first line of csv
        for(int i= 0; i != lines; i++) {
            npcData[i] = npcFile.nextLine().split("[,]");
            npcs[i] = new npc(new int[]{Integer.valueOf(npcData[i][0]), Integer.valueOf(npcData[i][3]), Integer.valueOf(npcData[i][4])});
            npcMap.put(Integer.valueOf(npcData[i][0]), npcData[i][1]);
            npcWepMap.put(Integer.valueOf(npcData[i][0]), npcData[i][2]);
            reverseNpcMap.put(npcData[i][1].toLowerCase(), Integer.valueOf(npcData[i][0]));
        }
    }

    static void popRooms() {
        int lines = mapFile.nextInt();
        roomData = new String[lines][];   //This and rooms will hold room data
        rooms = new room[lines];

        System.out.print(mapFile.nextLine());   //finishes off first line of csv
        for (int i = 0; i != lines; i++) {
            boolean itemnpcToggle = false;
            roomData[i] = mapFile.nextLine().split("[,]");
            int[] subDir = new int[6];  //subDir contains the direction information for each room that is sent to each room
            for (int b = 0; b < 6; b++) {
                subDir[b] = Integer.valueOf(roomData[i][b + 2]);
            }
            rooms[i] = new room(Integer.parseInt(roomData[i][0]), subDir);  //Creates a new room with the given data
            roomMap.put(Integer.parseInt(roomData[i][0]), roomData[i][1]);  //Inputs data into the HashMap roomMap\
            for (int c = 8; c < roomData[i].length; c++) {  //Adds new item to room from index 8 and on
                if(itemnpcToggle) rooms[i].giveNpc(npcs[Integer.valueOf(roomData[i][c])]);
                if(roomData[i][c].equals("|")) itemnpcToggle = true;
                if(!itemnpcToggle) rooms[i].giveItem(items[Integer.valueOf(roomData[i][c])]);
            }
        }
    }

    static void createPlayer() {
        playerData = playerFile.nextLine().split("[,]");
        john = new player(new int[]{Integer.valueOf(playerData[0]), Integer.valueOf(playerData[1]), Integer.valueOf(playerData[2])});

        for(int i = 3; i < playerData.length; i++) {
            john.addItem(Integer.valueOf(playerData[i]));
        }
    }

    static void listRooms() {   //Lists the rooms
        for (int i = 0; i != roomMap.size(); i++) {
            System.out.println(i + " " + roomMap.get(i));   //Testing out the functionality of the HashMap roomMap
        }
    }
}