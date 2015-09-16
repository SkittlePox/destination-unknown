package com.company;

/**
 * Created by Benjamin on 9/5/15.
 */
public class item {
    int number, damage, weight;

    boolean unique;

    String name;

    item(int[] givInfo, boolean givenUniqueness, String givenName) {
        number = givInfo[0];
        damage = givInfo[1];
        weight = givInfo[2];
        unique = givenUniqueness;
        name = givenName;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return number;
    }

    public boolean isUnique() {
        return unique;
    }

    public int getDamage() {
        return damage;
    }

    public int getWeight() {
        return weight;
    }
}
