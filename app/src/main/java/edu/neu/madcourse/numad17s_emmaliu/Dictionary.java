package edu.neu.madcourse.numad17s_emmaliu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;
import android.widget.Button;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;



public class Dictionary extends AppCompatActivity  {
    private static final String TAG = "Test File existense";
    private ListView listView;
    private ArrayAdapter adapter;
    private EditText editText;
    private ToneGenerator toneGenerator;
    private ArrayList<String> words = new ArrayList<>();
    String inputWord = "";
    Trie trie = new Trie();
    String fileName = "";
    boolean[][][] visited = new boolean[26][26][26];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);

        adapter = new ArrayAdapter<String>(this, R.layout.mylist_layout,words);
        listView = (ListView) findViewById(R.id.word_list);

        listView.setAdapter(adapter);

        Button clearButton;
        clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                words.clear();
                adapter.notifyDataSetChanged();

            }
        });


        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputWord = s.toString();
                Log.e(TAG, inputWord);
                if (inputWord.length() >= 3) {
                    fileName = inputWord.substring(0, 3);
                    int a = fileName.charAt(0) - 'a';
                    int b = fileName.charAt(1) - 'a';
                    int c = fileName.charAt(2) - 'a';
                    Log.e(TAG, fileName);
                    if (!visited[a][b][c]) {
                        try {
                            visited[a][b][c] = true;
                            readData(fileName);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (trie.search(inputWord)) {
                        Log.e(TAG, "Find word " + inputWord);
                        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                        if (!words.contains(inputWord)) {
                            words.add(inputWord);
                        }
                    } else {
                        Log.e(TAG, "word" + inputWord + " doesn't exist");
                    }

                }

            }
        });
    }

    public void readData(String fileName) throws IOException {
        try {
            AssetManager manager = getResources().getAssets();
            InputStream is = manager.open(fileName + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            Long tsLong = System.currentTimeMillis()/1000;
            System.out.println(tsLong.toString());


            while ((line = reader.readLine()) != null) {
                //System.out.println(line.split("\n")[0]);
                trie.insert(line.split("\n")[0]);
            }
            Log.e(TAG, "trie build complete");
            Long tsLong2 = System.currentTimeMillis()/1000;
            System.out.println(tsLong2.toString());

            reader.close();
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        String savedWords = TextUtils.join(",", words);
        Log.e(savedWords, "This is saved words in onPause");
        SharedPreferences sp = this.getSharedPreferences("save", Context.MODE_PRIVATE);
        sp.edit().putString("content", savedWords).commit();
    }

    @Override
    protected void onResume() {
        String saveWords = this.getSharedPreferences("save", Context.MODE_PRIVATE).getString("content", null);
        Log.e(saveWords, "get savewords");
        if (saveWords != null && saveWords.length() != 0) {
            String[] temp = saveWords.split(",");
            for (int i = 0; i < temp.length; i++) {
                words.add(temp[i]);
            }
            Log.e(words.toString(), "this is words");
        }
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void back_button_click(View view) {
        Intent intent = new Intent(Dictionary.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void acknowledge_button_click(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(Dictionary.this).create();
        alertDialog.setTitle(R.string.acknowledgementsTitle);
        alertDialog.setMessage
                (getApplicationContext().getResources().getString(R.string.acknowledgements));
        alertDialog.show();
    }
}
