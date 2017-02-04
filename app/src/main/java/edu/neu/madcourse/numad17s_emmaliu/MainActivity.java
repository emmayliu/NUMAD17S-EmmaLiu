package edu.neu.madcourse.numad17s_emmaliu;

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

    public void about_button_click(View view) {
        Intent intent = new Intent(MainActivity.this, AboutMe.class);
        startActivity(intent);
    }

    public void getError(View v) {
        throw new RuntimeException("This is a crash");
    }

    public void dictionary_button_click(View view) {
        Intent intent = new Intent(MainActivity.this, Dictionary.class);
        startActivity(intent);
    }



}