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
import com.example.vungho.mykeyalpha20.SlidingTabs.AppSlidingTabs;


public class AppFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FragmentActivity fragmentActivity;

    public static AppFragment newInstance(String param1, String param2) {
        AppFragment fragment = new AppFragment();
        return fragment;
    }

    public AppFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity = (FragmentActivity)getActivity();


        if (savedInstanceState == null){
            FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            AppSlidingTabs appSlidingTabs = new AppSlidingTabs();
            fragmentTransaction.replace(R.id.app_mainLayout, appSlidingTabs);
            fragmentTransaction.commit();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app, container, false);
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

}
