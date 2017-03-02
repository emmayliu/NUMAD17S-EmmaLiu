package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.media.MediaPlayer;
import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by emma on 3/1/17.
 */

public class GameStatus {
    private static int score = 0;
    private static int timeLeft = 60;
    public static MediaPlayer mediaPlayer;
    public static boolean isPlaying;
    public static int gameStage = 1;
    private static long length;
    private String TAG = "debuging ";


    public static void setScore (int updateScore) {
        score = updateScore;
    }

    public static int getScore () {
        return score;
    }

    public static void setTimeLeft(int time) {
        timeLeft = time;
    }
    public static int getTimeLeft() {
        return timeLeft;
    }

    public static void setStage(int stage) {
        gameStage = stage;
    }
    public static int getStage() {
        return gameStage;
    }



    public void playMusic (Context context, int music_id) {
        if (isPlaying) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(context, music_id);
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

    }

    protected long getCurrentPosition() {
        if(isPlaying) {
            return (mediaPlayer.getCurrentPosition());
        } else {
            return -1;
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            length = getCurrentPosition();
        }
    }

    public void resumeMusic(Context context, int music_id) {
        Log.e(TAG, "trying to resume music");
        mediaPlayer.seekTo((int) length);
        mediaPlayer.start();

    }

    public void startPlaying() {
        isPlaying = true;
    }

    public static void muteMusic() {
        isPlaying = false;
    }
}
