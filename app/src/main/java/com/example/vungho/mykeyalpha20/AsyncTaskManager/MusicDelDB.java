package com.example.vungho.mykeyalpha20.AsyncTaskManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Music.MusicInfo;
import com.example.vungho.mykeyalpha20.R;

import java.util.ArrayList;

/**
 * Created by vungho on 15/06/2016.
 */
public class MusicDelDB extends AsyncTask<ArrayList<MusicInfo>, Integer, Void> {
    private DataBase dataBase;
    private Context context;
    private ProgressDialog dialog;
    private ArrayList<MusicInfo> list;
    private ChangeName changeName;

    public MusicDelDB(DataBase dataBase, Context context){
        this.dataBase = dataBase;
        this.context = context;
        changeName = new ChangeName();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, context.getString(R.string.music_process),
                context.getString(R.string.process_mess), true);
    }

    @Override
    protected Void doInBackground(ArrayList<MusicInfo>... params) {

        list = params[0];
        int sizeList = list.size();
        for (int i=0; i<sizeList; i++){
            MusicInfo item = list.get(i);
            changeName.recoveryPath(item.getNewPath(), item.getPath());
            dataBase.queryData("delete from music where path = '" + item.getPath() + "'");
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
