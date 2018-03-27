package com.hwr.cookbook.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hwr.cookbook.R;

/**
 * Created by Thomas on 27.03.2018.
 */

public class LayoutBookmarksRecipe extends RelativeLayout {
    TextView textView;
    RatingBar ratingBar;


    public LayoutBookmarksRecipe(Context context) {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int margin = getContext().getResources().getInteger(R.integer.marginRecipes);
        params.setMargins(margin, margin, margin, margin);

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Dialog or Activity instead
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //add name, change later to a function add recipe object
    public void addName(String name){
        textView = new TextView(getContext());
        textView.setText(name);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        textView.setLayoutParams(params);

        this.addView(textView);
    }


    //add name, change later to a function add recipe object
    public void addRating(int rating) {
        ratingBar = new RatingBar(getContext());
        int maxRating = getContext().getResources().getInteger(R.integer.maxRating);
        ratingBar.setNumStars(maxRating);
        ratingBar.setRating(rating);
        ratingBar.setEnabled(false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        ratingBar.setLayoutParams(params);
        this.addView(ratingBar);
    }

}