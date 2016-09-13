package com.example.vungho.mykeyalpha20.Control;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.Application.AppInfo;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 28/06/2016.
 */
public class LoadingApp extends AsyncTask<Void, Void, ArrayList<AppInfo>> {

    private Context context;
    private ArrayList<AppInfo> list;

    public LoadingApp(Context context, ArrayList<AppInfo> list){
        this.context = context;
        this.list = list;
    }


    @Override
    protected ArrayList<AppInfo> doInBackground(Void... params) {
        Cursor cursor = new DataBase(context).
                getData("select packagename from app");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            AppInfo item = new AppInfo();
            item.setPackageName(cursor.getString(0));
            list.add(item);
            cursor.moveToNext();
        }
        return null;
    }
}
