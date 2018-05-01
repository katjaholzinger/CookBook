package com.hwr.cookbook;

import android.provider.CalendarContract;

import com.github.tibolte.agendacalendarview.models.CalendarEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Plan {

    public String name;
    public String id;
    public ArrayList<RecipeMarker> markers = new ArrayList<RecipeMarker>();



    public Plan () {
    }

    public Plan (ArrayList<RecipeMarker> markers) {
        this.markers = markers;
    }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

}
