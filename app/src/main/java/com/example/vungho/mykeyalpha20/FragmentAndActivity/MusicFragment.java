package com.example.vungho.mykeyalpha20.FragmentAndActivity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vungho.mykeyalpha20.R;
import com.example.vungho.mykeyalpha20.SlidingTabs.MusicSlidingTabs;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FragmentActivity musicFragmentActivity;



    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();

        return fragment;
    }

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicFragmentActivity = (FragmentActivity)getActivity();
        if (savedInstanceState == null){
            FragmentTransaction fragmentTransaction = musicFragmentActivity.getSupportFragmentManager().beginTransaction();
            MusicSlidingTabs musicSlidingTabs = new MusicSlidingTabs();
            fragmentTransaction.replace(R.id.music_mainLayout, musicSlidingTabs);
            fragmentTransaction.commit();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false);
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

}
