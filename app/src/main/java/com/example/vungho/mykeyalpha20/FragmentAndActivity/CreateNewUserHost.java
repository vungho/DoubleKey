package com.example.vungho.mykeyalpha20.FragmentAndActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.R;


public class CreateNewUserHost extends AppCompatActivity {


    private PinLockView myPinLockView;
    private IndicatorDots myIndicatorDots;
    private PinLockListener myPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Toast.makeText(CreateNewUserHost.this, "Tao mat khau thanh cong\n"
                    +"Mật khẩu: " +pin, Toast.LENGTH_LONG).show();
            Intent newHostIntent = new Intent(CreateNewUserHost.this, CreateNewUserGuest.class);
            newHostIntent.putExtra("hostPassIntent", pin);
            startActivity(newHostIntent);
            finish();
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
        setContentView(R.layout.activity_create_new_user_host);
        myPinLockView = (PinLockView)findViewById(R.id.new_pinlock);
        myIndicatorDots = (IndicatorDots)findViewById(R.id.new_indicator_dots);
        myPinLockView.attachIndicatorDots(myIndicatorDots);
        myPinLockView.setPinLockListener(myPinLockListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
