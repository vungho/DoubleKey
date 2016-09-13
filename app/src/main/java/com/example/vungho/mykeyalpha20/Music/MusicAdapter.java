package com.example.vungho.mykeyalpha20.Music;

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
 * Created by vungho on 22/03/2016.
 */
public class MusicAdapter extends ArrayAdapter<MusicInfo> {

    private Context context;
    private int musicLayout;
    private ArrayList<MusicInfo> arrMusics;

    public MusicAdapter(Context context, int musicLayout, ArrayList<MusicInfo> arrMusics){
        super(context, musicLayout, arrMusics);
        this.context = context;
        this.musicLayout = musicLayout;
        this.arrMusics = arrMusics;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, musicLayout, null);
        TextView musicText = (TextView)view.findViewById(R.id.music_name);
        ImageView musicImagr = (ImageView)view.findViewById(R.id.music_image);
        RelativeLayout topLayout = (RelativeLayout)view.findViewById(R.id.music_top_layout);

        ImageView musicImageTop = (ImageView)view.findViewById(R.id.music_image_topView);

        MusicInfo m = arrMusics.get(position);
        musicText.setText(m.getName());
        Picasso.with(context).load(R.drawable.musics_icon).into(musicImagr);


        if (m.isStatut() == true){
            topLayout.setBackgroundColor(Color.parseColor("#66af49"));
            topLayout.setAlpha((float) 0.5);
            Picasso.with(context).load(R.drawable.ic_check).into(musicImageTop);


        }else {

            topLayout.setBackgroundColor(Color.TRANSPARENT);
            musicImageTop.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;
    }

    @Override
    public void remove(MusicInfo object) {
        arrMusics.remove(object);
        notifyDataSetChanged();
    }

    public void changeData(boolean statut, int position){
        MusicInfo musicInfo = arrMusics.get(position);
        arrMusics.remove(position);
        musicInfo.setStatut(statut);
        arrMusics.add(position, musicInfo);
        notifyDataSetChanged();
    }

}
