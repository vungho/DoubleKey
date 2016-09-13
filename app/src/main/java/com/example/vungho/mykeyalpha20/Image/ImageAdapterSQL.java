package com.example.vungho.mykeyalpha20.Image;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Manager.ImageManager;
import com.example.vungho.mykeyalpha20.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by vungho on 31/05/2016.
 */
public class ImageAdapterSQL extends ArrayAdapter<ImageInfo>{

    private Context context;
    private int myLayout;
    private ArrayList<ImageInfo> list;


    public ImageAdapterSQL(Context context, int myLayout, ArrayList<ImageInfo> list) {
        super(context, myLayout, list);
        this.context = context;
        this.myLayout = myLayout;
        this.list = list;
    }

    static class ViewHolder {
        RelativeLayout topLayout;
        ImageView imageItem;
        ImageView imageTopView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);
        ImageInfo item = list.get(position);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.image_item);
        ImageView imageTopView = (ImageView)convertView.findViewById(R.id.image_topView);
        RelativeLayout imageTopLayout = (RelativeLayout)convertView.findViewById(R.id.image_topLayout);

        new LoadImageDatabaseAsyncTask(context, imageView).execute(item.getStringPath());

        if (item.isTouchStatut()){ //Da duoc click
            Picasso.with(context).load(R.drawable.ic_check).into( imageTopView);
            imageTopLayout.setBackgroundColor(Color.parseColor("#66af49"));
            imageTopLayout.setAlpha((float)0.5);
        }else {
            imageTopLayout.setBackgroundColor(Color.TRANSPARENT);
            imageTopView.setBackgroundColor(Color.TRANSPARENT);
        }


        return  convertView;

    }
    public void changeData(boolean statut, int position){
        ImageInfo imageInfo = list.get(position);
        list.remove(position);
        imageInfo.setTouchStatut(statut);
        list.add(position, imageInfo);
        notifyDataSetChanged();
    }


        /*
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.topLayout = (RelativeLayout)convertView.findViewById(R.id.image_topLayout);
        viewHolder.imageItem = (ImageView)convertView.findViewById(R.id.image_item);
        viewHolder.imageTopView = (ImageView)convertView.findViewById(R.id.image_topView);



        viewHolder.imageItem.setImageBitmap(ImageManager.convertIntoBitmap(infoDB.getAvatar()));


        if (infoDB.isTouchStatut()){ //Da duoc click
            Picasso.with(context).load(R.drawable.ic_check).into( viewHolder.imageTopView);
            viewHolder.topLayout.setBackgroundColor(Color.parseColor("#66af49"));
            viewHolder.topLayout.setAlpha((float)0.5);
        }else {
            viewHolder.imageTopView.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.topLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        convertView.setTag(viewHolder);
        */
}
