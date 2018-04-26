package com.hwr.cookbook.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Thomas on 18.03.2018.
 */

public class FragmentBookmarks extends Fragment {
    private LinearLayout linearLayout;
    private User user;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<Recipe>> expandableListDetail;


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

        createFloatActionButton();

        createExpandableList();
    }

    private void createFloatActionButton() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), RecipeActivity.class);
                RecipeActivity.recipe=null;
                getContext().startActivity(intent);
                            }
        });
    }


    private void createExpandableList () {
        expandableListView = (ExpandableListView) getActivity().findViewById(R.id.expandableListView);

        MainActivity mainActivity = (MainActivity)getActivity();
        expandableListDetail = ExpandableListDataPump.getData(mainActivity.bookList);

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new BooksExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {}
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                RecipeActivity.recipe = expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition);
                getActivity().startActivity(intent);

                return false;

            }
        });
    }


    public void updateList() {
        expandableListView.
    }
}
