package com.example.vungho.mykeyalpha20.Control;

import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 28/06/2016.
 */
public class HideImage extends AsyncTask<ArrayList<ImageInfo>, Void, Void> {

    private ChangeName changeName;
    public HideImage() {
        changeName = new ChangeName();
    }

    @Override
    protected Void doInBackground(ArrayList<ImageInfo>... params) {
        ArrayList<ImageInfo> list = params[0];
        for (ImageInfo item : list){
            changeName.changePath(item.getStringPath(), item.getNewPath());
        }
        return null;
    }
}
