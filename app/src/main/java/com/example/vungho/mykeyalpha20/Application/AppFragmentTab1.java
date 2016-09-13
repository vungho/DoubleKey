package com.example.vungho.mykeyalpha20.Application;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vungho.mykeyalpha20.AsyncTaskManager.AppInsertDB;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Manager.ImageManager;
import com.example.vungho.mykeyalpha20.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AppFragmentTab1 extends Fragment {


    private OnFragmentInteractionListener mListener;
    private AppAdapter appAdapter;
    private ArrayList<AppInfo> list;
    private PackageManager packageManager;
    private ListView appListView;
    private ArrayList<AppInfo> listSelected;
    private FloatingActionButton fabSelectAll, fabLock, fabUncheck;
    private FloatingActionsMenu fabMenu;
    private DataBase dataBase;
    private LoadAppInfoAsyncTask loadAppInfoAsyncTask;


    public static AppFragmentTab1 newInstance(String param1, String param2) {
        AppFragmentTab1 fragment = new AppFragmentTab1();

        return fragment;
    }

    public AppFragmentTab1() {
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
        final View view = inflater.inflate(R.layout.app_fragment_tab1, container, false);

        list = new ArrayList<AppInfo>();
        listSelected = new ArrayList<AppInfo>();

        //create table
        dataBase = new DataBase(view.getContext());

        appListView = (ListView) view.findViewById(R.id.app_listView1);
        fabSelectAll = (FloatingActionButton) view.findViewById(R.id.app_selectAll);
        fabLock = (FloatingActionButton) view.findViewById(R.id.app_check);
        fabUncheck = (FloatingActionButton) view.findViewById(R.id.app_uncheck);
        fabMenu = (FloatingActionsMenu) view.findViewById(R.id.app_fabMenu);

        fabUncheck.setSize(FloatingActionButton.SIZE_MINI);
        fabLock.setSize(FloatingActionButton.SIZE_MINI);
        fabSelectAll.setSize(FloatingActionButton.SIZE_MINI);

        packageManager = getActivity().getPackageManager();


        final List<ApplicationInfo> listApp = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);


        for (ApplicationInfo a : listApp) {
            AppInfo item = new AppInfo();
            item.setName(a.loadLabel(packageManager).toString());
            item.setPackageName(a.packageName.toString());
            list.add(item);
        }


        Collections.sort(list, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo lhs, AppInfo rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        long last = System.nanoTime();


        // loadAppInfoAsyncTask = new LoadAppInfoAsyncTask(list, view.getContext());
        //loadAppInfoAsyncTask.execute(packageManager);
        appAdapter = new AppAdapter(getActivity(), R.layout.app_item, list);
        appListView.setAdapter(appAdapter);


        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = list.get(position);
                if (listSelected.contains(appInfo)) {
                    listSelected.remove(appInfo);
                } else {
                    listSelected.add(appInfo);
                }
                appAdapter.changeData(!appInfo.isStatut(), position);
            }
        });

        fabLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listSelected.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.empty_select), Toast.LENGTH_SHORT).show();
                } else {
                    for (AppInfo app : listSelected) {
                        list.remove(app);
                    }
                    new AppInsertDB(dataBase, getContext()).execute(listSelected);
                    listSelected = new ArrayList<AppInfo>();
                    appAdapter.notifyDataSetChanged();
                }
                fabMenu.collapse();
            }
        });

        fabSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    AppInfo item = list.get(i);
                    if (!listSelected.contains(item)) {
                        listSelected.add(item);
                        appAdapter.changeData(!item.isStatut(), i);
                    }
                }
                fabMenu.collapse();
            }
        });

        fabUncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (AppInfo item : listSelected) {
                    appAdapter.changeData(!item.isStatut(), list.indexOf(item));
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
    public void onStop() {
        super.onStop();
        dataBase.close();
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
