package com.hwr.cookbook;

import android.provider.ContactsContract;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.github.tibolte.agendacalendarview.models.WeekItem;
import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Sidney on 22.03.2018.
 */

public class RecipeMarker implements CalendarEvent{

    @Exclude
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");

    public String recipeId;
    public int persons = 0;
    public String date;
    @Exclude
    private Calendar calendar;
    public String name;
    public String id;


    public RecipeMarker () {}

    public RecipeMarker (String recipeId, int persons, Calendar calendar) {
        id = UUID.randomUUID().toString();
        this.recipeId = recipeId;
        this.persons = persons;
        this.calendar = calendar;
    }

    @Exclude
    public String getName() { return name; }

    @Exclude
    public void setName(String name) { this.name = name; }

    @Exclude
    @Override
    public long getId() {
        return 0;
    }

    @Exclude
    @Override
    public void setId(long mId) {

    }

    @Exclude
    @Override
    public Calendar getStartTime() {
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(date);
        return calendar;
    }

    @Exclude
    @Override
    public void setStartTime(Calendar mStartTime) {

    }

    @Exclude
    @Override
    public Calendar getEndTime() {
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(date);
        return calendar;
    }

    @Exclude
    @Override
    public void setEndTime(Calendar mEndTime) {

    }

    @Exclude
    @Override
    public String getTitle() {
        return null;
    }

    @Exclude
    @Override
    public void setTitle(String mTitle) {

    }

    @Exclude
    @Override
    public Calendar getInstanceDay() {
        return null;
    }

    @Exclude
    @Override
    public void setInstanceDay(Calendar mInstanceDay) {

    }

    @Exclude
    @Override
    public DayItem getDayReference() {
        return null;
    }

    @Exclude
    @Override
    public void setDayReference(DayItem mDayReference) {

    }

    @Exclude
    @Override
    public WeekItem getWeekReference() {
        return null;
    }

    @Exclude
    @Override
    public void setWeekReference(WeekItem mWeekReference) {

    }

    //@Exclude
    @Override
    public CalendarEvent copy() {
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(date);
        String title = toString();
        // using the description and location fields of BaseCalendarEvent for these non-intended purposes is quite dirty!
        String description = id;
        String location = persons + " persons";
        int color = R.color.primary_dark;
        Calendar startTime = calendar;
        long dates = calendar.getTimeInMillis();
        long dateStart = calendar.getTimeInMillis();
        Calendar endTime = calendar;
        long dateEnd = calendar.getTimeInMillis();
        int allDay = 1;
        String duration = "";
        //return new BaseCalendarEvent(title, description, location, color,  startTime,  endTime, allDay);
        return new BaseCalendarEvent(0, color, title, description, location, dateStart, dateEnd, allDay, duration);
    }

    //@Exclude
    public String toString() {
        return name;
    }

    @Exclude
    public Calendar getCalendar() {
        return calendar;
    }

    @Exclude
    public void setCalendar(Calendar calendar) {
        date = sdf.format(calendar.getTime());
        this.calendar = calendar;
    }

    //@Exclude
    public void refreshCalendarByDate() {
        calendar = Calendar.getInstance();
        Date tempDate = null;
        try {
            tempDate = sdf.parse(date);
        } catch (ParseException e) {
            Log.d("Marker", "Could not parse "+date+" into Date format");
        }
        calendar.setTime(tempDate);

    }
}
