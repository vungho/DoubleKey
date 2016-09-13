package com.example.vungho.mykeyalpha20.AsyncTaskManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Manager.ImageManager;
import com.example.vungho.mykeyalpha20.Music.MusicInfo;
import com.example.vungho.mykeyalpha20.R;
import com.example.vungho.mykeyalpha20.Video.VideoInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 15/06/2016.
 */
public class VideoInsertDB extends AsyncTask<ArrayList<VideoInfo>, Integer, Void> {

    private DataBase dataBase;
    private Context context;
    private ProgressDialog dialog;
    private ArrayList<VideoInfo> list;
    private ChangeName changeName;

    public VideoInsertDB(DataBase dataBase, Context context){
        this.dataBase = dataBase;
        this.context = context;
        changeName = new ChangeName();
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
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(item.getStringPath(),
                    MediaStore.Images.Thumbnails.MINI_KIND);
            if (bitmap == null){
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.videos_icon);
            }
            String newPath = changeName.changePathPlus(item.getStringPath());

            dataBase.insertVideoInfoToDataBase(item.getStringPath(), newPath,
                    item.getName(),
                    ImageManager.covertImageToArr(bitmap));
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
