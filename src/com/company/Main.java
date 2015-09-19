package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

public class Main {

    static Scanner scan = new Scanner(System.in);  //Scanner for taking input from the console

    static FileReader configReader;
    static JSONObject playerData;
    static JSONArray itemData;
    static JSONArray npcData;
    static JSONArray roomData;

    public static HashMap<String, Integer> checkItemMap = new HashMap<>();
    public static HashMap<String, Integer> checkNpcMap = new HashMap<>();
    public static HashMap<String, Integer> checkRoomMap = new HashMap<>();

    static room[] rooms;    //An array of our room objects. See room.java.
    static item[] items;    //An array of item object.
    static npc[] npcs;

    static player john;     //You become self aware but will remain nameless

    static String input;
    static String[] parsedCommand;

    public static void main(String[] args) throws Throwable {
        configReader = new FileReader(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/company/gameconfig.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject file = (JSONObject) jsonParser.parse(configReader);

        playerData = (JSONObject) file.get("player");
        itemData = (JSONArray) file.get("items");
        npcData = (JSONArray) file.get("npcs");
        roomData = (JSONArray) file.get("rooms");

        popItems();
        popNpcs();
        popRooms();
        createPlayer();

        rooms[john.getRoomNum()].visit();

        while (john.isAlive()) {  //Runs parse() until quit
            parse();
        }
    }

    static void parse() {   //The body of the UI
        input = scan.nextLine().toLowerCase();    //Sets a String to the input command
        parsedCommand = input.split("[ ]+");    //Splits that String into a String array so you can examine each word in the input

        switch (parsedCommand.length) {
            case 0:
                rooms[john.getRoomNum()].stay();
                parse();
                break;
            case 1:
                switch (parsedCommand[0]) {
                    case "syntax":
                        System.out.println("To navigate this world, you may move in 6 directions:\nnorth\nsouth\neast\nwest\nup\ndown");
                        System.out.println("You may type 'north', 'n', 'go north', 'go n' to go north for example");
                        break;
                    case "i":
                    case "inventory":
                        john.printInventory();
                        break;
                    case "l":
                    case "look":
                        rooms[john.getRoomNum()].stay();
                        break;
                    case "quit":
                    case "exit":
                        john.alive = false;
                        break;
                    default:
                        john.goTo(parsedCommand[0]);
                        break;
                }
                break;

            default:
                String tempCommand = "";
                switch (parsedCommand[0]) {
                    case "go":
                        john.goTo(parsedCommand[1]);    //parsedCommand[x] is a String that will be a direction (ie: north) that is sent to player.goTo() for translating into a destination room number
                        break;
                    case "take":
                        switch (parsedCommand[1]) {
                            case "all":
                                john.takeAll();
                                break;
                            case "the":
                                for (int x = 2; x < parsedCommand.length; x++) {
                                    tempCommand = tempCommand.concat(" " + parsedCommand[x]);
                                }
                                john.take(tempCommand.substring(1));  //Sends command to take()
                                break;
                            default:
                                for (int x = 1; x < parsedCommand.length; x++) {
                                    tempCommand = tempCommand.concat(" " + parsedCommand[x]);
                                }
                                john.take(tempCommand.substring(1));  //Sends command to take()
                                break;
                        }
                        break;
                    case "drop":
                        switch (parsedCommand[1]) {
                            case "all":
                                john.dropAll();
                                break;
                            case "the":
                                for (int x = 2; x < parsedCommand.length; x++) {
                                    tempCommand = tempCommand.concat(" " + parsedCommand[x]);
                                }
                                john.drop(tempCommand.substring(1));
                                break;
                            default:
                                for (int x = 1; x < parsedCommand.length; x++) {
                                    tempCommand = tempCommand.concat(" " + parsedCommand[x]);
                                }
                                john.drop(tempCommand.substring(1));
                                break;
                        }
                        break;
                    case "kill":
                        if (parsedCommand[1].equals("the")) {
                            for (int x = 2; x < parsedCommand.length; x++) {
                                tempCommand = tempCommand.concat(" " + parsedCommand[x]);
                            }
                            john.attack(tempCommand.substring(1));
                        }
                        else {
                            for (int x = 1; x < parsedCommand.length; x++) {
                                tempCommand = tempCommand.concat(" " + parsedCommand[x]);
                            }
                            john.attack(tempCommand.substring(1));
                        }
                        break;
                    default:
                        john.goTo(parsedCommand[0]);
                        break;
                }
                break;
        }
    }

    static void popItems() {
        items = new item[itemData.size()];

        for (int i = 0; i != itemData.size(); i++) {
            JSONObject currentItem = (JSONObject) itemData.get(i);
<<<<<<< HEAD
            try {
                items[i] = new item(new int[]{i, Integer.valueOf(currentItem.get("damage").toString()), 0}, (boolean) currentItem.get("isUnique"), currentItem.get("name").toString());  //Creates a new item with the given data
            } catch (NullPointerException e) {
            }
=======
            //If name is null, skips over item
            if (currentItem.get("name") == null)
                continue;
            //                               If damage is not missing        then use the damage value                else default value 0      If isUnique is not missing         then use isUnique value             else default value false
            items[i] = new item(new int[]{i, currentItem.get("damage") != null ? Integer.valueOf(currentItem.get("damage").toString()) : 0, 0}, currentItem.get("isUnique") != null ? (boolean) currentItem.get("isUnique") : false, currentItem.get("name").toString());  //Creates a new item with the given data
>>>>>>> 1874bf7fbe42671eff6251a2db3a5d3434ec6b4f
            checkItemMap.put(currentItem.get("name").toString().toLowerCase(), i);
        }
    }

    static void popNpcs() {
        npcs = new npc[npcData.size()];

        for (int i = 0; i != npcData.size(); i++) {
            JSONObject currentNpc = (JSONObject) npcData.get(i);
            npcs[i] = new npc(new int[]{i, Integer.valueOf(currentNpc.get("health").toString())}, (boolean) currentNpc.get("isUnique"), (String) currentNpc.get("genderIdentifier"), currentNpc.get("name").toString());

            if (currentNpc.get("inventory") != null) {
                JSONArray currentNpcItems = (JSONArray) currentNpc.get("inventory");
                for (Object currentNpcItem : currentNpcItems) {
                    npcs[i].giveItem(items[checkItemMap.get(currentNpcItem.toString().toLowerCase())]);
                }
            }
            if (currentNpc.get("drop") != null) {
                JSONArray currentNpcDropItems = (JSONArray) currentNpc.get("drop");
                for (Object currentNpcDropItem : currentNpcDropItems) {
                    npcs[i].giveDropItem(items[checkItemMap.get(currentNpcDropItem.toString().toLowerCase())]);
                }
            }
            checkNpcMap.put(currentNpc.get("name").toString().toLowerCase(), i);
        }
    }

    static void popRooms() {
        rooms = new room[roomData.size()];

        for (int i = 0; i < roomData.size(); i++) {
            JSONObject currentRoom = (JSONObject) roomData.get(i);
            checkRoomMap.put(currentRoom.get("name").toString().toLowerCase(), i);
        }

        for (int i = 0; i < roomData.size(); i++) {
            JSONObject currentRoom = (JSONObject) roomData.get(i);
            JSONArray objectDirections = (JSONArray) currentRoom.get("directions");
            JSONArray objectItems = (JSONArray) currentRoom.get("items");
            JSONArray objectNpcs = (JSONArray) currentRoom.get("npcs");

            int[] tempDirections = new int[6];  //tempDirections contains the direction information for each room that is sent to each room

            for (int b = 0; b < 6; b++) {   //Populates the tempDirections array with integers representing which room to visit
                if (objectDirections.get(b).getClass().toString().equals("class java.lang.Long"))
                    tempDirections[b] = Integer.valueOf(objectDirections.get(b).toString());
                else
                    tempDirections[b] = checkRoomMap.get(objectDirections.get(b).toString().toLowerCase());
            }

            rooms[i] = new room(i, tempDirections, currentRoom.get("name").toString());  //Creates a new room with the given data

            if (objectItems != null)
                for (Object currentItem : objectItems) {
                    rooms[i].giveItem(items[checkItemMap.get(currentItem.toString().toLowerCase())]);
                }
            if (objectNpcs != null)
                for (Object currentNpc : objectNpcs) {
                    rooms[i].giveNpc(npcs[checkNpcMap.get(currentNpc.toString().toLowerCase())]);
                }
        }
    }

    static void createPlayer() {
        john = new player(new int[]{Integer.valueOf(playerData.get("maxHealth").toString()), Integer.valueOf(playerData.get("health").toString()), Integer.valueOf(playerData.get("startingRoomNum").toString())});

        if (playerData.get("inventory") != null) {
            JSONArray playerItems = (JSONArray) playerData.get("inventory");
            for (Object currentItem : playerItems) {
                if (currentItem.getClass().toString().equals("class java.lang.String"))
                    john.addItem(checkItemMap.get(currentItem.toString()));
                else
                    john.addItem(Integer.valueOf(currentItem.toString()));
            }
        }
    }
}