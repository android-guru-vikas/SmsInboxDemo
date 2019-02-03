package com.dev.vokal.model;

public class GeneralItem extends ListItem {

    private SmsDataModel smsDataModel;

    public SmsDataModel getSmsDataModel() {
        return smsDataModel;
    }

    public void setSmsDataModel(SmsDataModel smsDataModel) {
        this.smsDataModel = smsDataModel;
    }

    @Override
    public int getType() {
        return TYPE_GENERAL;
    }

}
