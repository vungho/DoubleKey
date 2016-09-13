package com.example.vungho.mykeyalpha20.Application;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
 * Created by vungho on 06/07/2016.
 */
public class AppAdapter extends ArrayAdapter<AppInfo>{

    private Context context;
    private int myLayout;
    private AppRequestHandler appRequestHandler;
    private Picasso picasso;
    private ArrayList<AppInfo> list;



    public AppAdapter(Context context, int myLayout, ArrayList<AppInfo> list) {
        super(context, myLayout, list);
        this.context = context;
        this.myLayout = myLayout;
        this.list = list;

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.addRequestHandler(new AppRequestHandler(context));
        picasso = builder.build();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View  view = View.inflate(context, myLayout, null);

        ImageView appImage = (ImageView)view.findViewById(R.id.app_image);
        TextView appName = (TextView)view.findViewById(R.id.app_name);
        ImageView appTopImage = (ImageView)view.findViewById(R.id.app_topImage);
        RelativeLayout appTopLayout = (RelativeLayout)view.findViewById(R.id.app_topLayout);

        AppInfo item = list.get(position);

        //Set name
        appName.setText(item.getName());

        //Load icon
        picasso.load(AppRequestHandler.SCHEME_APP_ICON + ":" +item.getPackageName()).into(appImage);

        if (item.isStatut()){
            Picasso.with(context).load(R.drawable.ic_check).into(appTopImage);
            appTopLayout.setBackgroundColor(Color.parseColor("#66af49"));
            appTopLayout.setAlpha((float)0.5 );
        }else{
            appTopImage.setBackgroundColor(Color.TRANSPARENT);
            appTopLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;

    }

    public void changeData(boolean statut, int position){
        AppInfo aI = list.get(position);
        list.remove(position);
        aI.setStatut(statut);
        list.add(position, aI);
        notifyDataSetChanged();
    }
}
