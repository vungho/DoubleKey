package com.example.vungho.mykeyalpha20.FragmentAndActivity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.vungho.mykeyalpha20.DataBase.CreateFolder;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.LockScreen.LockScreenService;
import com.example.vungho.mykeyalpha20.R;


public class MainActivity extends AppCompatActivity {


    private CreateFolder createFolder;
    private PinLockView myPinLockView;
    private IndicatorDots myIndicatorDots;
    private String successPass;
    private DataBase dataBase;

    private PinLockListener myPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            if (successPass.equalsIgnoreCase(pin)){
                Toast.makeText(MainActivity.this, getString(R.string.login_statut_sucess), Toast.LENGTH_SHORT).show();
                dataBase.close();
                Intent intent = new Intent(MainActivity.this, LoginedActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(MainActivity.this, getString(R.string.login_statut_fail), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onEmpty() {

        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //start service
        startService(new Intent(this, LockScreenService.class));
        //create mkd
        createFolder = new CreateFolder();

        //database
        dataBase = new DataBase(getApplicationContext());
        creatTable();
        Cursor cursor = dataBase.getUser("host");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            successPass = cursor.getString(0);
            Log.i("get", successPass);
            cursor.moveToNext();
        }

        if (successPass == null){
            dataBase.close();
            startActivity(new Intent(MainActivity.this, CreateNewUserHost.class));

        }

        //map

        myPinLockView = (PinLockView)findViewById(R.id.main_pinlock);
        myIndicatorDots = (IndicatorDots)findViewById(R.id.main_indicator_dots);


        myIndicatorDots.setPinLength(4);
        myPinLockView.attachIndicatorDots(myIndicatorDots);
        myPinLockView.setPinLockListener(myPinLockListener);

    }

    private void creatTable() {

        String sqlUser = "create table if not exists user (classify nvarchar(6) primary key not null, " +
                "pass nvarchar(100))";
        dataBase.queryData(sqlUser);

        String sqlApp = "create table if not exists app " +
                "(packagename nvarchar(500) primary key, name nvarchar(100), avatar blob)";
        dataBase.queryData(sqlApp);

        String sqlImage = "create table if not exists image (path nvarchar(500) primary key, newpath nvarchar(505), " +
                "name nvarchar(200), avatar blob )";
        dataBase.queryData(sqlImage);

        String sqlMusic = "create table if not exists music (path nvarchar(500) primary key, newpath nvarchar(505), " +
                        "name nvarchar(200)  not null )";
        dataBase.queryData(sqlMusic);

        String sqlVideo =  "create table if not exists video (path nvarchar(500) primary key, newpath nvarchar(505), name nvarchar(200), " +
                "avatar blob )";
        dataBase.queryData(sqlVideo);

        String sqlLockScreen = "create table if not exists lockScreen (statut nvarchar(10))";
        dataBase.queryData(sqlLockScreen);

        String sqlClassifyAccount = "create table if not exists classifyAccount (classify nvarchar (6))";
        dataBase.queryData(sqlClassifyAccount);

    }


    @Override
    protected void onStop() {
        super.onStop();
        startService(new Intent(this, LockScreenService.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startService(new Intent(this, LockScreenService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        dataBase = new DataBase(getApplicationContext());
        Cursor cursor = dataBase.getUser("host");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            successPass = cursor.getString(0);
            Log.i("get", successPass);
            cursor.moveToNext();
        }

        startService(new Intent(this, LockScreenService.class));
    }
}
