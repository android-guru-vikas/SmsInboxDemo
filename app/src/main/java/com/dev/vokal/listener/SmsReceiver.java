package com.dev.vokal.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.dev.vokal.utils.Constants;

public class SmsReceiver extends BroadcastReceiver {

    String smsSender = "";
    String smsBody = "";

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            smsSender = smsMessage.getDisplayOriginatingAddress();
            smsBody += smsMessage.getMessageBody();
        }
        Intent smsIntent = new Intent(Constants.TAG_INCOMING_SMS);
        smsIntent.putExtra(Constants.TAG_SMS, smsBody);
        context.sendBroadcast(smsIntent);
        NotificationHelper helper = new NotificationHelper(context);
        helper.createNotification(smsSender, smsBody);
    }
}
