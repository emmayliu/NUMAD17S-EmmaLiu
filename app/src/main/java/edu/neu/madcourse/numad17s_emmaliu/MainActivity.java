package edu.neu.madcourse.numad17s_emmaliu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import edu.neu.madcourse.numad17s_emmaliu.wordGame.GameStatus;


public class MainActivity extends AppCompatActivity {
    Button mTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle(R.string.name);

        mTestButton = (Button) findViewById(R.id.buttonTest);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        //subscribe to topic
        FirebaseMessaging.getInstance().subscribeToTopic("TopWinner");
        System.out.println(" registering");

        String token = FirebaseInstanceId.getInstance().getToken();
        GameStatus.setToken(token);


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
        Intent intent = new Intent(MainActivity.this,
                edu.neu.madcourse.numad17s_emmaliu.wordGame.MainActivity.class);
        startActivity(intent);
    }









}
