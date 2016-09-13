package com.example.vungho.mykeyalpha20.MenuAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vungho.mykeyalpha20.R;

import java.util.ArrayList;

/**
 * Created by vungho on 17/03/2016.
 */
public class MenuAdapter extends ArrayAdapter<MenuInfo> {

    private Context context;
    private int menuLayout;
    private ArrayList<MenuInfo> listMenu;


    public MenuAdapter(Context context, int menuLayout, ArrayList<MenuInfo> listMenu) {
        super(context, menuLayout, listMenu);
        this.context = context;
        this.menuLayout = menuLayout;
        this.listMenu = listMenu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(menuLayout, null);

        ImageView imageMenu = (ImageView)convertView.findViewById(R.id.image_menu);
        TextView txtMenu = (TextView)convertView.findViewById(R.id.txt_menu);
        txtMenu.setText(listMenu.get(position).getName());
        imageMenu.setBackgroundResource(listMenu.get(position).getId());

        return  convertView;
    }
}
