package com.hwr.cookbook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Ingredient extends IdentifiableElement {

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

}




