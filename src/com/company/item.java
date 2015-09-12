package com.company;

/**
 * Created by Benjamin on 9/5/15.
 */
public class item {
    int number, damage, weight;

    boolean unique;

    item(int[] givInfo, boolean givenUniqueness) {
        number = givInfo[0];
        damage = givInfo[1];
        weight = givInfo[2];
        unique = givenUniqueness;
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
