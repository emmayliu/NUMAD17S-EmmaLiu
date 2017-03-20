package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.neu.madcourse.numad17s_emmaliu.wordGame.LeaderboardActivity;


import edu.neu.madcourse.numad17s_emmaliu.R;

public class NotificationDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

    }

    public void close(View view) {
        finish();
    }
    
    public void goToLeaderboard(View view) {
        finish();
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }
}
