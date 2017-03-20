package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.media.MediaPlayer;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

import edu.neu.madcourse.numad17s_emmaliu.realtimedatabase.models.User;

public class GameStatus {
    private static int score = 0;
    private static int timeLeft = 60;
    public static MediaPlayer mediaPlayer;
    public static boolean isInGame;
    public static boolean isGameStageTwo;
    private static long length;
    private static HashSet<String> reportWords = new HashSet<>();
    private static String[] originalWords = new String[9];
    private static boolean hasRestore;
    private static String TAG = "debuging ";
    private static String username;
    private static int phase1Score;
    private static int phase2Score;
    private static int highestScoreForSingleWord = 0;
    private static String longestWord;
    private static String currentDateTime;
    private static String token;
    private static ArrayList<User> displayedUser = new ArrayList<>();


    public static void setRestoreStatus (boolean restore) {
        hasRestore = restore;
    }

    public static boolean getRestroeStatus () {
        return hasRestore;
    }

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

    public static void setIsGameStageTwo(boolean value) {
        isGameStageTwo = value;
    }
    public static boolean getIsGameStageTwo() {
        return isGameStageTwo;
    }

    public static void setUsername (String name) {
        username = name;
    }
    public static String getUsername() {
        return username;
    }
    public static void setPhase1Score(int score) {
        phase1Score = score;
    }
    public static int getPhase1Score() {
        return phase1Score;
    }
    public static void setPhase2Score(int score) {
        phase2Score = score;
    }
    public static int getPhase2Score() {
        return phase2Score;
    }

    public static void setIsInGame(boolean value) {
        isInGame = value;
    }
    public static boolean getIsInGame() {
        return isInGame;
    }


    public static void setHighestScoreForSingleWord(int score) {
        if (score >= highestScoreForSingleWord) {
            highestScoreForSingleWord = score;
        }
    }

    public static void setDisplayedUser(ArrayList<User> users) {
        displayedUser = users;
    }
    public static ArrayList<User> getDisplayedUser() {
        return displayedUser;
    }
    public static void clearDisplayedUser() {
        displayedUser.clear();;
    }

    public static int getHighestScoreForSingleWord() {
        return highestScoreForSingleWord;
    }
    public static void setLongestWord(String word) {
        longestWord = word;
    }
    public static String getLongestWord() {
        return longestWord;
    }
    public static void setCurrentDateTime(String dateTime) {
        currentDateTime = dateTime;
    }
    public static String getCurrentDateTime() {
        return currentDateTime;
    }
    public static void setToken(String mToken) {
        token = mToken;
    }
    public static String getToken() {
        return token;
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
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(context, music_id);
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

    }

    protected long getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
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

}
