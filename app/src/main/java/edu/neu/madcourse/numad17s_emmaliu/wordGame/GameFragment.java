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
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.MediaPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;


import edu.neu.madcourse.numad17s_emmaliu.wordGame.GameStatus;
import edu.neu.madcourse.numad17s_emmaliu.R;

public class GameFragment extends Fragment {
    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9,};
    static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,
            R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
            R.id.small9,};
    //private Handler mHandler = new Handler();
    private Tile mEntireBoard = new Tile(this);
    private Tile mLargeTiles[] = new Tile[9];
    private Tile mSmallTiles[][] = new Tile[9][9];
    //private Tile.Owner mPlayer = Tile.Owner.X;
    private Set<Tile> mAvailable = new HashSet<Tile>();
    private int mSoundX, mSoundO, mSoundMiss, mSoundRewind;
    private SoundPool mSoundPool;
    private float mVolume = 1f;
    private int mLastLarge;
    private int mLastSmall;
    private Map<Integer, ArrayList<Integer>> map = new HashMap<>();
    private int[] status = new int[9];
    private StringBuilder[] sbArr = new StringBuilder[9];
    private Trie trie = new Trie();
    private boolean[][][] visited = new boolean[26][26][26];
    private static String fileName = "";
    private Tile[][] userInputTiles = new Tile[9][9];
    private MediaPlayer mediaPlayer;
    private StringBuilder sb = new StringBuilder();
    private HashSet<String> phase1Words = new HashSet<>();
    private HashSet<String> phase2Words = new HashSet<>();

    private String errorTAG = "Error Log";
    private String debugTAG = "Debug ";
    private String TAG = "search word part";


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
    private void initStringBuilder () {
        for (int i = 0;  i < 9; i++) {
            sbArr[i] = new StringBuilder("");
        }
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

    private boolean isValidMove(int mLastLarge, int large, int small, int gameStage) {
        int lasPos = status[large];
        if (gameStage == 1) {
            if (mLastLarge == -1) return true;
            if (lasPos == small) return false;
            if (lasPos == -1) return true;
            ArrayList<Integer> neighbors = map.get(lasPos);
            for (int neighbor : neighbors) {
                if (neighbor == small) {
                    return true;
                }
            }
        } else if (gameStage == 2) {
            if(mSmallTiles[large][small].getOwner().name() == "NEIGHTER") {
                return false;
            }
            return true;
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

    private void initialStatus() {
        for (int i = 0; i < status.length; i++) {
            status[i] = -1;
        }
    }

    private void initUserInputTiles() {
        for (int i = 0; i < userInputTiles.length; i++) {
            for (int j = 0; j < userInputTiles[i].length; j++) {
                if(userInputTiles[i][j] != null) {
                    userInputTiles[i][j].removeBackgroud();
                }
                userInputTiles[i][j] = null;
            }
        }
    }


    private void initViews(View rootView) {
        Log.e(debugTAG, "I am inside initViews");
        GameStatus.setStage(1);

        mEntireBoard.setView(rootView);
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
                int bc = smallTile.getBackGroundColor();
                //Log.e(TAG, Integer.toString(bc));
                if (bc == 1) {
                    smallTile.changeBackground();
                }

                // ...
                inner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // ...
                        String s = Integer.toString(GameStatus.getStage());
                        Log.e(TAG, s);

                        int phase = GameStatus.getStage();
                        if (phase == 1) {
                            playGamePhaseOne(fLarge, fSmall, smallTile, phase);
                        } else if (GameStatus.getStage() == 2){
                            Log.e(TAG, " stage two code");
                            playGamePhaseTwo(fLarge, fSmall, smallTile, phase);
                        }
                    }
                });
                // ...
            }
        }
    }

    public void playGamePhaseOne(int fLarge, int fSmall, Tile smallTile, int stage) {
        int isSelected = smallTile.getIsSelected();
        if (isValidMove(mLastLarge, fLarge, fSmall, stage) && (isSelected == 0)) {
            smallTile.animate();
            smallTile.selectLetter();
            mSoundPool.play(mSoundX, mVolume, mVolume, 1, 0, 1f);

            //makeMove(fLarge, fSmall);
            mLastLarge = fLarge;
            mLastSmall = fSmall;
            status[fLarge] = fSmall;

            char c = smallTile.getLetter();
            sbArr[fLarge].append(c);
            String inputWord = sbArr[fLarge].toString();
            userInputTiles[fLarge][fSmall] = smallTile;


            // verify word
            //Log.e(errorTAG, inputWord);
            if (searchWord(inputWord)) {
                for (Tile tile : userInputTiles[fLarge]) {
                    if (tile != null) {
                        tile.changeBackground();
                        tile.setBackGroundColor(1);
                    }
                }
                int myScore = GameStatus.getScore();
                myScore += increaseScore(inputWord);
                GameStatus.setScore(myScore);
                String value = "Score: " + Integer.toString(myScore);
                TextView textView = (TextView) getActivity()
                        .findViewById(R.id.score);
                textView.setText(value);
                phase1Words.add(inputWord);
                GameStatus.addReportWords(inputWord);

            } else {
                Log.v(debugTAG, "not a word");
            }
        }
    }

    public void startGamestage2() {
        GameStatus.setStage(2);
        //removeUnConfirmedWords();
        removeBackgroundColorForStage2();
        updateAllTiles();
        initUserInputTiles();
    }


    public void playGamePhaseTwo(int fLarge, int fSmall, Tile smallTile, int stage) {
        String name = smallTile.getOwner().name();
        int isSelected = smallTile.getIsSelected();
        if (isValidMove(mLastLarge, fLarge, fSmall, stage)&& (isSelected == 0) &&
                !(name.equals("NEITHER"))) {
            smallTile.animate();
            smallTile.selectLetter();
            mSoundPool.play(mSoundX, mVolume, mVolume, 1, 0, 1f);

            mLastLarge = fLarge;
            mLastSmall = fSmall;

            char c = smallTile.getLetter();
            sb.append(c);
            String inputWord = sb.toString();
            userInputTiles[fLarge][fSmall] = smallTile;

            if (searchWord(inputWord)) {
                Log.e(TAG, " found word in phase 2");
                Log.e(TAG, inputWord);
                int myScore = GameStatus.getScore();
                myScore += increaseScoreForPhaseTwo(inputWord);
                GameStatus.setScore(myScore);
                String value = "Score: " + Integer.toString(myScore);
                TextView textView = (TextView) getActivity()
                        .findViewById(R.id.score);
                textView.setText(value);
                userInputTiles[fLarge][fSmall] = smallTile;
                phase2Words.add(inputWord);
                GameStatus.addReportWords(inputWord);
                for (Tile tile : userInputTiles[fLarge]) {
                    if (tile != null) {
                        tile.changeBackground();
                        tile.setBackGroundColor(1);
                    }
                }
            } else {
                Log.e(TAG, "not word in stage2");
                Log.e(TAG, inputWord);

            }
            // if user selected used word, restart phase 2 board
        } else if (isSelected == 1) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (mSmallTiles[i][j].getIsSelected() == 1) {
                        mSmallTiles[i][j].unSelectLetter();
                        mSmallTiles[i][j].updateDrawableState();
                    }
                }
            }
            sb = new StringBuilder();
            Log.e(TAG, "reset all words in phase 2");
        }

    }

    public void removeBackgroundColorForStage2() {
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                if (mSmallTiles[i][j].getBackGroundColor() == 1) {
                    mSmallTiles[i][j].removeBackgroud();
                    mSmallTiles[i][j].setBackGroundColor(0) ;
                    mSmallTiles[i][j].unSelectLetter();
                    mSmallTiles[i][j].setIsSelected(0);
                } else {
                    mSmallTiles[i][j].setIsSelected(0);
                    mSmallTiles[i][j].setOwner(Tile.Owner.NEITHER);
                }
            }
        }
    }

    public void restartGame() {
        mSoundPool.play(mSoundRewind, mVolume, mVolume, 1, 0, 1f);
        // ...
        initialStatus();
        initStringBuilder();
        initUserInputTiles();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                mSmallTiles[i][j].unSelectLetter();
            }
        }
        fillWithWords();
        updateAllTiles();
    }

    public void initGame() {
        Log.e("word game", "init game");
        addAllNeighbors();
        initialStatus();
        initStringBuilder();
        initUserInputTiles();

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

    public ArrayList<String> readData(String fileName) throws IOException {
        ArrayList<String> wordlist = new ArrayList<>();
        try {
            AssetManager manager = getResources().getAssets();
            InputStream is = manager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                wordlist.add(line);
            }
            reader.close();
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return wordlist;
    }

    private int getRandom() {
        Random rand = new Random();
        int num = rand.nextInt(999) + 0;
        return num;
    }

    /**
     * we can design a lot of rules here to make the game addictive
     */

    private int increaseScoreForPhaseTwo(String word) {
        Log.e(TAG, "increasing score for phase two");
        int score = 0;
        CharSequence c = "word";
        char magicLetter = 'y';

        if (phase2Words.contains(word)) {
            Log.e(TAG, "Alredy have this word in phase 2");
            return score;
        }

        if (word.contains(c)) {
            score += 500;
        }
        if (word.indexOf(magicLetter) != -1) {
            score =+ 100;
        }

        if (word.length() == 9) {
            score += 1000;
        }
        score += 200;
        return score;
    }

    private int increaseScore(String word) {
        int score = 0;
        CharSequence c = "word";
        char magicLetter = 'y';

        // score booster
        if (word.contains(c)) {
            score += 500;
        }
        if (word.indexOf(magicLetter) != -1) {
            score =+ 100;
        }
        if (word.length() == 9) {
            score += 100;
        } else if (word.length() >= 6) {
            score += 60;
        } else if (word.length() >= 4)  {
            score += 40;
        } else {
            score += 20;
        }
        return score;
    }

    public boolean searchWord(String s) {
        s = s.toLowerCase();
        if (isAlpha(s)) {
            if (s.length() >= 3) {
                fileName = s.substring(0, 3);
                int a = fileName.charAt(0) - 'a';
                int b = fileName.charAt(1) - 'a';
                int c = fileName.charAt(2) - 'a';


                if (!visited[a][b][c]) {
                    try {
                        visited[a][b][c] = true;
                        Log.e(TAG, fileName + "I am here before open file");
                        readDataForWordGame(fileName);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (trie.search(s)) {
                    //toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                    Log.e(TAG, " found the word");
                    return true;
                } else {
                    Log.e(TAG, "word" + s + " doesn't exist in dictionary");
                    return false;
                }
            }
        } else {
            Log.e(TAG, "Not valid input");
        }
        return false;
    }


    public void readDataForWordGame(String fileName) throws IOException {
        Log.e(TAG, fileName + "fileName");
        try {
            AssetManager manager = getResources().getAssets();
            InputStream is = manager.open(fileName + ".txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() <= 9) {
                    trie.insert(line.split("\n")[0]);
                }
            }
            Log.e(TAG, " wordGame trie build complete");

            reader.close();
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isAlpha (String s) {
        return s.matches("[a-z]+");
    }



    private void fillWithWords() {
        //randomly pick 1000 words with length 9 in wordlist_random_9.txt
        ArrayList<String> wordlist = new ArrayList<>();
        String[] words = new String[9];
        try{
            wordlist = readData("wordlist_random_9.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < words.length; i++) {
            int random = getRandom();
            String word;
            word = wordlist.get(random);
            words[i] = word.toUpperCase();
        }

        GameStatus.setOriginalWords(words);

        CalculatePath cp = new CalculatePath();
        ArrayList<String> path = cp.generatePath();

        for (int i = 0 ; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int pos = path.get(i).charAt(j) - '0';
                mSmallTiles[i][pos].setLetter(words[i].charAt(j));
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
                if(mSmallTiles[large][small].getBackGroundColor() == 1) {
                    mSmallTiles[large][small].changeBackground();
                }
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
                builder.append(mSmallTiles[large][small].getLetter());
                builder.append(',');
                builder.append(mSmallTiles[large][small].getOwner().name());
                builder.append(',');
                builder.append(mSmallTiles[large][small].getIsSelected());
                builder.append(',');
                builder.append(mSmallTiles[large][small].getBackGroundColor());
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
                Character letter = fields[index++].charAt(0);
                mSmallTiles[large][small].setLetter(letter);
                Tile.Owner owner = Tile.Owner.valueOf(fields[index++]);
                mSmallTiles[large][small].setOwner(owner);
                String s = fields[index++];
                int value = Integer.valueOf(s);
                mSmallTiles[large][small].setIsSelected(value);
                String color = fields[index++];
                int c = Integer.valueOf(color);
                mSmallTiles[large][small].setBackGroundColor(c);
            }
        }
        setAvailableFromLastMove(mLastSmall);
        updateAllTiles();
    }
}

