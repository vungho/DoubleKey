package com.example.vungho.mykeyalpha20.Application;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.vungho.mykeyalpha20.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vungho on 13/06/2016.
 */
public class LoadAppInfoAsyncTask extends AsyncTask<PackageManager, Void, ArrayList<AppInfo>> {
    private ArrayList<AppInfo> list;
    private Context context;
    private ProgressDialog dialog;


    public LoadAppInfoAsyncTask(ArrayList<AppInfo> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, context.getString(R.string.load_app), context.getString(R.string.load_app_mess), true);

    }

    @Override
    protected  ArrayList<AppInfo> doInBackground(PackageManager... params) {
        PackageManager packageManager = params[0];
        final List<ApplicationInfo> listApp = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);


        for (ApplicationInfo a: listApp){
            AppInfo item = new AppInfo();
            item.setName(a.loadLabel(packageManager).toString());
            item.setName(a.loadLabel(packageManager).toString());
            item.setPackageName(a.packageName.toString());
            list.add(item);

        }
        Collections.sort(list, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo lhs, AppInfo rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        return list;
    }

    @Override
    protected void onPostExecute( ArrayList<AppInfo> list) {
        super.onPostExecute(list);
        dialog.dismiss();
    }
}
