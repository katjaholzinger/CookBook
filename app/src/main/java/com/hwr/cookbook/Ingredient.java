package com.hwr.cookbook;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Ingredient {

    public String name;
    public float amount;
    public String unit;

    public Ingredient() {

    }

    public Ingredient (String name, float amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

}
