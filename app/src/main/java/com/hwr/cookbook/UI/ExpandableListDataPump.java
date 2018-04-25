package com.hwr.cookbook.UI;

import com.hwr.cookbook.Book;
import com.hwr.cookbook.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<Recipe>> getData(ArrayList<Book> books) {
        HashMap<String, List<Recipe>> expandableListDetail = new HashMap<>();

        for (Book book:books){
            List<Recipe> list = new ArrayList<>();
            //Recipe[] recipes;
            list = book.getFullRecipes();
            Collections.addAll(list);
            expandableListDetail.put(book.name, list);
        }

        return expandableListDetail;


    }
}