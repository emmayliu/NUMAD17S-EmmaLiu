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
    private ArrayList<String> contents = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferences_editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences_editor = sharedPreferences.edit();

        if (sharedPreferences.contains("record")) {
            String record = sharedPreferences.getString("record", null);
            String[] recordArr = record.split("\\*");
            System.out.println(recordArr.length + " array length");
            if (recordArr != null) {
                for (String r : recordArr) {
                    if (r != null && !r.isEmpty()) {
                        contents.add(r);
                    }
                }
            }
        }

        listView = (ListView) findViewById(R.id.scorelist);
        adapter = new ArrayAdapter<>(this, R.layout.mylist_score, contents);
        listView.setAdapter(adapter);


        buttonSortByScore = (Button) findViewById(R.id.buttonSortByScore);
        buttonSortByWords = (Button) findViewById(R.id.buttonSortByWord);
    }
}
