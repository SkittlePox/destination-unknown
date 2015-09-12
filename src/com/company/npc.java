package com.company;

import java.util.ArrayList;

/**
 * Created by Benjamin on 9/6/15.
 */
public class npc {
    int number, maxHealth, health, damage;

    boolean alive = true;

    npc(int[] npcInfo) {
        number = npcInfo[0];
        maxHealth = npcInfo[1];
        health = maxHealth;
        damage = npcInfo[2];
    }

    public void damage(int dmg) {
        health-=dmg;
        if(health <= 0) {
            alive = false;
            System.out.println("You have slain the " + Main.npcMap.get(number));
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getNum() {
        return number;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }
}
