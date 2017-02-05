package edu.neu.madcourse.numad17s_emmaliu;

import android.content.Intent;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.util.Log;
import com.google.gson.Gson;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class Dictionary extends AppCompatActivity  {
    private static final String TAG = "Test File existense";
    ArrayList<String> words = new ArrayList<>();
    String inputWord = "";
    Trie trie = new Trie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);


        final EditText editText = (EditText) findViewById(R.id.editText);
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
                if (trie.search(inputWord)) {
                    Log.e(TAG, "Yes, find the words");
                    if (!words.contains(inputWord)) {
                        words.add(inputWord);
                    }
                } else {
                    Log.e(TAG, "word is not exist");
                }
            }
        });



        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.mylist_layout,words);


        ListView listView = (ListView) findViewById(R.id.word_list);
        listView.setAdapter(adapter);


//        AssetManager manager = getResources().getAssets();
//        InputStream is = null;
//        try {
//            is = manager.open("wordlist.txt");
//        } catch (IOException ex) {
//            Log.e(TAG, "cannot get file");
//
//        } finally {
//            if (is != null) {
//                Log.i(TAG, "Wordlist file is here");
//            }
//        }

        try {
            readData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }



    }

    public void readData() throws IOException {
        try {
            AssetManager manager = getResources().getAssets();
            InputStream is = manager.open("un.txt");
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

    public void clear(View view) {
        EditText et = (EditText) findViewById(R.id.editText);
        et.setText("");
        words.clear();
    }


}
