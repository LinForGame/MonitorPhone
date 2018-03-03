package com.example.why.monitorphone;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String tag = "MainActivity";
public TelephonyManager telephonyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Log.d(tag,"onCreate");
        PhoneStateListener phoneStateListener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state){
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.d(tag,"CALL_STATE_IDLE");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.d(tag,"CALL_STATE_OFFHOOK");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.d(tag,"CALL_STATE_RINGING");
                        OutputStream os = null;
                        try {
                            os = openFileOutput("phonelist",MODE_APPEND);
                            Log.d(tag,"onCallStateChanged"+os);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        PrintStream ps = new PrintStream(os);
                        Log.d(tag,"ps:"+ps);
                        ps.println(new Date()+"来电："+incomingNumber);
                        Log.d(tag,"incomingNumber"+incomingNumber);


                        ps.close();
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
    }
}
