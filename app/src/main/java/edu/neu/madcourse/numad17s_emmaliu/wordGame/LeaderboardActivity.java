package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.numad17s_emmaliu.R;

public class LeaderboardActivity extends AppCompatActivity {

    private ListView listView;
    private Button buttonSortByScore;
    private Button buttonSortByWords;
    private ArrayAdapter adapter;
    private ArrayList<String> contents = new ArrayList<>();
    private String record;
    private boolean toggle1 = false;
    private int num1 = 0;
    private int num2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
    }
}
