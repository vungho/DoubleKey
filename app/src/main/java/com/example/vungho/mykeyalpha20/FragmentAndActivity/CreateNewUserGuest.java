package com.example.vungho.mykeyalpha20.FragmentAndActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.R;


public class CreateNewUserGuest extends AppCompatActivity {

    private PinLockView myPinLockView;
    private IndicatorDots myIndicatorDots;
    private TextView txtMessGuest;
    private DataBase dataBase;
    private Intent receivedIntent;
    private String passHost = null;
    private PinLockListener myPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            if (passHost.equalsIgnoreCase(pin)){
                txtMessGuest.setText(getString(R.string.equal_pass));
            }else {
                Toast.makeText(CreateNewUserGuest.this, "Tao mat khau thanh cong\n"
                        + "Mật khẩu: " + pin, Toast.LENGTH_LONG).show();
                dataBase.insertUser("host", passHost);
                dataBase.insertUser("guest", pin);
                dataBase.close();
                Intent intent = new Intent(CreateNewUserGuest.this, LoginedActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onEmpty() {
            txtMessGuest.setText(getString(R.string.first_pass_guest));
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user_guest);

        dataBase = new DataBase(getApplicationContext());
        receivedIntent = getIntent();
        passHost = receivedIntent.getStringExtra("hostPassIntent");

        txtMessGuest = (TextView)findViewById(R.id.txt_mess_guess);
        myPinLockView = (PinLockView)findViewById(R.id.new_pinlock_guest);
        myIndicatorDots = (IndicatorDots)findViewById(R.id.new_indicator_dots_guest);
        myPinLockView.attachIndicatorDots(myIndicatorDots);
        myPinLockView.setPinLockListener(myPinLockListener);
    }

}
