package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import edu.neu.madcourse.numad17s_emmaliu.R;

public class ScoreboardActivity extends AppCompatActivity {

    private ListView listView;
    private Button buttonSortByScore;
    private Button buttonSortByWords;
    private ArrayAdapter adapter;
    private ArrayList<String> contents;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferences_editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        contents = new ArrayList<>();


        String name = sharedPreferences.getString("username", "DEFAULT");
        contents.add(name);

        listView = (ListView) findViewById(R.id.scorelist);
        adapter = new ArrayAdapter<>(this, R.layout.mylist_score, contents);
        listView.setAdapter(adapter);



        buttonSortByScore = (Button) findViewById(R.id.buttonSortByScore);
        buttonSortByWords = (Button) findViewById(R.id.buttonSortByWord);
    }
}
