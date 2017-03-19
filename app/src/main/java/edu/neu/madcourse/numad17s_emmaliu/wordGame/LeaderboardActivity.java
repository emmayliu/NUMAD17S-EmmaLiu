package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private String record;
    private boolean toggle1 = false;
    private int num1 = 0;
    private int num2 = 0;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

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
                ArrayList<User> temp = getFirst10Users(users);
                for (User u : temp) {
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



    private String convertUser (User user) {
        String result;
        result = "username: " + user.username + "\n"
                + "TotalScore: " + user.score + "\n"
                + "dataPlayer: " + user.datePlayed + "\n"
                + "longestWord: " + user.longestWord + "\n"
                + "longestWordScore: " + user.longestWordScore + "\n";

        return result;
    }

    private ArrayList<String> getFirstTenData(ArrayList<String> data) {
        ArrayList<String> result = new ArrayList<>();
        int size = data.size();
        if (size <= 10) {
            result = data;
        } else {
            String[] arr = data.toArray(new String[data.size()]);
            Arrays.sort(arr, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return 0;
                }
            });
        }

        return result;
    }
}
