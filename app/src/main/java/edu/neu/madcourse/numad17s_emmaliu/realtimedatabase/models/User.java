package edu.neu.madcourse.numad17s_emmaliu.realtimedatabase.models;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {

    public String token;
    public String username;
    public Integer score;
    public String datePlayed;
    public String longestWord;
    public Integer longestWordScore;



    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String token, String username, Integer score, String datePlayed,
                String longestWord, Integer longestWordScore){
        this.token = token;
        this.username = username;
        this.score = score;
        this.datePlayed = datePlayed;
        this.longestWord = longestWord;
        this.longestWordScore = longestWordScore;
    }

    // generate toString by IDE for debugging
    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", datePlayed='" + datePlayed + '\'' +
                ", longestWord='" + longestWord + '\'' +
                ", longestWordScore=" + longestWordScore +
                '}';
    }

}
