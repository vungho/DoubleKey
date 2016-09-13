package com.example.vungho.mykeyalpha20.AsyncTaskManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;
import com.example.vungho.mykeyalpha20.Manager.ImageManager;
import com.example.vungho.mykeyalpha20.R;

import java.util.ArrayList;

/**
 * Created by vungho on 15/06/2016.
 */
public class ImageInsertDB extends AsyncTask<ArrayList<ImageInfo>, Integer, Void> {

    private DataBase dataBase;
    private Context context;
    private ProgressDialog dialog;
    private ArrayList<ImageInfo> list;
    private ChangeName changeName;
    public ImageInsertDB(DataBase dataBase, Context context){
        this.dataBase = dataBase;
        this.context = context;
        changeName = new ChangeName();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, context.getString(R.string.image_process),
                context.getString(R.string.process_mess), true);
    }

    @Override
    protected Void doInBackground(ArrayList<ImageInfo>... params) {

        list = params[0];
        int sizeList = list.size();
        for (int i=0; i<sizeList; i++){
            try{
                ImageInfo item = list.get(i);
                byte[] avarta = ImageManager.convertImageToArr(item.getStringPath());
                String newPath = changeName.changePathPlus(item.getStringPath());

                dataBase.insertImageIoDataBase(item.getStringPath(), newPath, item.getName(), avarta);
                publishProgress(i);
            }catch (Exception ex){
                ex.printStackTrace();
            }
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
