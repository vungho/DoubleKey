package com.example.vungho.mykeyalpha20.Music;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vungho.mykeyalpha20.AsyncTaskManager.MusicInsertDB;
import com.example.vungho.mykeyalpha20.DataBase.ChangeName;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicFragmentTab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicFragmentTab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragmentTab1 extends Fragment {

    private ArrayList<MusicInfo> list = new ArrayList<MusicInfo>();
    private MusicAdapter musicAdapter;
    private Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private ListView musicListView;
    private ArrayList<MusicInfo> listSelected = new ArrayList<MusicInfo>();
    private DataBase dataBase ;

    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fabLock, fabSelectAll, fabUncheck;
    private FloatingActionsMenu fabMenu;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragmentTab1.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicFragmentTab1 newInstance(String param1, String param2) {
        MusicFragmentTab1 fragment = new MusicFragmentTab1();

        return fragment;
    }

    public MusicFragmentTab1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.music_fragment_tab1, container, false);
        dataBase = new DataBase(view.getContext());

        musicListView = (ListView)view.findViewById(R.id.music_listView1);
        fabMenu = (FloatingActionsMenu)view.findViewById(R.id.music_fabMenu);

        fabSelectAll = (FloatingActionButton)view.findViewById(R.id.music_selectAll);
        fabLock = (FloatingActionButton)view.findViewById(R.id.music_check);
        fabUncheck = (FloatingActionButton)view.findViewById(R.id.music_uncheck);

        fabUncheck.setSize(FloatingActionButton.SIZE_MINI);
        fabLock.setSize(FloatingActionButton.SIZE_MINI);
        fabSelectAll.setSize(FloatingActionButton.SIZE_MINI);

        loadMuisc();
        musicAdapter = new MusicAdapter(getActivity(), R.layout.music_item, list);

        musicListView.setAdapter(musicAdapter);



       musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               MusicInfo m = list.get(position);
               boolean statut = m.isStatut();
               musicAdapter.changeData(!statut, position);
               if (statut) {
                   listSelected.remove(m);
               } else {
                   listSelected.add(m);
               }
           }
       });


        fabLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listSelected.isEmpty()){
                    Toast.makeText(getContext(), getString(R.string.empty_select), Toast.LENGTH_SHORT).show();
                }else {
                    for (MusicInfo item : listSelected) {
                        list.remove(item);
                    }
                    new MusicInsertDB(dataBase, getContext()).execute(listSelected);

                    listSelected = new ArrayList<MusicInfo>();
                    musicAdapter.notifyDataSetChanged();
                }
                fabMenu.collapse();
            }
        });

        fabUncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (MusicInfo item : listSelected) {
                    musicAdapter.changeData(!item.isStatut(), list.indexOf(item));
                }
                listSelected.clear();
                fabMenu.collapse();
            }
        });

        fabSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    MusicInfo item = list.get(i);
                    if (!listSelected.contains(item)) {
                        listSelected.add(item);
                        musicAdapter.changeData(!item.isStatut(), i);
                    }
                }
                fabMenu.collapse();
            }
        });



        return view;
    }



    private void loadMuisc() {

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(musicUri, null, null, null, null);
        cursor.moveToFirst();

        while (! cursor.isAfterLast()){
            MusicInfo item = new MusicInfo();
            String duongDan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String ten = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            item.setPath(duongDan);
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
