package com.example.vungho.mykeyalpha20.AsyncTaskManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Manager.ImageManager;
import com.example.vungho.mykeyalpha20.R;
import com.example.vungho.mykeyalpha20.Video.VideoInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 15/06/2016.
 */
public class VideoDelDB extends AsyncTask<ArrayList<VideoInfo>, Integer, Void> {

    private DataBase dataBase;
    private Context context;
    private ProgressDialog dialog;
    private ArrayList<VideoInfo> list;
    private ChangeName changeName;

    public VideoDelDB(DataBase dataBase, Context context){
        this.dataBase = dataBase;
        this.context = context;
        this.changeName = new ChangeName();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, context.getString(R.string.video_process),
                context.getString(R.string.process_mess), true);
    }

    @Override
    protected Void doInBackground(ArrayList<VideoInfo>... params) {

        list = params[0];
        int sizeList = list.size();
        for (int i=0; i<sizeList; i++){
            VideoInfo item = list.get(i);
            changeName.recoveryPath(item.getNewPath(), item.getStringPath());
            dataBase.queryData("delete from video where path = '" + list.get(i).getStringPath() + "'");
            publishProgress(i);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int sizeList = list.size();

        dialog.setMessage(context.getString(R.string.process_mess)+"\n"
                +context.getString(R.string.process_label) +": " + values[0]+"/"+sizeList);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
    }

}
