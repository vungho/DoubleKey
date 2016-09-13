package com.example.vungho.mykeyalpha20.Image;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.vungho.mykeyalpha20.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vungho on 17/03/2016.
 */
public class ImageAdapter extends ArrayAdapter<ImageInfo>{

    private Context context;
    private int imageLayout;
    private ArrayList<ImageInfo> arrImage;


    public ImageAdapter(Context context, int imageLayout, ArrayList<ImageInfo> arrImage) {
        super(context, imageLayout, arrImage);
        this.context = context;
        this.imageLayout = imageLayout;
        this.arrImage = arrImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View  view = View.inflate(context, imageLayout, null);

        RelativeLayout topLayout = (RelativeLayout)view.findViewById(R.id.image_topLayout);
        ImageView imageImageView = (ImageView)view.findViewById(R.id.image_item);
        ImageView imageTop = (ImageView)view.findViewById(R.id.image_topView);

        ImageInfo imageInfo = arrImage.get(position);

        Picasso.with(context).load("file://"+imageInfo.getStringPath()).centerCrop().fit().into(imageImageView);

        if (imageInfo.isTouchStatut()){ //Da duoc click
            Picasso.with(context).load(R.drawable.ic_check).into(imageTop);
            topLayout.setBackgroundColor(Color.parseColor("#66af49"));
            topLayout.setAlpha((float)0.5);
        }else {
            imageTop.setBackgroundColor(Color.TRANSPARENT);
            topLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;
    }

    public void changeData(boolean statut, int position){
        ImageInfo imageInfo = arrImage.get(position);
        arrImage.remove(position);
        imageInfo.setTouchStatut(statut);
        arrImage.add(position, imageInfo);
        notifyDataSetChanged();
    }

}
