package com.hwr.cookbook.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.Plan;
import com.hwr.cookbook.R;
import com.hwr.cookbook.RecipeMarker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Thomas on 18.03.2018.
 */

public class FragmentPlaner extends Fragment implements CalendarPickerController {

    private Database db;

    public FragmentPlaner() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        db = new Database();
        Database.newListener();

        Plan plan = Database.getPlan();
        if (plan.Markers == null) {
            mockPlan(plan);
        }

        // Inflate the layout for this fragment

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.WEEK_OF_MONTH, -1);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.MONTH, 2);

        View view = inflater.inflate(R.layout.fragment_planer, container, false);
        AgendaCalendarView acview = view.findViewById(R.id.agenda_calendar_view);
        acview.init(plan.Markers, minDate, maxDate, Locale.getDefault(), this);

        return view;
    }

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        if (event.getTitle().equals("No events")) {
            //new marker

        } else {
            //show marker, editable

        Intent intent = new Intent(getActivity(), EventActivity.class);
        getActivity().startActivity(intent);


        }
    }

    @Override
    public void onScrollToDate(Calendar calendar) {

    }

    private void mockPlan(Plan plan) {

        plan.Markers = new ArrayList<CalendarEvent>();
        Book book = TestBook.generateTestBook().get(0);
        Calendar today = Calendar.getInstance();
        RecipeMarker marker1 = new RecipeMarker ( book.getRecipes()[0], 5, today);
        plan.Markers.add(marker1);

        Calendar newCal = Calendar.getInstance();
        newCal.add(Calendar.DAY_OF_MONTH, 1);
        RecipeMarker marker2 = new RecipeMarker( book.getRecipes()[1], 5, newCal);
        plan.Markers.add(marker2);

    }
}
