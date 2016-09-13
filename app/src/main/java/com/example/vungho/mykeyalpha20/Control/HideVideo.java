package com.example.vungho.mykeyalpha20.Control;

import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;
import com.example.vungho.mykeyalpha20.Video.VideoInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 28/06/2016.
 */
public class HideVideo extends AsyncTask<ArrayList<VideoInfo>, Void, Void> {

    private ChangeName changeName;
    public HideVideo() {
        changeName = new ChangeName();
    }

    @Override
    protected Void doInBackground(ArrayList<VideoInfo>... params) {
        ArrayList<VideoInfo> list = params[0];
        for (VideoInfo item : list){
            changeName.changePath(item.getStringPath(), item.getNewPath());
        }
        return null;
    }
}
