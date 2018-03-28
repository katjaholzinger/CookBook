package com.hwr.cookbook;

import java.util.ArrayList;

/**
 * Created by kholzinger on 26.03.2018.
 */

public class IngredientList extends ArrayList {
    public IngredientList(){
        
    }
    public void normalize(int defaultportions) {
        for (Object i: this) {
            Ingredient ingredient = (Ingredient)i;
            ingredient.amount = ingredient.amount / defaultportions;
        }
    }

    public Ingredient[] toArray() {
        Ingredient[] arrayIngredient = new Ingredient[this.size()];
        int i = 0;
        for (Object o: this) {
            Ingredient ingredient = (Ingredient)o;
            arrayIngredient[i] = ingredient;
            i++;
        }
        return arrayIngredient;
    }
}
