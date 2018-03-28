package com.hwr.cookbook.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hwr.cookbook.Book;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;


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
       // /**
       //  * Just for testing
        Recipe r1 = new Recipe("Test", null, 1, null, null);
        Recipe r2 = new Recipe("Test2", null, 1, null, null);

        for (int i = 0; i<=10; i++){
            Book book = new Book(new Recipe[]{r1,r2});
            book.setName("Title");
            addBook(book);
        }
         //*/

    }


    public void addBook(Book book){
        LayoutBooks layoutBooks = new LayoutBooks(getContext(), book);
        linearLayout.addView(layoutBooks);
    }
}
