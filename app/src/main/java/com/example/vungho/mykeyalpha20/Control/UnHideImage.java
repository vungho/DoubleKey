package com.example.vungho.mykeyalpha20.Control;

import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 28/06/2016.
 */
public class UnHideImage extends AsyncTask<ArrayList<ImageInfo>, Void, Void> {

    private ChangeName changeName;
    public UnHideImage(){
        changeName = new ChangeName();
    }

    @Override
    protected Void doInBackground(ArrayList<ImageInfo>... params) {
        ArrayList<ImageInfo> list = params[0];
        for (ImageInfo item: list){
            changeName.recoveryPath(item.getNewPath(), item.getStringPath());
        }
        return null;
    }
}
