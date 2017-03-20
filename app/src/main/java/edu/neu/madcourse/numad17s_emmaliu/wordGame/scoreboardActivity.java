package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import edu.neu.madcourse.numad17s_emmaliu.R;

public class ScoreboardActivity extends AppCompatActivity {

    private ListView listView;
    private Button buttonSortByScore;
    private Button buttonSortByWords;
    private ArrayAdapter adapter;
    private ArrayList<String> contents = new ArrayList<>();
    private String record;
    private boolean toggle1 = false;
    private int num1 = 0;
    private int num2 = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferences_editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences_editor = sharedPreferences.edit();

        if (sharedPreferences.contains("record")) {
            record = sharedPreferences.getString("record", null);
            String[] recordArr = record.split("\\*");
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

        buttonSortByScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByScore(num1);
                num1++;
                adapter.notifyDataSetChanged();
            }
        });

        buttonSortByWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByWords(num2);
                num2++;
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void sortByScore(final int num) {
        String[] arr = contents.toArray(new String[contents.size()]);

        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] temp1 = o1.split("\n");
                String[] temp2 = o2.split("\n");

                int score1 = Integer.valueOf(temp1[3].substring(13));
                int score2 = Integer.valueOf(temp2[3].substring(13));
                if ((num & 1) == 1) {
                    return score2 - score1;
                } else {
                    return score1 - score2;
                }
            }
        });

        contents.clear();
        for (String s : arr) {
            contents.add(s);
        }
    }

    private void sortByWords(final int num) {
        String[] arr = contents.toArray(new String[contents.size()]);

        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] temp1 = o1.split("\n");
                String[] temp2 = o2.split("\n");

                int score1 = Integer.valueOf(temp1[5].substring(23));
                int score2 = Integer.valueOf(temp2[5].substring(23));

                if ((num & 1) == 1) {
                    return score2 - score1;
                } else {
                    return score1 - score2;
                }
            }
        });

        contents.clear();
        for (String s : arr) {
            contents.add(s);
        }
    }

}
