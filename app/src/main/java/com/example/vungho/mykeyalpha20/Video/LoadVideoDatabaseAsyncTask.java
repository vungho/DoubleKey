package com.example.vungho.mykeyalpha20.Video;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Manager.ImageManager;

import java.lang.ref.WeakReference;

/**
 * Created by vungho on 13/06/2016.
 */
public class LoadVideoDatabaseAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private Context context;
    private final WeakReference<ImageView> imageViewWeakReference;

    public LoadVideoDatabaseAsyncTask(Context context, ImageView imageView) {
        this.context = context;
        imageViewWeakReference =  new WeakReference<ImageView>(imageView);
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        String uri = params[0];
        Cursor cursor = new DataBase(context).
                getData("select avatar from video where path = '" +uri +"'");
        cursor.moveToFirst();
        byte[] arr = cursor.getBlob(0);
        cursor.close();
        return ImageManager.convertIntoBitmap(arr);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);
        if (imageViewWeakReference != null && bitmap != null){
            ImageView imageView = imageViewWeakReference.get();
            if (imageView != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
