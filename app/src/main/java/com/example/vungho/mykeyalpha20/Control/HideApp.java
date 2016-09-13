package com.example.vungho.mykeyalpha20.Control;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.Application.AppInfo;
import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 28/06/2016.
 */
public class HideApp extends AsyncTask<ArrayList<AppInfo>, Void, Void> {

    private Context context;
    private PackageManager packageManager;

    public HideApp(Context context) {
        this.context = context;
        packageManager = context.getApplicationContext().getPackageManager();
    }

    @Override
    protected Void doInBackground(ArrayList<AppInfo>... params) {
        ArrayList<AppInfo> list = params[0];
        for (AppInfo item : list){
            String s = item.getPackageName();
            packageManager.setComponentEnabledSetting(new ComponentName(s, s+".MainActivity"), //can be MainActivity
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
        return null;
    }
}
