package com.hwr.cookbook.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hwr.cookbook.R;


/**
 * Created by Thomas on 18.03.2018.
 */

public class FragmentBookmarks extends Fragment{
    private LinearLayout linearLayout;

    public FragmentBookmarks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //find Linear Layout
        linearLayout = getActivity().findViewById(R.id.linearLayoutBookmarks);

        //add Recipes
        addRecipe("test",4);
        addRecipe("test2",5);
        addRecipe("test3",3);
    }


    public void addRecipe(String name, int rating){
        LayoutBookmarksRecipe recipe = new LayoutBookmarksRecipe(getContext());
        recipe.addName(name);
        recipe.addRating(rating);
        linearLayout.addView(recipe);
    }
}
