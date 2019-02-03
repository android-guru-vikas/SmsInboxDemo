package com.dev.vokal.interfaces;

import android.content.Context;

public interface Toaster {
    public void show(Context context, String str);
    public void showLong(Context context, String str);
    public void showForTesting(Context context, String str);
}
