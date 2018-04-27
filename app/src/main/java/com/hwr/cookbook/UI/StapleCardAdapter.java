package com.hwr.cookbook.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;

public class StapleCardAdapter extends ArrayAdapter<Recipe> {

    public StapleCardAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        ViewHolder holder;

        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            contentView = inflater.inflate(R.layout.recipe_card, parent, false);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        Recipe recipe = getItem(position);

        holder.name.setText(recipe.name);
        holder.rating.setRating(recipe.rating);

        return contentView;
    }

    private static class ViewHolder {
        public TextView name;
        public RatingBar rating;
        public ImageView image;

        public ViewHolder(View view) {
            this.name = view.findViewById(R.id.recipe_card_name);
            this.rating = view.findViewById(R.id.recipe_card_rating);
        }
    }

}