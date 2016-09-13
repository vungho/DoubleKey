package com.example.vungho.mykeyalpha20.Image;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.vungho.mykeyalpha20.AsyncTaskManager.ImageDelDB;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;


public class ImageFragmentTab2 extends Fragment {

    private OnFragmentInteractionListener mListener;
    private GridView gridView;
    private FloatingActionsMenu fabMenu;
    private FloatingActionButton fabSelectAll, fabUncheck, fabUnLock;
    private ArrayList<ImageInfo> list;
    private ArrayList<ImageInfo> listSelected;
    private ImageAdapterSQL adapter;
    private DataBase dataBase;
    private String sql;


    public static ImageFragmentTab2 newInstance(String param1, String param2) {
        ImageFragmentTab2 fragment = new ImageFragmentTab2();
        return fragment;
    }

    public ImageFragmentTab2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.image_fragment_tab2, container, false);

        list = new ArrayList<ImageInfo>();
        listSelected = new ArrayList<ImageInfo>();
        gridView = (GridView)view.findViewById(R.id.image_gridView_tab2);
        fabMenu = (FloatingActionsMenu)view.findViewById(R.id.image_fab2);
        fabUnLock = (FloatingActionButton)view.findViewById(R.id.image_check_tab2);
        fabSelectAll = (FloatingActionButton)view.findViewById(R.id.image_selectAll_tab2);
        fabUncheck = (FloatingActionButton)view.findViewById(R.id.image_uncheck_tab2);

        fabUncheck.setSize(FloatingActionButton.SIZE_MINI);
        fabUnLock.setSize(FloatingActionButton.SIZE_MINI);
        fabSelectAll.setSize(FloatingActionButton.SIZE_MINI);

        sql = "select path, newpath, name from image";
        dataBase = new DataBase(view.getContext());
        Cursor cursor = dataBase.getData(sql);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ImageInfo item = new ImageInfo(cursor.getString(2), cursor.getString(0));
            item.setNewPath(cursor.getString(1));
            list.add(item);
            cursor.moveToNext();
        }

        adapter = new ImageAdapterSQL(getContext(), R.layout.image_item, list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageInfo item = list.get(position);
                adapter.changeData(!item.isTouchStatut(), position);
                if (listSelected.contains(item)) {
                    listSelected.remove(item);
                } else {
                    listSelected.add(item);
                }
            }
        });

        fabUnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listSelected.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.empty_select), Toast.LENGTH_SHORT).show();
                } else {
                    for (ImageInfo item : listSelected) {
                        list.remove(item);
                    }
                    adapter.notifyDataSetChanged();
                    new ImageDelDB(dataBase, getContext()).execute(listSelected);
                    listSelected = new ArrayList<ImageInfo>();
                }
                fabMenu.collapse();
            }
        });



        fabSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    ImageInfo item = list.get(i);
                    if (!listSelected.contains(item)) {
                        listSelected.add(item);
                        adapter.changeData(!item.isTouchStatut(), i);
                    }
                }
                fabMenu.collapse();
            }
        });

        fabUncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (ImageInfo item : listSelected) {
                    adapter.changeData(!item.isTouchStatut(), list.indexOf(item));
                }
                listSelected.clear();
                fabMenu.collapse();
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStop() {
        super.onStop();
        dataBase.close();
    }
}
