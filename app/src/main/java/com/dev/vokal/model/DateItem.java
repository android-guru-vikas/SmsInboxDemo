package com.dev.vokal.model;

public class DateItem extends ListItem {

    private int date;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }
}