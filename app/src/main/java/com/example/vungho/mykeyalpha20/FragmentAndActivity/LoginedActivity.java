package com.example.vungho.mykeyalpha20.FragmentAndActivity;




import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vungho.mykeyalpha20.LockScreen.LockScreenService;
import com.example.vungho.mykeyalpha20.MenuAdapter.MenuAdapter;
import com.example.vungho.mykeyalpha20.MenuAdapter.MenuInfo;
import com.example.vungho.mykeyalpha20.R;

import java.io.File;
import java.util.ArrayList;

public class LoginedActivity extends AppCompatActivity {

    private ListView listMenu;
    private MenuAdapter menuAdapter;
    private ArrayList<MenuInfo> arrMenu = new ArrayList<MenuInfo>();
    private DrawerLayout menuDaDrawerLayout;
    private LinearLayout menuLinearLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logined);
        toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);


        listMenu = (ListView)findViewById(R.id.list_menu);
        createMenuItem(arrMenu);
        menuAdapter = new MenuAdapter(this, R.layout.menu_listview_adapter, arrMenu);
        listMenu.setAdapter(menuAdapter);


        //Xử lý item listview click
        menuDaDrawerLayout = (DrawerLayout)findViewById(R.id.menu_drawerlayout);
        menuLinearLayout = (LinearLayout)findViewById(R.id.menu_linearlayout);
        display(0);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                display(position);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuDaDrawerLayout.isDrawerOpen(menuLinearLayout)) {
                    toolbar.setNavigationIcon(R.drawable.ic_menu);
                    menuDaDrawerLayout.closeDrawer(menuLinearLayout);
                } else {
                    toolbar.setNavigationIcon(R.drawable.ic_back);
                    menuDaDrawerLayout.openDrawer(menuLinearLayout);
                }

            }
        });

        menuDaDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                toolbar.setNavigationIcon(R.drawable.ic_back);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                toolbar.setNavigationIcon(R.drawable.ic_menu);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }



    private void createMenuItem(ArrayList<MenuInfo> arrMenu) {
        arrMenu.add(new MenuInfo(R.drawable.image_icon, getString(R.string.image_menu)));
        arrMenu.add(new MenuInfo(R.drawable.videos_icon, getString(R.string.video_menu)));
        arrMenu.add(new MenuInfo(R.drawable.musics_icon, getString(R.string.music_menu)));
        arrMenu.add(new MenuInfo(R.drawable.apps_icon, getString(R.string.app_menu)));
        arrMenu.add(new MenuInfo(R.drawable.settings_icon, getString(R.string.setting_menu)));
        arrMenu.add(new MenuInfo(R.drawable.phone_icon, getString(R.string.phone_menu)));
    }

    private void display(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new ImageFragment();
                break;
            case 1:
                fragment = new VideoFragment();
                break;
            case 2:
                fragment = new MusicFragment();
                break;
            case 3:
                fragment = new AppFragment();
                break;
            case 4:
                fragment = new ChangePassFragment();
                break;
            case 5:
                fragment = new InfoFragment();
                break;
            default:
                break;
        }

        if (fragment != null){
            listMenu.setSelection(position);
            listMenu.setItemChecked(position, true);
            setTitle(arrMenu.get(position).getName());


            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_framelayout, fragment).commit();

            menuDaDrawerLayout.closeDrawer(menuLinearLayout);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        clearApplicationData();
        startService(new Intent(this, LockScreenService.class));
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
