package com.hwr.cookbook.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
    private AgendaCalendarView acview = null;
    private Calendar minDate;
    private Calendar maxDate;
    public Plan plan;


    public FragmentPlaner() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        plan = Database.getPlan();

        minDate = Calendar.getInstance();
        maxDate = Calendar.getInstance();
        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.DAY_OF_MONTH, -3);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.WEEK_OF_MONTH, 3);

        View view = inflater.inflate(R.layout.fragment_planer, container, false);
        acview = view.findViewById(R.id.agenda_calendar_view);
        acview.init(makeCalendarEventList(plan), minDate, maxDate, Locale.getDefault(), this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        plan = Database.getPlan();
        acview.init(makeCalendarEventList(plan), minDate, maxDate, Locale.getDefault(), this);
    }

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {

        BaseCalendarEvent baseCalendarEvent = (BaseCalendarEvent) event;

        if (baseCalendarEvent.getDescription() == null) {
            //new marker

            Intent intent = new Intent(getActivity(), EventActivity.class);
            EventActivity.marker = new RecipeMarker(null, 1, baseCalendarEvent.getInstanceDay());
            getActivity().startActivity(intent);

        } else {
            //show marker, editable
            RecipeMarker marker = new RecipeMarker(null, 1, baseCalendarEvent.getInstanceDay());
            for (RecipeMarker recipeMarker: Database.getPlan().markers) {
                if (recipeMarker.id.equals(baseCalendarEvent.getDescription())) {

                    //EventActivity.marker = recipeMarker;
                    marker = recipeMarker;
                    break;
                }
            }

            final RecipeMarker recipeMarker = marker;
            //new DialogEventOptions(FragmentPlaner.this, marker);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle(getActivity().getResources().getString(R.string.EventOptions));
            alertDialogBuilder.setPositiveButton(R.string.deleteMarker,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Database.deleteMarker(recipeMarker);
                            refresh();

                        }
                    });
            alertDialogBuilder.setNegativeButton(R.string.editMarker,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getActivity(), EventActivity.class);
                            EventActivity.marker = recipeMarker;
                            getActivity().startActivity(intent);
                        }
                    });
            alertDialogBuilder.setNeutralButton(R.string.showRecipe,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getActivity(), RecipeActivity.class);
                            RecipeActivity.recipe = Database.findRecipe(recipeMarker.recipeId);
                            intent.putExtra("PORTIONS", recipeMarker.getPersons());
                            getActivity().startActivity(intent);
                        }
                    });

            alertDialogBuilder.create().show();

            //getActivity().startActivity(intent);

        }

    }

    @Override
    public void onScrollToDate(Calendar calendar) {

    }

    private void mockPlan(Plan plan) {

        plan.markers = new ArrayList<RecipeMarker>();


        ArrayList<Book> books = TestBook.generateTestBook(false);
        Calendar today = Calendar.getInstance();
        Book book = null;
        if (books.get(0).name.equals("Eingang")) {
            book = books.get(1);
        } else {
            book = books.get(0);
        }
        RecipeMarker marker1 = new RecipeMarker ( book.getFullRecipes().get(0).id, 5, today);
        plan.markers.add(marker1);
        //Database.setNewMarkerInPlan(FirebaseAuth.getInstance().getCurrentUser().getUid(), plan.id, marker1);

        Calendar newCal = Calendar.getInstance();
        newCal.add(Calendar.DAY_OF_MONTH, 1);
        RecipeMarker marker2 = new RecipeMarker(book.getFullRecipes().get(1).id, 5, newCal);
        plan.markers.add(marker2);


    }

    private ArrayList<CalendarEvent> makeCalendarEventList(Plan plan) {

        ArrayList<CalendarEvent> eventlist = new ArrayList<>();
        //ArrayList<RecipeMarker> markerList = new ArrayList<RecipeMarker>();
        if (plan != null) {
            if (plan.markers != null && plan.markers.size() > 0) {
                    for (RecipeMarker marker : plan.markers) {
                    marker.refreshCalendarByDate();
                    eventlist.add(marker);
                }
            }
        }
        return eventlist;
    }
}
