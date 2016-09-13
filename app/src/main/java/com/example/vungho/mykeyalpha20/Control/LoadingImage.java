package com.example.vungho.mykeyalpha20.Control;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 28/06/2016.
 */
public class LoadingImage extends AsyncTask<Void, Void, ArrayList<ImageInfo>> {

    private Context context;
    private ArrayList<ImageInfo> list;

    public LoadingImage(Context context, ArrayList<ImageInfo> list){
        this.context = context;
        this.list = list;
    }


    @Override
    protected ArrayList<ImageInfo> doInBackground(Void... params) {
        Cursor cursor = new DataBase(context).
                getData("select path, newpath from image");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ImageInfo item = new ImageInfo();
            item.setStringPath(cursor.getString(0));
            item.setNewPath(cursor.getString(1));
            list.add(item);
            cursor.moveToNext();
        }
        return null;
    }
}
