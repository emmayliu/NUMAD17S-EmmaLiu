package edu.neu.madcourse.numad16s_emmaliu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;


public class AboutMe extends AppCompatActivity {
    public String device_id;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
        textView = (TextView) findViewById(R.id.phoneId);
        textView.setText(device_id);
    }

}
