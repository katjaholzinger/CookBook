package com.hwr.cookbook;

import android.provider.ContactsContract;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.github.tibolte.agendacalendarview.models.WeekItem;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Sidney on 22.03.2018.
 */

public class RecipeMarker implements CalendarEvent{

    public String recipeId;
    public int persons;
    public Calendar calendar;
    public String name;
    private long ID;

    public long getID() { return ID; }

    public void setID(long id) {
        this.ID = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public RecipeMarker () {}

    public RecipeMarker (String recipeId, int persons, Calendar calendar) {
        ID = System.currentTimeMillis();
        this.recipeId = recipeId;
        this.persons = persons;
        this.calendar = calendar;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long mId) {

    }

    @Override
    public Calendar getStartTime() {
        return calendar;
    }

    @Override
    public void setStartTime(Calendar mStartTime) {

    }

    @Override
    public Calendar getEndTime() {
        return calendar;
    }

    @Override
    public void setEndTime(Calendar mEndTime) {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String mTitle) {

    }

    @Override
    public Calendar getInstanceDay() {
        return null;
    }

    @Override
    public void setInstanceDay(Calendar mInstanceDay) {

    }

    @Override
    public DayItem getDayReference() {
        return null;
    }

    @Override
    public void setDayReference(DayItem mDayReference) {

    }

    @Override
    public WeekItem getWeekReference() {
        return null;
    }

    @Override
    public void setWeekReference(WeekItem mWeekReference) {

    }

    @Override
    public CalendarEvent copy() {
        String title = toString();
        String description = "Description";
        String location = persons + " persons";
        int color = R.color.primary_dark;
        Calendar startTime = calendar;
        long dateStart = calendar.getTimeInMillis();
        Calendar endTime = calendar;
        long dateEnd = calendar.getTimeInMillis();
        int allDay = 1;
        String duration = "";
        //return new BaseCalendarEvent(title, description, location, color,  startTime,  endTime, allDay);
        return new BaseCalendarEvent(ID, color, title, description, location, dateStart, dateEnd, allDay, duration);
    }

    public String toString() {
        if (Database.findRecipe(recipeId) != null) {
            return Database.findRecipe(recipeId).name;
        }
        return "";
    }
}
