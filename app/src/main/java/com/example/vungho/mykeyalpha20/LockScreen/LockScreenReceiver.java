package com.example.vungho.mykeyalpha20.LockScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.example.vungho.mykeyalpha20.Application.AppInfo;
import com.example.vungho.mykeyalpha20.Control.LoadingApp;
import com.example.vungho.mykeyalpha20.Control.LoadingImage;
import com.example.vungho.mykeyalpha20.Control.LoadingMusic;
import com.example.vungho.mykeyalpha20.Control.LoadingVideo;
import com.example.vungho.mykeyalpha20.Control.UnHideApp;
import com.example.vungho.mykeyalpha20.Control.UnHideImage;
import com.example.vungho.mykeyalpha20.Control.UnHideMusic;
import com.example.vungho.mykeyalpha20.Control.UnHideVideo;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;
import com.example.vungho.mykeyalpha20.Music.MusicInfo;
import com.example.vungho.mykeyalpha20.Video.VideoInfo;

import java.util.ArrayList;

/**
 * Created by vungho on 25/06/2016.
 */
public class LockScreenReceiver extends BroadcastReceiver {

    private DataBase dataBase;
    private String userPass;
    private int countLockScreen = 0;
    private ArrayList<ImageInfo> listImage;
    private ArrayList<MusicInfo> listMusic;
    private ArrayList<VideoInfo> listVideo;
    private ArrayList<AppInfo> listApp;
    @Override
    public void onReceive(Context context, Intent intent) {
        dataBase =  new DataBase(context);
        listImage = new ArrayList<ImageInfo>();
        listMusic = new ArrayList<MusicInfo>();
        listVideo = new ArrayList<VideoInfo>();
        listApp = new ArrayList<AppInfo>();

        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BOOT_COMPLETED) || action.equals(Intent.ACTION_SCREEN_OFF)){
            Log.i("lockIntent", action);

            //Loading host pass of user
            Cursor cursorPassUser = dataBase.getUser("host");
            cursorPassUser.moveToFirst();
            while (!cursorPassUser.isAfterLast()){
                userPass = cursorPassUser.getString(0);
                cursorPassUser.moveToNext();
            }

            //Loading staut of LockScreen
            String statusStartLS = dataBase.getLockSreen();
            String classifyAccount = dataBase.getStatusAccount();

            //If login of user is GUEST
            if (classifyAccount!=null){
                if (classifyAccount.equals("guest")){
                    loadInfo(context);
                    controlUnLock();
                    dataBase.deleteStatusAccount();
                }
            }

            //When user was created
            if (userPass != null){
                    if (statusStartLS == null){ //Khi lock screen chua duoc kich hoat
                        dataBase.insertLockSrceen("YES");
                        dataBase.close();
                        Intent i = new Intent(context, LockScreen.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
            }
        }
    }

    private void controlUnLock(){
        //Load các file bị khóa lên và tiến hành mở khóa
        new UnHideImage().execute(listImage);
        new UnHideVideo().execute(listVideo);
        new UnHideMusic().execute(listMusic);
        //new UnHideApp().execute(listApp);
    }

    private void loadInfo(Context myContext){
        //Load thong tin
        new LoadingImage(myContext, listImage).execute();
        new LoadingMusic(myContext, listMusic).execute();
        new LoadingVideo(myContext, listVideo).execute();
        //new LoadingApp(myContext, listApp).execute();
    }
}
