package com.company;

public class event {

    int[] heldItems, roomItems, deadNcps;
    boolean triggered, repeat;

    public event(int[] itemsHeldNeeded, int[] itemsRoomNeeded, int[] npcsDead, boolean repeated) {
        heldItems = itemsHeldNeeded;
        roomItems = itemsRoomNeeded;
        deadNcps = npcsDead;
        repeat = repeated;
    }

    public void test() {

    }
}