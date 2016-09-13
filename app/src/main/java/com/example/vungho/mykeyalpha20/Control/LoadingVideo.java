package com.example.vungho.mykeyalpha20.Control;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;
import com.example.vungho.mykeyalpha20.Video.VideoInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 28/06/2016.
 */
public class LoadingVideo extends AsyncTask<Void, Void, ArrayList<VideoInfo>> {

    private Context context;
    private ArrayList<VideoInfo> list;

    public LoadingVideo(Context context, ArrayList<VideoInfo> list){
        this.context = context;
        this.list = list;
    }


    @Override
    protected ArrayList<VideoInfo> doInBackground(Void... params) {
        Cursor cursor = new DataBase(context).
                getData("select path, newpath from video");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            VideoInfo item = new VideoInfo();
            item.setStringPath(cursor.getString(0));
            item.setNewPath(cursor.getString(1));
            list.add(item);
            cursor.moveToNext();
        }
        return null;
    }
}
