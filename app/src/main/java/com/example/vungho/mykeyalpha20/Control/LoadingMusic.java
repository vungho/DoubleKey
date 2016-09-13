package com.example.vungho.mykeyalpha20.Control;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;
import com.example.vungho.mykeyalpha20.Music.MusicInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 28/06/2016.
 */
public class LoadingMusic extends AsyncTask<Void, Void, ArrayList<MusicInfo>> {

    private Context context;
    private ArrayList<MusicInfo> list;

    public LoadingMusic(Context context, ArrayList<MusicInfo> list){
        this.context = context;
        this.list = list;
    }


    @Override
    protected ArrayList<MusicInfo> doInBackground(Void... params) {
        Cursor cursor = new DataBase(context).
                getData("select path, newpath from music");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            MusicInfo item = new MusicInfo();
            item.setPath(cursor.getString(0));
            item.setNewPath(cursor.getString(1));
            list.add(item);
            cursor.moveToNext();
        }
        return null;
    }
}
