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
    private String ID;
    public ArrayList<RecipeMarker> Markers;



    public Plan () {
    }

    public Plan (ArrayList<RecipeMarker> Markers) {
        super();
        this.Markers = Markers;
    }

    public String getID() { return ID; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setID(String id) {
        this.ID = id;
    }

}
