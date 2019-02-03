package com.dev.vokal.utils;

public class Constants {
    public static final String KEY_SMS_URI = "content://sms/inbox";
    public static final String KEY_SMS_ID = "_id";
    public static final String KEY_SMS_BODY = "body";
    public static final String KEY_SMS_ADDRESS = "address";
    public static final String KEY_SMS_DATE = "date";
    public static final String KEY_QUERY_DATE = "datetime(date/1000, 'unixepoch') between date('now', '-1 day') and date('now')";
    public static final String KEY_FONT_TYPE = "DROIDSERIF.ttf";
    public static final String TAG_INCOMING_SMS = "incomingsms";
    public static final String TAG_SMS = "sms";
    public static final String KEY_CHANNEL_ID = "com.dev.vokal.sms.channel";
    public static final String KEY_CHANNEL_NAME = "Sms Channel";
    public static final String KEY_PACKAGE_NAME = "package";
}
