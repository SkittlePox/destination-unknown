package com.company;

/**
 * Created by Benjamin on 9/5/15.
 */
public class item {
    int number, damage, weight;

    item(int[] givInfo){
        number = givInfo[0];
        damage = givInfo[1];
        weight = givInfo[2];
    }

    public int getNum() {
        return number;
    }

    public int getDamage() {
        return damage;
    }

    public int getWeight() {
        return weight;
    }
}
