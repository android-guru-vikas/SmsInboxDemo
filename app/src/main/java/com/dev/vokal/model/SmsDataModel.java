package com.dev.vokal.model;

public class SmsDataModel implements Comparable<SmsDataModel> {

    private String number;
    private String body;
    private long date;
    private boolean shouldHighLight;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isShouldHighLight() {
        return shouldHighLight;
    }

    public void setShouldHighLight(boolean shouldHighLight) {
        this.shouldHighLight = shouldHighLight;
    }

    @Override
    public int compareTo(SmsDataModel o) {
        return Long.compare(this.date, o.date);
    }
}
