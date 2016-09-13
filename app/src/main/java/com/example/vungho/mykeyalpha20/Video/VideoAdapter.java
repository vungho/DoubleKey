package com.example.vungho.mykeyalpha20.Video;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vungho.mykeyalpha20.R;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;

/**
 * Created by vungho on 20/03/2016.
 */
public class VideoAdapter extends ArrayAdapter<VideoInfo> {

    private Context context;
    private int myLayout;
    private ArrayList<VideoInfo> list;
    private Picasso picasso;
    private VideoRequestHandler videoRequestHandler;



    public VideoAdapter(Context context, int myLayout, ArrayList<VideoInfo> list) {
        super(context, myLayout, list);
        this.list = list;
        this.context = context;
        this.myLayout = myLayout;

        Picasso.Builder builder = new Picasso.Builder(context);
        videoRequestHandler = new VideoRequestHandler();
        builder.addRequestHandler(videoRequestHandler);
        picasso = builder.build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, myLayout, null);

        ImageView videoView = (ImageView)view.findViewById(R.id.videosImage_item);
        TextView videosName = (TextView)view.findViewById(R.id.videosName_item);
        ImageView videoTopImage = (ImageView)view.findViewById(R.id.video_topImage);
        RelativeLayout videoTopLayout = (RelativeLayout)view.findViewById(R.id.videos_topView);

        VideoInfo item = list.get(position);


        videosName.setText(item.getName());
        picasso.load(videoRequestHandler.SCHEME_VIEDEO +":" +item.getStringPath()).centerCrop().fit().into(videoView);


        if (item.isStatut()){
            Picasso.with(context).load(R.drawable.ic_check).into(videoTopImage);
            videoTopLayout.setBackgroundColor(Color.parseColor("#66af49"));
            videoTopLayout.setAlpha((float)0.5);
        }else {
            videoTopImage.setBackgroundColor(Color.TRANSPARENT);
            videoTopLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;
    }


    public void changeData(boolean statut, int position){
        VideoInfo videoInfo = list.get(position);
        list.remove(position);
        videoInfo.setStatut(statut);
        list.add(position, videoInfo);
        notifyDataSetChanged();
    }
}
