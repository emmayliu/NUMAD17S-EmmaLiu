/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/
package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import edu.neu.madcourse.numad17s_emmaliu.R;

public class GameActivity extends Activity {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private GameFragment mGameFragment;
    private CountDownTimer countDownTimer;
    public TextView scoreView;
    public TextView timeView;
    public int time;

    String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mGameFragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game);
        scoreView = (TextView) findViewById(R.id.score);
        timeView = (TextView) findViewById(R.id.timer);
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        int timeLeft = GameStatus.getTimeLeft();
        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);
                int initialScore = GameStatus.getScore();
                String s = "Score: " + Integer.toString(initialScore);
                scoreView.setText(s);
                String time = "Time: " + Integer.toString(timeLeft);
                timeView.setText(time);
                countDown(timeLeft);
            }

        }else {
            Log.d("wordGame", "restore = " + restore);
            countDown(60000);
        }

    }

    public void restartGame() {
        mGameFragment.restartGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this, R.raw.yankee);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(null);
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        String gameData = mGameFragment.getState();
        getPreferences(MODE_PRIVATE).edit()
                .putString(PREF_RESTORE, gameData)
                .commit();
        int timeLeft = time;
        GameStatus.setTimeLeft(timeLeft);

        Log.d("UT3", "state = " + gameData);
    }

    public void countDown(long millisUntilFinished) {
       Log.v(TAG , "i am inside timer countDown method");

        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = (int) millisUntilFinished;
                long sec = millisUntilFinished / 1000;
                timeView.setText("Time: " + String.valueOf(sec));
            }

            @Override
            public void onFinish() {
                timeView.setText("END");
            }
        }.start();

    }
}
