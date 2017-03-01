package edu.neu.madcourse.numad17s_emmaliu.wordGame;

/**
 * Created by emma on 3/1/17.
 */

public class GameStatus {
    private static int score = 0;
    private static int timeLeft = 30;

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
}
