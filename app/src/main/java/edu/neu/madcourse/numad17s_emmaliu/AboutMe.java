package edu.neu.madcourse.numad17s_emmaliu;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.util.Log;





public class AboutMe extends AppCompatActivity {
    private static final int DEV_ID_PERMISSION = 1;
    private static final String TAG = "DisplayMessageActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        TextView textView = (TextView) findViewById(R.id.phoneId);

        Log.i(TAG, "Try using Log now");


        //Check permission

        Log.v(TAG, "About to check permission");
        int permissionCheck
                = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Already have permission");
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (tm.getDeviceId() != null) {
                textView.setText(tm.getDeviceId());
            } else {
                textView.setText("This Emulator doesn't have IMEI");
            }
        } else {
            Log.e(TAG, "Don't have permission yet, need to ask user");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    DEV_ID_PERMISSION);


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Request Permission returned");
        switch (requestCode) {
            case DEV_ID_PERMISSION: {
                Log.i(TAG, "Do I have DEV_ID permissions? ");
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.v(TAG, "user give the permission!");
                    // permission was granted, yay! Do the
                    // permissions-related task you need to do.
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    TextView textView = (TextView) findViewById(R.id.phoneId);
                    if (tm.getDeviceId() != null) {
                        textView.setText(tm.getDeviceId());
                    } else {
                        textView.setText("This Emulator doesn't have IMEI");
                    }


                } else {
                    Log.e(TAG, "User denied permission. ");
                }
                return;
            }
        }
    }


}
