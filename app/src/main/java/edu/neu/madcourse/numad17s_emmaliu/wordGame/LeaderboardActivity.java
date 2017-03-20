package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import edu.neu.madcourse.numad17s_emmaliu.R;
import edu.neu.madcourse.numad17s_emmaliu.realtimedatabase.models.User;

public class LeaderboardActivity extends AppCompatActivity {

    private ListView listView;
    private Button buttonSortByTotalScore;
    private Button buttonSortBySingleWord;
    private ArrayAdapter adapter;
    private ArrayList<String> contents = new ArrayList<>();
    private int num1 = 0;
    private int num2 = 0;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> displayedUsers = new ArrayList<>();

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUsersRef = mRootRef.child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView = (ListView) findViewById(R.id.leadboardlist);
        adapter = new ArrayAdapter<>(this, R.layout.mylist_leaderboard, contents);
        listView.setAdapter(adapter);


        buttonSortByTotalScore = (Button) findViewById(R.id.buttonSortTotoalScore);
        buttonSortBySingleWord = (Button) findViewById(R.id.buttonSortWordScore);

        buttonSortByTotalScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByTotalScore(num1);
                num1++;
                adapter.notifyDataSetChanged();
            }
        });

        buttonSortBySingleWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByLongestWord(num2);
                num2++;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   User user = ds.getValue(User.class);
                   users.add(user);
               }
                displayedUsers = getFirst10Users(users);
                for (User u : displayedUsers) {
                    String string = convertUser(u);
                    contents.add(string);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<User> getFirst10Users(ArrayList<User> users) {
        ArrayList<User> result = new ArrayList<>();

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.score - o1.score;
            }
        });

        if (users.size() <= 10) {
            result = users;
        } else {
            for (int i = 0; i < 10; i++) {
                result.add(users.get(i));
            }
        }
        return result;
    }

    private void sortByTotalScore(final int num) {
        Collections.sort(displayedUsers, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if ((num & 1) == 0) {
                    return o1.score - o2.score;
                } else {
                    return o2.score - o1.score;
                }
            }
        });
        contents.clear();

        for (User u : displayedUsers) {
            String string = convertUser(u);
            contents.add(string);
        }
        adapter.notifyDataSetChanged();
    }

    private void sortByLongestWord(final int num) {
        Collections.sort(displayedUsers, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if ((num & 1) == 0) {
                    return o1.longestWordScore - o2.longestWordScore;
                } else {
                    return o2.longestWordScore - o1.longestWordScore;
                }
            }
        });
        contents.clear();

        for (User u : displayedUsers) {
            String string = convertUser(u);
            contents.add(string);
        }
        adapter.notifyDataSetChanged();
    }


    private String convertUser (User user) {
        String result;
        result = "username: " + user.username + "\n"
                + "TotalScore: " + user.score + "\n"
                + "dataPlayer: " + user.datePlayed + "\n"
                + "longestWord: " + user.longestWord + "\n"
                + "longestWordScore: " + user.longestWordScore + "\n";

        return result;
    }

}
