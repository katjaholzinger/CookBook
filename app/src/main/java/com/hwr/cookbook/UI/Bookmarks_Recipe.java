package com.hwr.cookbook.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwr.cookbook.R;

/**
 * Created by Thomas on 27.03.2018.
 */

public class Bookmarks_Recipe extends RelativeLayout {
    TextView textView;
    RatingBar ratingBar;


    public Bookmarks_Recipe(Context context) {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int margin = getContext().getResources().getInteger(R.integer.marginRecipes);
        params.setMargins(margin, margin, margin, margin);
    }

    public void addName(String name){
        textView = new TextView(getContext());
        textView.setText(name);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        textView.setLayoutParams(params);

        this.addView(textView);
    }

    public void addRating(int rating) {
        ratingBar = new RatingBar(getContext());
        int maxRating = getContext().getResources().getInteger(R.integer.maxRating);
        ratingBar.setNumStars(maxRating);
        ratingBar.setRating(rating);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        ratingBar.setLayoutParams(params);

        this.addView(ratingBar);
    }

}
