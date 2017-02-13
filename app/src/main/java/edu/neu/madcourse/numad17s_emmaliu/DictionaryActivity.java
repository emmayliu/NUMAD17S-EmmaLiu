package edu.neu.madcourse.numad17s_emmaliu;

import android.content.Intent;
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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class DictionaryActivity extends AppCompatActivity  {
    private static final String TAG = "LOG_ACTIVITY";
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
        ListView listView = (ListView) findViewById(R.id.word_list);
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
                if (isAlpha(inputWord)) {
                    System.out.println("inputWord " + inputWord);

                    if (inputWord.length() >= 3) {
                        fileName = inputWord.substring(0, 3);
                        int a = fileName.charAt(0) - 'a';
                        int b = fileName.charAt(1) - 'a';
                        int c = fileName.charAt(2) - 'a';

                        //System.out.println("a: " + a);
                        //System.out.println("b: " + b);
                        //System.out.println("c: " + c);

                        if (!visited[a][b][c]) {
                            try {
                                visited[a][b][c] = true;
                                readData(fileName);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (trie.search(inputWord)) {
                            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                            if (!words.contains(inputWord)) {
                                words.add(inputWord);
                            }
                        } else {
                            Log.e(TAG, "word" + inputWord + " doesn't exist in dictionary");
                        }
                    }
                } else {
                    Log.e(TAG, "Not valid input");
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
                trie.insert(line.split("\n")[0]);
            }
            Log.e(TAG, "trie build complete");

            reader.close();
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isAlpha (String s) {
        return s.matches("[a-z]+");
    }



    public void back_button_click(View view) {
        Intent intent = new Intent(DictionaryActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void acknowledge_button_click(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(DictionaryActivity.this).create();
        alertDialog.setTitle(R.string.acknowledgementsTitle);
        String trie = getApplicationContext().getResources().getString(R.string.acknowledgements_Trie);
        String discuss = getApplicationContext().getResources().getString(R.string.acknowledgements_discuss);
        String piazza = getApplicationContext().getResources().getString(R.string.acknowledgements_Piazza);
        String files = getApplicationContext().getResources().getString(R.string.acknowledgements_split_file);
        alertDialog.setMessage
                (piazza + "\n" + "\n" + discuss + "\n" + "\n" +trie + "\n" + "\n" + files);

        alertDialog.show();
    }
}
