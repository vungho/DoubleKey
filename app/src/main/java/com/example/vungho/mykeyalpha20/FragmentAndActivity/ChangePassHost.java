package com.example.vungho.mykeyalpha20.FragmentAndActivity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.vungho.mykeyalpha20.DataBase.DataBase;
import com.example.vungho.mykeyalpha20.R;

public class ChangePassHost extends AppCompatActivity {

    private int count = 0;
    private PinLockView pinLockView;
    private IndicatorDots indicatorDots;
    private DataBase dataBase;
    private String pastPass;
    private TextView txtMess;
    private PinLockListener pinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            if (count == 0){
                if (pastPass.equals(pin)){
                    count ++;
                    pinLockView.resetPinLockView();
                    txtMess.setText(getString(R.string.first_pass));
                }else {
                    txtMess.setText(getString(R.string.login_statut_fail));
                }
            }else {
                Toast.makeText(ChangePassHost.this, "Thay đổi thành công\nMật khẩu: "+pin, Toast.LENGTH_LONG).show();
                dataBase.fixUser("host", pin);
                dataBase.close();
                onBackPressed();
                finish();
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
        setContentView(R.layout.activity_change_pass_host);
        dataBase = new DataBase(getApplicationContext());
        Cursor cursor = dataBase.getUser("host");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            pastPass = cursor.getString(0);
            cursor.moveToNext();
        }
        txtMess = (TextView)findViewById(R.id.txt_mess_new_host_pass);
        indicatorDots = (IndicatorDots)findViewById(R.id.change_host_indicator_dots);
        pinLockView = (PinLockView)findViewById(R.id.change_host_pinlock);
        pinLockView.attachIndicatorDots(indicatorDots);
        pinLockView.setPinLockListener(pinLockListener);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
