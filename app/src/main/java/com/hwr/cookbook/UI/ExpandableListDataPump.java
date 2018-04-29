package com.hwr.cookbook.UI;

import android.util.Log;

import com.hwr.cookbook.Book;
import com.hwr.cookbook.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<Recipe>> getData(ArrayList<Book> books) {
        HashMap<String, List<Recipe>> expandableListDetail = new HashMap<>();
        if (books != null ) {
            for (Book book:books){
                List<Recipe> list;
                list = book.getFullRecipes();
                if (list == null) {
                    Log.d("ExpandableListDump", "List is null!");
                }else{
                    Log.d("ExpandableListDump", "Size "+book.name+": "+list.size());
                }

                Collections.addAll(list);
                expandableListDetail.put(book.name, list);
            }

            return expandableListDetail;
        }
        return  null;

    }
}