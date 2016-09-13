package com.example.vungho.mykeyalpha20.Music;

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

import com.example.vungho.mykeyalpha20.Application.AppInfo;
import com.example.vungho.mykeyalpha20.AsyncTaskManager.MusicDelDB;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicFragmentTab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicFragmentTab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragmentTab2 extends Fragment {


    private ArrayList<MusicInfo> list = new ArrayList<MusicInfo>();
    private MusicAdapter musicAdapter;
    private ListView musicListView;
    private ArrayList<MusicInfo> listSelected = new ArrayList<MusicInfo>();
    private DataBase dataBase ;
    private FloatingActionButton fabUnLock, fabSelectAll, fabUncheck;
    private FloatingActionsMenu fabMenu;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragmentTab2.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicFragmentTab2 newInstance(String param1, String param2) {
        MusicFragmentTab2 fragment = new MusicFragmentTab2();
        return fragment;
    }

    public MusicFragmentTab2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.music_fragment_tab2, container, false);

        dataBase = new DataBase(view.getContext());

        musicListView = (ListView)view.findViewById(R.id.music_listView2);


        fabMenu = (FloatingActionsMenu)view.findViewById(R.id.music_fabMenu_2);

        fabSelectAll = (FloatingActionButton)view.findViewById(R.id.music_selectAll_2);
        fabUnLock = (FloatingActionButton)view.findViewById(R.id.music_check_2);
        fabUncheck = (FloatingActionButton)view.findViewById(R.id.music_uncheck_2);

        fabUncheck.setSize(FloatingActionButton.SIZE_MINI);
        fabUnLock.setSize(FloatingActionButton.SIZE_MINI);
        fabSelectAll.setSize(FloatingActionButton.SIZE_MINI);

        loadMuisc();
        musicAdapter = new MusicAdapter(getContext(), R.layout.music_item, list);

        musicListView.setAdapter(musicAdapter);

        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicInfo item = list.get(position);
                if (listSelected.contains(item)) {
                    list.remove(item);
                } else {
                    listSelected.add(item);
                }
                musicAdapter.changeData(!item.isStatut(), position);
            }
        });


        fabUnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listSelected.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.empty_select), Toast.LENGTH_SHORT).show();
                } else {
                    for (MusicInfo item : listSelected) {
                        list.remove(item);
                    }
                    new MusicDelDB(dataBase, getContext()).execute(listSelected);
                    listSelected = new ArrayList<MusicInfo>();
                    musicAdapter.notifyDataSetChanged();
                }
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


        return view;
    }

    private void loadMuisc() {

        Cursor cursor = dataBase.getData("select *from music");
        cursor.moveToFirst();

        while (! cursor.isAfterLast()){
            MusicInfo m = new MusicInfo();

            m.setStatut(false);
            m.setName(cursor.getString(2));
            m.setNewPath(cursor.getString(1));
            m.setPath(cursor.getString(0));
            list.add(m);

            cursor.moveToNext();
        }
        cursor.close();
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
