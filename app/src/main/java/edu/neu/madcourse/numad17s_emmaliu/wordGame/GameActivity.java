package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import android.util.Log;
import java.util.Calendar;


import edu.neu.madcourse.numad17s_emmaliu.R;

public class GameActivity extends Activity {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private Handler mHandler = new Handler();
    private GameFragment mGameFragment;
    private ControlFragment controlFragment;
    private CountDownTimer countDownTimer;
    public TextView scoreView;
    public TextView timeView;
    public ToggleButton pauseButton;
    public GameStatus gs = new GameStatus();
    public int time;
    public Button restartButton;
    public Button homeButton;

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
        restartButton = (Button) findViewById(R.id.button_restart);
        homeButton = (Button)findViewById(R.id.button_main);
        pauseButton = (ToggleButton) findViewById(R.id.toggleButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleClicked(v);
            }
        });
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        int timeLeft = GameStatus.getTimeLeft();


        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);
                int myScore = GameStatus.getScore();
                String s = "Score: " + Integer.toString(myScore);
                scoreView.setText(s);
                String time = "Time: " + Integer.toString(timeLeft);
                timeView.setText(time);
                countDown(timeLeft);
            }
        }else {
            //Log.d("wordGame", "restore = " + restore);
            GameStatus.setScore(0);
            //change phase 1 timer
            countDown(10000);
        }

    }

    public void restartGame() {
        mGameFragment.restartGame();
    }

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        if (on) {
            countDownTimer.cancel();
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
            GameStatus.mediaPlayer.seekTo(length);
            GameStatus.mediaPlayer.start();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameStatus.mediaPlayer = MediaPlayer.create(this, R.raw.yankee);
        GameStatus.mediaPlayer.setLooping(true);

        if (GameStatus.isPlaying == true) {
            GameStatus.mediaPlayer.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(null);
        String gameData = mGameFragment.getState();

        GameStatus.mediaPlayer.pause();
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



        //Log.d("UT3", "state = " + gameData);
    }

    public void countDown(long millisUntilFinished) {
        //Log.v(TAG , "i am inside timer countDown method");

        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = (int) millisUntilFinished;
                long sec = millisUntilFinished / 1000;
                String s = "Time: " + String.valueOf(sec);
                timeView.setText(s);
                if (sec == 10) {
                    Toast.makeText(GameActivity.this, R.string.alertToastText,
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFinish() {
                if (!GameStatus.isGameStageTwo) {
                    mGameFragment.startGamestage2();
                    // change phase 2 timer
                    countDown(10000);
                } else {
                    mGameFragment.getView().setVisibility(View.GONE);
                    timeView.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.GONE);
                    restartButton.setVisibility(View.GONE);
                    scoreView.setVisibility(View.GONE);
                    AlertDialog.Builder alertDialogBuilder =
                            new AlertDialog.Builder(GameActivity.this);
                    alertDialogBuilder.setTitle(R.string.gameReportTitle);
                    alertDialogBuilder.setMessage(generateReport());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    GameStatus.setRestoreStatus(false);
                    homeButton.setVisibility(View.VISIBLE);


                    GameStatus.setIsGameStageTwo(false);

                }
            }
        }.start();
    }

            private void updateResultToScoreboard() {
                // dateTime, final score, longestWord, highest word score, phase1, phase2
                String result = generateRecord();
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(this);

                SharedPreferences.Editor sharedPreferences_editor = sharedPreferences.edit();

                if (!sharedPreferences.contains("record")) {
                    sharedPreferences_editor.putString("record", result);
                } else {
                    String temp = sharedPreferences.getString("record", null);
                    temp = temp + "***" + result;
                    sharedPreferences_editor.putString("record", temp);
                }
                sharedPreferences_editor.apply();
            }

            private String generateRecord() {
                String longestWord = getLongestWord();
                GameStatus.setLongestWord(longestWord);
                int longestWordScore = GameStatus.getHighestScoreForSingleWord();
                int phase1Score = GameStatus.getPhase1Score();
                int phase2Score = GameStatus.getPhase2Score();
                int finalScore = GameStatus.getScore();

                String mydate = java.text.DateFormat.getDateTimeInstance().
                        format(Calendar.getInstance().getTime());
                GameStatus.setCurrentDateTime(mydate);

                String result = "DateTime: " + mydate + "\n"
                                + "Phase One Score: " + phase1Score + "\n"
                                + "Phase Two Score: " + phase2Score + "\n"
                                + "Total Score: " + finalScore + "\n"
                                + "Longest Word You Found: " + longestWord +"\n"
                                + "Score of Longest Word: " +  longestWordScore +"\n";

                return result;
            }

            private String getLongestWord() {
                String longestWord = "";
                HashSet<String> userWords = GameStatus.getReprotWords();
                if (userWords != null) {
                    for (String word : userWords) {
                        if (word.length() > longestWord.length()) {
                            longestWord = word;
                        }
                    }
                }
                return longestWord;
            }

            private String generateReport() {
                String[] words = GameStatus.getOriginalWords();
                String s1 = convertToString(words);
                HashSet<String> userWords = GameStatus.getReprotWords();
                String[] userWordArr  = userWords.toArray(new String[userWords.size()]);
                String s2 = convertToString(userWordArr);
                int score = GameStatus.getScore();
                // set phase2 score;
                int phase2Score = score - GameStatus.getPhase1Score();
                GameStatus.setPhase2Score(phase2Score);


                String s3 = getResources().getString(R.string.myScore) + Integer.toString(score);


                String message = getResources().getString(R.string.congratulation) + "\n" + "\n"
                        + getResources().getString(R.string.originalWords) + "\n" + s1 + "\n" +"\n"
                        + getResources().getString(R.string.userWords) + "\n" + s2 + "\n" + "\n"
                        + s3 + "\n" + "\n"
                        + getResources().getString(R.string.enjoyText);

                updateResultToScoreboard();


                return message;
            }



            private  String convertToString(String[] arr) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < arr.length; i++) {
                    String s = arr[i].toLowerCase();
                    sb.append(s);
                    if (i != arr.length - 1) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }

}
