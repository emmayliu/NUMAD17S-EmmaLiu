package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.media.MediaPlayer;
import android.content.Context;
import android.util.Log;

import java.util.HashSet;

public class GameStatus {
    private static int score = 0;
    private static int timeLeft = 60;
    public static MediaPlayer mediaPlayer;
    public static boolean isPlaying;
    public static int gameStage = 1;
    private static long length;
    private static HashSet<String> reportWords = new HashSet<>();
    private static String[] originalWords = new String[9];
    private static String TAG = "debuging ";


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

    public static void addReportWords(String word) {
        reportWords.add(word);
    }
    public static HashSet getReprotWords() {
        return reportWords;
    }
    public static void setOriginalWords(String[] words) {
        originalWords = words;
        Log.e(TAG, " setting words");
        Log.e(TAG, originalWords[0]);
    }

    public static String[] getOriginalWords() {
        return originalWords;
    }

    public static void playMusic (Context context, int music_id) {
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

    public static void stopMusic() {
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

    public static void startPlaying() {
        isPlaying = true;
    }

}
