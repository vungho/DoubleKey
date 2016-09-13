package com.example.vungho.mykeyalpha20.Control;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import java.util.ArrayList;

/**
 * Created by vungho on 07/04/2016.
 */
public class AppControl {

    private ArrayList<String> listPackage;
    private Context context;
    private PackageManager packageManager;

    public AppControl(ArrayList<String> listPackage, Context context) {
        this.listPackage = listPackage;
        this.context = context;
        packageManager = context.getApplicationContext().getPackageManager();
    }

    public void disableApp(){

        for (String s: listPackage){
            packageManager.setComponentEnabledSetting(new ComponentName(s, s+".MainActivity"), //can be MainActivity
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
    }

    public void enableApp(){
       for(String s: listPackage){
           packageManager.setComponentEnabledSetting(new ComponentName(s, s+".MaintActivity"), //can be MainActivity
                   PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
       }
    }
}
