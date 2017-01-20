package edu.neu.madcourse.numad16s_emmaliu;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void about_button_Click(View view) {
        Intent intent = new Intent(MainActivity.this, AboutMe.class);
        startActivity(intent);
    }
    
    public void getError(View v) {
        throw new RuntimeException("This is a crash");
    }


}
