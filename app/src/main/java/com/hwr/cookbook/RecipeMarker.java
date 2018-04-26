package com.hwr.cookbook;

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

    public Recipe recipe;
    public int persons;
    public Calendar calendar;
    public String name;
    private String ID;

    public String getID() { return ID; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setID(String id) {
        this.ID = id;
    }

    public RecipeMarker () {}

    public RecipeMarker (Recipe recipe, int persons, Calendar calendar) {
        ID = UUID.randomUUID().toString();
        this.recipe = recipe;
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
        String title = recipe.getName();
        String description = "Description";
        String location = persons + " persons";
        int color = R.color.primary_dark;
        Calendar startTime = calendar;
        Calendar endTime = calendar;
        boolean allDay = true;
        return new BaseCalendarEvent(title, description, location, color,  startTime,  endTime, allDay);
    }
}
