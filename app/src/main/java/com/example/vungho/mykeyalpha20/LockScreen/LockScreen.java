package com.example.vungho.mykeyalpha20.LockScreen;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.vungho.mykeyalpha20.Application.AppInfo;
import com.example.vungho.mykeyalpha20.Control.HideApp;
import com.example.vungho.mykeyalpha20.Control.HideImage;
import com.example.vungho.mykeyalpha20.Control.HideMusic;
import com.example.vungho.mykeyalpha20.Control.HideVideo;
import com.example.vungho.mykeyalpha20.Control.LoadingApp;
import com.example.vungho.mykeyalpha20.Control.LoadingImage;
import com.example.vungho.mykeyalpha20.Control.LoadingMusic;
import com.example.vungho.mykeyalpha20.Control.LoadingVideo;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.Image.ImageInfo;
import com.example.vungho.mykeyalpha20.Music.MusicInfo;
import com.example.vungho.mykeyalpha20.R;
import com.example.vungho.mykeyalpha20.Video.VideoInfo;

import java.util.ArrayList;

public class LockScreen extends AppCompatActivity implements LockscreenUtils.OnLockStatusChangedListener{

    private ArrayList<ImageInfo> listImage;
    private ArrayList<MusicInfo> listMusic;
    private ArrayList<VideoInfo> listVideo;
    private ArrayList<AppInfo> listApp;
    private String hostPass;
    private String guestPass;
    private DataBase dataBase;
    private PinLockView myPinLockView;
    private IndicatorDots myIndicatorDots;
    private TextView txtLoginStatut;

    private LockscreenUtils mLockscreenUtils;

    private PinLockListener myPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            if (pin.equals(hostPass)){
                dataBase.deleteLockScreen();
                dataBase.insertStatusAccount("host");
                dataBase.close();
                Log.i("login", "HOST");

                goHomeAction();
            }else if (pin.equals(guestPass)){
                dataBase.deleteLockScreen();
                dataBase.insertStatusAccount("guest");
                controlLock();
                dataBase.close();
                Log.i("login", "GUEST");
                goHomeAction();
            }else {
                txtLoginStatut.setText("Nhập khẩu không đúng!");
            }
        }

        @Override
        public void onEmpty() {
            txtLoginStatut.setText("Nhập mật khẩu!");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_lock_screen);

        //Bỏ ra để thử có add hàm attach windows vào được hay không
        makeFullScreen();

        mLockscreenUtils = new LockscreenUtils();
        dataBase = new DataBase(getApplicationContext());
        listImage = new ArrayList<ImageInfo>();
        listMusic = new ArrayList<MusicInfo>();
        listVideo = new ArrayList<VideoInfo>();
        listApp = new ArrayList<AppInfo>();
        loadInfo();

        myIndicatorDots = (IndicatorDots)findViewById(R.id.lock_screen_indicator_dots);
        myPinLockView = (PinLockView)findViewById(R.id.lock_screen_pinlock);
        txtLoginStatut = (TextView)findViewById(R.id.txt_login_statut);
        txtLoginStatut.setText("Nhập mật khẩu");
        myPinLockView.attachIndicatorDots(myIndicatorDots);
        myIndicatorDots.setPinLength(4);
        myPinLockView.setPinLockListener(myPinLockListener);


        Cursor hostCursor = dataBase.getUser("host");
        hostCursor.moveToFirst();
        while (!hostCursor.isAfterLast()){
            hostPass = hostCursor.getString(0);
            hostCursor.moveToNext();
        }
        hostCursor.close();
        Log.i("hostPass", hostPass);

        Cursor guestCursor = dataBase.getUser("guest");
        guestCursor.moveToFirst();
        while (!guestCursor.isAfterLast()){
            guestPass = guestCursor.getString(0);
            guestCursor.moveToNext();
        }
        guestCursor.close();
        Log.i("guestPass", guestPass);


        //unlock screen in case of app get killed by system
        if (getIntent() != null && getIntent().hasExtra("kill")
                && getIntent().getExtras().getInt("kill") == 1) {
            startService(new Intent(this, LockScreenService.class));
            dataBase.deleteLockScreen();
            dataBase.insertStatusAccount("guest");
            controlLock();
            dataBase.close();

            enableKeyguard();
            unlockHomeButton();
        } else {
            try{

                //disableKeyguard();
                //lockHomeButton();
                StateListener phoneStateListener = new StateListener();
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                telephonyManager.listen(phoneStateListener,
                        PhoneStateListener.LISTEN_CALL_STATE);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }



    }

    private void makeFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT <19){
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }else {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        return; //Not thing
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        enableKeyguard();
        unlockHomeButton();
    }

    private void goHomeAction(){
        enableKeyguard();
        unlockHomeButton();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        startService(new Intent(this, LockScreenService.class));
    }

    private void controlLock(){
        //Load các tên file lên và tiến hành khóa
        new HideImage().execute(listImage);
        new HideMusic().execute(listMusic);
        new HideVideo().execute(listVideo);
        //new HideApp(getApplicationContext()).execute(listApp);
    }

    private void loadInfo(){
        //Load thong tin
        Context context = getApplicationContext();
        new LoadingImage(context, listImage).execute();
        new LoadingMusic(context, listMusic).execute();
        new LoadingVideo(context, listVideo).execute();
        //new LoadingApp(context, listApp).execute();
    }

    @Override
    public void onLockStatusChanged(boolean isLocked) {
        if (!isLocked) {
            unlockDevice();
        }
    }

    /*
    @Override
    public void onAttachedToWindow() {
        this.getWindow().setType(
                WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        this.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );

        super.onAttachedToWindow();
    }

    */


    // Handle events of calls and unlock screen if necessary
    private class StateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    unlockHomeButton();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    };

    // Handle button clicks
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
                || (keyCode == KeyEvent.KEYCODE_POWER)
                || (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
                || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {

            return true;
        }

        return false;

    }

    // handle the key press events here itself
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
                || (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
                || (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
            return false;
        }
        if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {

            return true;
        }
        return false;
    }

    // Lock home button
    public void lockHomeButton() {
        mLockscreenUtils.lock(LockScreen.this);
    }

    // Unlock home button and wait for its callback
    public void unlockHomeButton() {
        mLockscreenUtils.unlock();
    }

    @SuppressWarnings("deprecation")
    private void disableKeyguard() {
        KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
        mKL.disableKeyguard();
    }

    @SuppressWarnings("deprecation")
    private void enableKeyguard() {
        KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
        mKL.reenableKeyguard();
    }

    //Simply unlock device by finishing the activity
    private void unlockDevice()
    {
        goHomeAction();
        finish();
    }
}
