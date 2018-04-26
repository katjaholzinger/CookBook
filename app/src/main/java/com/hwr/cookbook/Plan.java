package com.hwr.cookbook;

import android.provider.CalendarContract;

import com.github.tibolte.agendacalendarview.models.CalendarEvent;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Plan extends IdentifiableElement {

    public ArrayList<CalendarEvent> Markers;

    public Plan () {
        super();
    }

    public Plan (ArrayList<CalendarEvent> Markers) {
        super();
        this.Markers = Markers;
    }

}
