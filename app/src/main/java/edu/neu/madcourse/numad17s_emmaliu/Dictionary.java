package edu.neu.madcourse.numad17s_emmaliu;

import android.content.Intent;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



public class Dictionary extends AppCompatActivity  {
    private static final String TAG = "Test File existense";
    ArrayList<String> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        words.add("Hello");
        words.add("World");

        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.mylist_layout,words);


        ListView listView = (ListView) findViewById(R.id.word_list);
        listView.setAdapter(adapter);

        //test if json file already exist

        AssetManager manager = getResources().getAssets();
        InputStream is = null;
        try {
            is = manager.open("shortWordlist.txt");
        } catch (IOException ex) {
            Log.e(TAG, "cannot get file");

        } finally {
            if (is != null) {
                Log.i(TAG, "Wordlist file is here");
            }
        }

        try {
            readData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void readData() throws IOException {
        String data = "";
        try {
            InputStream is = getAssets().open("shortWordlist.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            data = new String(buffer);
            Log.e(data, "This is words");
            String[] words = data.split("\n");

            Trie trie = new Trie();
            for (String w : words) {
                trie.insert(w);
            }
            Log.i(TAG, "trie build complete");


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
