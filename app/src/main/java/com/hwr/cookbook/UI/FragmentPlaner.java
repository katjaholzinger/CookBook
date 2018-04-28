package com.hwr.cookbook.UI;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.Plan;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.RecipeMarker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Thomas on 18.03.2018.
 */

public class FragmentPlaner extends Fragment implements CalendarPickerController {


    public FragmentPlaner() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Plan plan = Database.getPlan();
        /*if (plan != null){

            Log.d("FragmentPlaner", plan.name);
            if (plan.Markers == null) {
                mockPlan(plan);
            }
        } else {
            plan = new Plan();
            mockPlan(plan);
        }

        */

        ArrayList<CalendarEvent> eventlist = new ArrayList<CalendarEvent>();
        if (plan == null) {

        } else {
            for (RecipeMarker marker : plan.Markers) {
                eventlist.add(marker);
            }
        }


        // Inflate the layout for this fragment

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.DAY_OF_MONTH, -3);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.WEEK_OF_MONTH, 3);

                View view = inflater.inflate(R.layout.fragment_planer, container, false);
        AgendaCalendarView acview = view.findViewById(R.id.agenda_calendar_view);
        acview.init( eventlist, minDate, maxDate, Locale.getDefault(), this);

        return view;
    }

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {

        Intent intent = new Intent(getActivity(), EventActivity.class);
        if (event.getId() == 0) {
            //new marker
            Calendar temp = event.getInstanceDay();
            EventActivity.marker = new RecipeMarker(null, 1, event.getInstanceDay());

        } else {
            //show marker, editable

            for (RecipeMarker rm: Database.getPlan().Markers) {
                if (rm.getID() == event.getId()) {

                    EventActivity.marker = rm;
                    break;
                }
            }



        }

        getActivity().startActivity(intent);

    }

    @Override
    public void onScrollToDate(Calendar calendar) {

    }

    private void mockPlan(Plan plan) {

        plan.Markers = new ArrayList<RecipeMarker>();


        ArrayList<Book> books = TestBook.generateTestBook(false);
        Calendar today = Calendar.getInstance();
        Book book = null;
        if (books.get(0).name.equals("Eingang")) {
            book = books.get(1);
        }
        else {
            book = books.get(0);
        }
        RecipeMarker marker1 = new RecipeMarker ( book.getFullRecipes().get(0).id, 5, today);
        plan.Markers.add(marker1);

        Calendar newCal = Calendar.getInstance();
        newCal.add(Calendar.DAY_OF_MONTH, 1);
        RecipeMarker marker2 = new RecipeMarker( book.getFullRecipes().get(1).id, 5, newCal);
        plan.Markers.add(marker2);


    }
}
