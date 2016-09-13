package com.example.vungho.mykeyalpha20.Application;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
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
 * Created by vungho on 07/06/2016.
 */
public class AppAdapterSQL extends ArrayAdapter<AppInfo> {

    private Context context;
    private int myLayout;
    private ArrayList<AppInfo> list;
    private LoadAppDatabaseAsyncTask  loadApp;


    public AppAdapterSQL(Context context, int myLayout, ArrayList<AppInfo> list) {
        super(context, myLayout, list);
        this.context = context;
        this.myLayout = myLayout;
        this.list = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.app_item, null);

        ImageView appImage = (ImageView)convertView.findViewById(R.id.app_image);
        TextView appName = (TextView)convertView.findViewById(R.id.app_name);
        ImageView appTopImage = (ImageView)convertView.findViewById(R.id.app_topImage);
        RelativeLayout appTopLayout = (RelativeLayout)convertView.findViewById(R.id.app_topLayout);


        AppInfo item = list.get(position);

        loadApp = new LoadAppDatabaseAsyncTask(context, appImage);
        loadApp.execute(item.getPackageName());
        appName.setText(item.getName());

        if (item.isStatut()){
            Picasso.with(context).load(R.drawable.ic_check).into(appTopImage);
            appTopLayout.setBackgroundColor(Color.parseColor("#66af49"));
            appTopLayout.setAlpha((float)0.5 );
        }else{
            appTopImage.setBackgroundColor(Color.TRANSPARENT);
            appTopLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    public void changeData(boolean statut, int position){
        AppInfo aI = list.get(position);
        list.remove(position);
        aI.setStatut(statut);
        list.add(position, aI);
        notifyDataSetChanged();
    }
}
