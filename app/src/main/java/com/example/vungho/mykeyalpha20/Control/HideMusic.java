package com.example.vungho.mykeyalpha20.Control;

import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;
import com.example.vungho.mykeyalpha20.Music.MusicInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 28/06/2016.
 */
public class HideMusic extends AsyncTask<ArrayList<MusicInfo>, Void, Void> {

    private ChangeName changeName;
    public HideMusic() {
        changeName = new ChangeName();
    }

    @Override
    protected Void doInBackground(ArrayList<MusicInfo>... params) {
        ArrayList<MusicInfo> list = params[0];
        for (MusicInfo item : list){
            changeName.changePath(item.getPath(), item.getNewPath());
        }
        return null;
    }
}
