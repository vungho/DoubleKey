package com.example.vungho.mykeyalpha20.Control;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by vungho on 04/07/2016.
 */
public class RedLog {

    public RedLog() {
    }

    public void read(Context context) {

        try {
            Process mLogcatProc = null;
            BufferedReader reader = null;
            mLogcatProc = Runtime.getRuntime().exec(new String[]{"logcat", "-d"});

            reader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));

            String line;
            final StringBuilder log = new StringBuilder();
            String separator = System.getProperty("line.separator");

            while ((line = reader.readLine()) != null) {
                log.append(line);
                log.append(separator);
            }
            String w = log.toString();
            Toast.makeText(context, w, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
