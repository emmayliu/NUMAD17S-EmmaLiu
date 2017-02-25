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
        this.setTitle(R.string.name);

    }

    public void about_button_click(View view) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    public void getError(View v) {
        throw new RuntimeException("This is a crash");
    }

    public void dictionary_button_click(View view) {
        Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
        startActivity(intent);
    }

    public void tic_tac_toe_button_click(View view) {
        Intent intent = new Intent(MainActivity.this, edu.neu.madcourse.numad17s_emmaliu.tictactoe.MainActivity.class);
        startActivity(intent);
    }





}
