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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


import edu.neu.madcourse.numad17s_emmaliu.R;

public class GameActivity extends Activity {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private GameFragment mGameFragment;
    private ControlFragment controlFragment;
    private CountDownTimer countDownTimer;
    public TextView scoreView;
    public TextView timeView;
    public ToggleButton tooggleB;
    public GameStatus gs = new GameStatus();
    public int time;
    String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mGameFragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game);
        controlFragment = (ControlFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game_controls);
        scoreView = (TextView) findViewById(R.id.score);
        timeView = (TextView) findViewById(R.id.timer);
        tooggleB = (ToggleButton) findViewById(R.id.toggleButton);
        tooggleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleClicked(v);
            }
        });
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        int timeLeft = GameStatus.getTimeLeft();
        int initialScore = GameStatus.getScore();

        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);

                String s = "Score: " + Integer.toString(initialScore);
                scoreView.setText(s);
                String time = "Time: " + Integer.toString(timeLeft);
                timeView.setText(time);
                countDown(timeLeft);
            }

        }else {
            Log.d("wordGame", "restore = " + restore);
            countDown(20000);
        }

    }

    public void restartGame() {
        mGameFragment.restartGame();
    }

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        if (on) {
            onPause();
            mGameFragment.getView().setVisibility(View.GONE);
            controlFragment.getView().setVisibility(View.GONE);
            timeView.setVisibility(View.INVISIBLE);
            scoreView.setVisibility(View.INVISIBLE);
            gs.pauseMusic();

        } else {
            countDown(time);
            mGameFragment.getView().setVisibility(View.VISIBLE);
            controlFragment.getView().setVisibility(View.VISIBLE);
            timeView.setVisibility(View.VISIBLE);
            scoreView.setVisibility(View.VISIBLE);
            int length =(int) gs.getCurrentPosition();
            mMediaPlayer.seekTo(length);
            mMediaPlayer.start();

        }
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
        String gameData = mGameFragment.getState();
        mMediaPlayer.pause();
        getPreferences(MODE_PRIVATE).edit()
                .putString(PREF_RESTORE, gameData)
                .apply();
        int timeLeft = time;
        GameStatus.setTimeLeft(timeLeft);
        String score = (String) scoreView.getText();
        score = score.substring(7);
        int num = Integer.valueOf(score);
        GameStatus.setScore(num);
        countDownTimer.cancel();

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
                timeView.setText("Stage 2");
                if (GameStatus.getStage() == 1) {
                    countDown(10000);
                } else {
                    timeView.setText("End");
                }
            }
        }.start();

    }
}
