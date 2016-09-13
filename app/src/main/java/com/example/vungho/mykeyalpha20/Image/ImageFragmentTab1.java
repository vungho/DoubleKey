package com.example.vungho.mykeyalpha20.Image;

import com.example.vungho.mykeyalpha20.AsyncTaskManager.ImageInsertDB;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.getbase.floatingactionbutton.FloatingActionButton;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.vungho.mykeyalpha20.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageFragmentTab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageFragmentTab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageFragmentTab1 extends Fragment {

    private ArrayList<ImageInfo> list;
    private ImageAdapter imageAdapter;
    private Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private GridView imageGridView;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fabSelectAll, fabUncheck, fabLock;
    private FloatingActionsMenu fabImageMenu;
    private DataBase dataBase;

    private ArrayList<ImageInfo> listSelected;



    // TODO: Rename and change types and number of parameters
    public static ImageFragmentTab1 newInstance() {
        ImageFragmentTab1 fragment = new ImageFragmentTab1();

        return fragment;
    }

    public ImageFragmentTab1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.image_fragment_tab1, container, false);

        //Provide memory
        list = new ArrayList<ImageInfo>();
        listSelected = new ArrayList<ImageInfo>();


        //Call database and create Image column
        dataBase = new DataBase(view.getContext());


        //Mapping user interface
        imageGridView = (GridView)view.findViewById(R.id.image_gridView);
        fabSelectAll = (FloatingActionButton)view.findViewById(R.id.image_selectAll);
        fabLock = (FloatingActionButton)view.findViewById(R.id.image_check);
        fabUncheck = (FloatingActionButton)view.findViewById(R.id.image_uncheck);
        fabImageMenu = (FloatingActionsMenu)view.findViewById(R.id.image_fab1);

        fabUncheck.setSize(FloatingActionButton.SIZE_MINI);
        fabLock.setSize(FloatingActionButton.SIZE_MINI);
        fabSelectAll.setSize(FloatingActionButton.SIZE_MINI);


        //Loading image from store
        loadImage();

        //create list image
        imageAdapter = new ImageAdapter(view.getContext(), R.layout.image_item, list);
        imageGridView.setAdapter(imageAdapter);

        //action
        //of grid view
        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageInfo imageInfo = list.get(position);
                imageAdapter.changeData(!imageInfo.isTouchStatut(), position);
                if (listSelected.contains(imageInfo)) {
                    listSelected.remove(imageInfo);
                } else {
                    listSelected.add(imageInfo);
                }
            }
        });

        fabLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listSelected.isEmpty()){
                    fabImageMenu.collapse();
                    Toast.makeText(getContext(), getString(R.string.empty_select), Toast.LENGTH_SHORT).show();
                }else {
                    for (ImageInfo info: listSelected){
                        list.remove(info);
                    }
                    fabImageMenu.collapse();
                    new ImageInsertDB(dataBase, view.getContext()).execute(listSelected);
                    imageAdapter.notifyDataSetChanged();
                    listSelected = new ArrayList<ImageInfo>();
                }

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
                        imageAdapter.changeData(!item.isTouchStatut(), i);
                    }
                }
                fabImageMenu.collapse();
            }
        });

        fabUncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (ImageInfo item : listSelected) {
                    imageAdapter.changeData(!item.isTouchStatut(), list.indexOf(item));
                }
                listSelected.clear();
                fabImageMenu.collapse();
            }
        });


        return view;

    }


    private void loadImage() {

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(imageUri, null, null, null, null);

        cursor.moveToFirst();
        while (! cursor.isAfterLast()){
            ImageInfo item = new ImageInfo();
            item.setStringPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            item.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
            list.add(0, item);
            cursor.moveToNext();
        }
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
