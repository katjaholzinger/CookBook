package com.hwr.cookbook;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Ingredient {

    public String name;
    private String ID;
    public float amount = 0;
    public String unit = "l";



    public Ingredient() {
        super();
    }

    public Ingredient (String name, float amount, String unit) {
        super();
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getID() { return ID; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setID(String id) {
        this.ID = id;
    }

}




