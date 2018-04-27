package com.hwr.cookbook;

import android.provider.CalendarContract;

import com.github.tibolte.agendacalendarview.models.CalendarEvent;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Plan extends IdentifiableElement {

    public ArrayList<RecipeMarker> Markers;

    public Plan () {
        super();
    }

    public Plan (ArrayList<RecipeMarker> Markers) {
        super();
        this.Markers = Markers;
    }

}
