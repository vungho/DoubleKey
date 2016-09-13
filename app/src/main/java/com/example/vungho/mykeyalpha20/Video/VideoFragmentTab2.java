package com.example.vungho.mykeyalpha20.Video;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vungho.mykeyalpha20.AsyncTaskManager.VideoDelDB;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoFragmentTab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoFragmentTab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragmentTab2 extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ArrayList<VideoInfo> list;
    private ListView listViewVideos;
    private ArrayList<VideoInfo> listSelected;
    private DataBase dataBase;
    private FloatingActionButton fabUnLock, fabUncheck, fabSelectAll;
    private FloatingActionsMenu fabMenu;
    private VideoAdapterSQL videoAdapterSQL;


    public static VideoFragmentTab2 newInstance(String param1, String param2) {
        VideoFragmentTab2 fragment = new VideoFragmentTab2();
        return fragment;
    }

    public VideoFragmentTab2() {
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
        View view =  inflater.inflate(R.layout.video_fragment_tab2, container, false);
        dataBase = new DataBase(view.getContext());
        list = new ArrayList<VideoInfo>();
        listSelected = new ArrayList<VideoInfo>();

        fabMenu = (FloatingActionsMenu)view.findViewById(R.id.video_fabMenu2);
        fabUnLock = (FloatingActionButton)view.findViewById(R.id.video_check2);
        fabUncheck = (FloatingActionButton)view.findViewById(R.id.video_uncheck2);
        fabSelectAll = (FloatingActionButton)view.findViewById(R.id.video_selectAll2);

        fabUnLock.setSize(FloatingActionButton.SIZE_MINI);
        fabSelectAll.setSize(FloatingActionButton.SIZE_MINI);
        fabUncheck.setSize(FloatingActionButton.SIZE_MINI);


        //load database
        Cursor cursor = dataBase.getData("select path, newpath, name from video");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            VideoInfo item = new VideoInfo(cursor.getString(0), cursor.getString(2));
            item.setNewPath(cursor.getString(1));
            list.add(item);

            cursor.moveToNext();
        }

        listViewVideos = (ListView)view.findViewById(R.id.video_listView2);
        videoAdapterSQL = new VideoAdapterSQL(getContext(), R.layout.video_item, list);
        listViewVideos.setAdapter(videoAdapterSQL);

        listViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoInfo item = list.get(position);
                if (listSelected.contains(item)) {
                    listSelected.remove(item);
                } else {
                    listSelected.add(item);
                }
                videoAdapterSQL.changeData(!item.isStatut(), position);
            }
        });

        fabUnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listSelected.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.empty_select), Toast.LENGTH_SHORT).show();
                } else {
                    for (VideoInfo item : listSelected) {
                        list.remove(item);
                    }
                    new VideoDelDB(dataBase, getContext()).execute(listSelected);
                    videoAdapterSQL.notifyDataSetChanged();
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
                        videoAdapterSQL.changeData(!item.isStatut(), i);
                    }
                }
                fabMenu.collapse();
            }
        });

        fabUncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (VideoInfo item : listSelected) {
                    videoAdapterSQL.changeData(!item.isStatut(), list.indexOf(item));
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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
