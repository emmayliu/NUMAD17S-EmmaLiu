package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.media.MediaPlayer;
import android.content.Context;

/**
 * Created by emma on 3/1/17.
 */

public class GameStatus {
    private static int score = 0;
    private static int timeLeft = 60;
    public static MediaPlayer mediaPlayer;
    public static boolean isPlaying;
    public static Context preContext;
    public static int preSongs;


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

    public static void playMusic (Context context, int music_id) {
        preSongs = music_id;
        preContext = context;

        if (isPlaying) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(preContext, preSongs);
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public static void resumeMusic() {
        mediaPlayer.start();
    }

    public static void startPlaying() {
        isPlaying = true;
    }

    public static void muteMusic() {
        isPlaying = false;
    }
}
