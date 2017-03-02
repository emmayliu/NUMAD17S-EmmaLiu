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
import android.media.MediaPlayer;
import android.os.Bundle;

import edu.neu.madcourse.numad17s_emmaliu.R;


public class MainActivity extends Activity {
    MediaPlayer mMediaPlayer;
    // ...
    GameStatus gs = new GameStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        gs.startPlaying();

    }

    @Override
    protected void onResume() {
        super.onResume();
        gs.stopMusic();
        gs.playMusic(this, R.raw.office);


    }

    @Override
    protected void onPause() {
        super.onPause();
        gs.stopMusic();
    }
}
