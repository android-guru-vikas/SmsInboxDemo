package com.dev.vokal.utils;

import android.os.Build;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AppLogger {
    private static File logFile;
    private String TAG = AppLogger.class.getSimpleName();
}
