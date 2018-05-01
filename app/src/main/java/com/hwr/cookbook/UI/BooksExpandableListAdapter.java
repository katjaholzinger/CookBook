package com.hwr.cookbook.UI;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hwr.cookbook.Book;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;

public class BooksExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Book> expandableListTitle;
    private HashMap<Book, List<Recipe>> expandableListDetail;

    public BooksExpandableListAdapter(Context context, List<Book> expandableListTitle,
                                      HashMap<Book, List<Recipe>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Recipe getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Recipe expandedListRecipe = getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = convertView
                .findViewById(R.id.expandedListItemText);
        expandedListTextView.setText(expandedListRecipe.name);
        RatingBar expandedListRatingBar = convertView
                .findViewById(R.id.expandedListItemRating);
        expandedListRatingBar.setRating(expandedListRecipe.rating);
        expandedListRatingBar.setEnabled(false);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        Log.d("BookExpandableList", expandableListDetail.toString());
        try {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                    .size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }



    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Book titleBook = (Book) getGroup(listPosition);
        Integer size = 0;
        if (titleBook.getFullRecipes() != null){
            size = titleBook.getFullRecipes().size();
        }
        String listTitle = titleBook.name + " ("+size+")";
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}