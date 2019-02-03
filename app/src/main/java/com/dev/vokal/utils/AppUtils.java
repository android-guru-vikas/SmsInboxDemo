package com.dev.vokal.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.dev.vokal.R;
import com.dev.vokal.application.VokalApplication;
import com.dev.vokal.model.SmsDataModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public final class AppUtils {
    private static AppUtils instance;

    private AppUtils() {

    }

    public static AppUtils getInstance() {
        if (instance == null) {
            synchronized (AppUtils.class) {
                if (instance == null) {
                    instance = new AppUtils();
                }
            }
        }
        return instance;
    }

    public Object getValueFromData(Object data) {
        return (data == null) ? "" : capitalizeString(data.toString());
    }

    private String capitalizeString(String s) {
        if (s != null && s.length() > 0) {
            return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
        } else
            return s;
    }

    public void createAndShowDialog(Context context) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.permission_never_ask_again)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> openAppPermissionSettings(context))
                .setNegativeButton(R.string.button_deny, (DialogInterface dialog, int button) -> {
                    dialog.cancel();
                    System.exit(0);
                })
                .show();
    }

    private void openAppPermissionSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts(Constants.KEY_PACKAGE_NAME, VokalApplication.getAppContext().getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    private int getTimeDifference(long time) {
        int timeAgo = 0;
        try {
            Date date = new Date(time);
            Date currentDate = new Date();
            long difference = currentDate.getTime() - date.getTime();
            long seconds = difference / 1000;
            long minutes = seconds / 60;
            long hour = minutes / 60;
            timeAgo = (int) hour;
        } catch (Exception e) {
            Log.d("TAG", "Current time ex : " + e.getMessage());
        }
        return timeAgo;
    }

    public TreeMap<Integer, List<SmsDataModel>> getHourDataFromSmsList(List<SmsDataModel> smsList) {
        TreeMap<Integer, List<SmsDataModel>> map = new TreeMap<>();
        for (SmsDataModel model : smsList) {
            int timeDiff = getTimeDifference(model.getDate());
            if (-1 < timeDiff && timeDiff < 25) {
                List<SmsDataModel> itemsList = map.get(timeDiff);
                if (itemsList == null) {
                    itemsList = new ArrayList<>();
                    itemsList.add(model);
                    map.put(timeDiff, itemsList);
                } else {
                    if (!itemsList.contains(model)) {
                        itemsList.add(model);
                    }
                }
            }
        }
        return map;
    }

}