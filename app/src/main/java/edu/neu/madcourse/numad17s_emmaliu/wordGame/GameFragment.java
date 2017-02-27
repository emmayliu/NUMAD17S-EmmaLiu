/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/
package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


import edu.neu.madcourse.numad17s_emmaliu.R;

public class GameFragment extends Fragment {
    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9,};
    static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,
            R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
            R.id.small9,};
    private Handler mHandler = new Handler();
    private Tile mEntireBoard = new Tile(this);
    private Tile mLargeTiles[] = new Tile[9];
    private Tile mSmallTiles[][] = new Tile[9][9];
    private Tile.Owner mPlayer = Tile.Owner.X;
    private Set<Tile> mAvailable = new HashSet<Tile>();
    private int mSoundX, mSoundO, mSoundMiss, mSoundRewind;
    private SoundPool mSoundPool;
    private float mVolume = 1f;
    private int mLastLarge;
    private int mLastSmall;
    private Map<Integer, ArrayList<Integer>> map = new HashMap<>();
    private int[] status = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        initGame();
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundX = mSoundPool.load(getActivity(), R.raw.sergenious_movex, 1);
        mSoundO = mSoundPool.load(getActivity(), R.raw.sergenious_moveo, 1);
        mSoundMiss = mSoundPool.load(getActivity(), R.raw.erkanozan_miss, 1);
        mSoundRewind = mSoundPool.load(getActivity(), R.raw.joanne_rewind, 1);
    }

    private void clearAvailable() {
        mAvailable.clear();
    }

    private void addAvailable(Tile tile) {
        tile.animate();
        mAvailable.add(tile);
    }

    public boolean isAvailable(Tile tile) {
        return mAvailable.contains(tile);
    }

    // this is not a good approach, will improve later
    private void addAllNeighbors () {
        map.put(0, new ArrayList<>(Arrays.asList(1, 3, 4)));
        map.put(1, new ArrayList<>(Arrays.asList(0, 2, 3, 4, 5)));
        map.put(2, new ArrayList<>(Arrays.asList(1, 4, 5)));
        map.put(3, new ArrayList<>(Arrays.asList(0, 1, 4, 6, 7)));
        map.put(4, new ArrayList<>(Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8)));
        map.put(5, new ArrayList<>(Arrays.asList(1, 2, 4, 7, 8)));
        map.put(6, new ArrayList<>(Arrays.asList(3, 4, 7)));
        map.put(7, new ArrayList<>(Arrays.asList(3, 4, 5, 6, 8)));
        map.put(8, new ArrayList<>(Arrays.asList(4, 5, 7)));
    }

    private boolean isValidMove(int mLastLarge, int mLastSmall, int large, int small) {
        if (mLastLarge == -1) {
            return true;
        } else {
            int lasPos = status[large];

            if (lasPos == -1) {
                return true;
            }
            if (lasPos == small) {
                return true;
            } else {
                ArrayList<Integer> neighbors = map.get(lasPos);
                for (int neighbor : neighbors) {
                    if (neighbor == small) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.large_board, container, false);
        initViews(rootView);
        updateAllTiles();
        return rootView;
    }

    private void initViews(View rootView) {
        mEntireBoard.setView(rootView);
        addAllNeighbors();
        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);
            mLargeTiles[large].setView(outer);

            for (int small = 0; small < 9; small++) {
                ImageButton inner = (ImageButton) outer.findViewById
                        (mSmallIds[small]);
                final int fLarge = large;
                final int fSmall = small;

                final Tile smallTile = mSmallTiles[large][small];
                smallTile.setView(inner);

                // ...
                inner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // ...
                        if (isValidMove(mLastLarge, mLastSmall, fLarge, fSmall)) {
                            smallTile.animate();
                            smallTile.selectLetter();
                            mSoundPool.play(mSoundX, mVolume, mVolume, 1, 0, 1f);
                            //makeMove(fLarge, fSmall);
                            mLastLarge = fLarge;
                            mLastSmall = fSmall;
                            status[fLarge] = fSmall;

                        } else {
                            mSoundPool.play(mSoundMiss, mVolume, mVolume, 1, 0, 1f);
                        }
                    }
                });
                // ...
            }
        }
    }



    public void restartGame() {
        mSoundPool.play(mSoundRewind, mVolume, mVolume, 1, 0, 1f);
        // ...
        initGame();
        initViews(getView());
        updateAllTiles();
    }

    public void initGame() {
        Log.e("word game", "init game");
        mEntireBoard = new Tile(this);
        // Create all the tiles
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large] = new Tile(this);
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small] = new Tile(this);
            }
            mLargeTiles[large].setSubTiles(mSmallTiles[large]);
        }
        mEntireBoard.setSubTiles(mLargeTiles);
        fillWithWords();

        // If the player moves first, set which spots are available
        mLastSmall = -1;
        mLastLarge = -1;
        setAvailableFromLastMove(mLastSmall);
    }

    private void fillWithWords() {
        String word = "ABCDEFGHI";
        String[] arr = new String[] {word, word, word, word, word, word, word, word, word};
        for (int i = 0 ; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                mSmallTiles[i][j].setLetter(arr[i].charAt(j));
            }
        }
    }
    private void setAvailableFromLastMove(int small) {
        clearAvailable();
        // Make all the tiles at the destination available
        if (small != -1) {
            for (int dest = 0; dest < 9; dest++) {
                Tile tile = mSmallTiles[small][dest];
                if (tile.getOwner() == Tile.Owner.NEITHER)
                    addAvailable(tile);
            }
        }
        // If there were none available, make all squares available
        if (mAvailable.isEmpty()) {
            setAllAvailable();
        }
    }

    private void setAllAvailable() {
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile tile = mSmallTiles[large][small];
                if (tile.getOwner() == Tile.Owner.NEITHER)
                    addAvailable(tile);
            }
        }
    }

    private void updateAllTiles() {
        mEntireBoard.updateDrawableState();
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large].updateDrawableState();
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small].updateDrawableState();
            }
        }
    }

    /** Create a string containing the state of the game. */
    public String getState() {
        StringBuilder builder = new StringBuilder();
        builder.append(mLastLarge);
        builder.append(',');
        builder.append(mLastSmall);
        builder.append(',');
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                builder.append(mSmallTiles[large][small].getOwner().name());
                builder.append(',');
            }
        }
        return builder.toString();
    }

    /** Restore the state of the game from the given string. */
    public void putState(String gameData) {
        String[] fields = gameData.split(",");
        int index = 0;
        mLastLarge = Integer.parseInt(fields[index++]);
        mLastSmall = Integer.parseInt(fields[index++]);
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile.Owner owner = Tile.Owner.valueOf(fields[index++]);
                mSmallTiles[large][small].setOwner(owner);
            }
        }
        setAvailableFromLastMove(mLastSmall);
        updateAllTiles();
    }
}

