package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import edu.neu.madcourse.numad17s_emmaliu.R;

public class NotificationDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setTheme(android.R.style.Theme_Dialog);
        setContentView(R.layout.activity_dialog);

    }

    public void close(View view) {
        finish();
    }
    
    public void goToLeaderboard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }
}
