package com.example.vungho.mykeyalpha20.Video;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vungho.mykeyalpha20.Application.AppInfo;
import com.example.vungho.mykeyalpha20.AsyncTaskManager.VideoInsertDB;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Manager.ImageManager;
import com.example.vungho.mykeyalpha20.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoFragmentTab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoFragmentTab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragmentTab1 extends Fragment {

    private Uri VideosUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    private ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();
    private ListView listView;
    private VideoAdapter videosAdapter;
    private ArrayList<VideoInfo> listSelected = new ArrayList<VideoInfo>();
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fabSelectAll, fabLock, fabUncheck;
    private FloatingActionsMenu fabMenu;
    private DataBase dataBase;


    // TODO: Rename and change types and number of parameters
    public static VideoFragmentTab1 newInstance(String param1, String param2) {
        VideoFragmentTab1 fragment = new VideoFragmentTab1();

        return fragment;
    }

    public VideoFragmentTab1() {
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
        View view = inflater.inflate(R.layout.video_fragment_tab1, container, false);
        Context context = view.getContext();

        dataBase = new DataBase(context);

        listView = (ListView) view.findViewById(R.id.video_listView1);

        fabMenu = (FloatingActionsMenu)view.findViewById(R.id.video_fabMenu);
        fabSelectAll = (FloatingActionButton) view.findViewById(R.id.video_selectAll);
        fabLock = (FloatingActionButton) view.findViewById(R.id.video_check);
        fabUncheck = (FloatingActionButton) view.findViewById(R.id.video_uncheck);

        fabUncheck.setSize(FloatingActionButton.SIZE_MINI);
        fabLock.setSize(FloatingActionButton.SIZE_MINI);
        fabSelectAll.setSize(FloatingActionButton.SIZE_MINI);

        loadVideo();
        videosAdapter = new VideoAdapter(getActivity(), R.layout.video_item, list);
        listView.setAdapter(videosAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoInfo item = list.get(position);
                if (listSelected.contains(item)) {
                    listSelected.remove(item);
                } else {
                    listSelected.add(item);
                }
                videosAdapter.changeData(!item.isStatut(), position);
            }
        });

        fabLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listSelected.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.empty_select), Toast.LENGTH_SHORT).show();
                } else {
                    for (VideoInfo item : listSelected) {
                        list.remove(item);
                    }
                    new VideoInsertDB(dataBase, getContext()).execute(listSelected);
                    videosAdapter.notifyDataSetChanged();
                    listSelected = new ArrayList<VideoInfo>();
                }
                fabMenu.collapse();
            }
        });

        fabSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    VideoInfo item = list.get(i);
                    if (!listSelected.contains(item)) {
                        listSelected.add(item);
                        videosAdapter.changeData(!item.isStatut(), i);
                    }
                }
                fabMenu.collapse();
            }
        });

        fabUncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (VideoInfo item : listSelected) {
                    videosAdapter.changeData(!item.isStatut(), list.indexOf(item));
                }
                listSelected.clear();
                fabMenu.collapse();
            }
        });

        return view;
    }


    private void loadVideo() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(VideosUri, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            VideoInfo item = new VideoInfo();
            String duongDan = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            String ten = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));

            item.setStringPath(duongDan);
            item.setName(ten);

            list.add(item);

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
