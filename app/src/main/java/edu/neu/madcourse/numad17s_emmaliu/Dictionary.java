package edu.neu.madcourse.numad17s_emmaliu;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class Dictionary extends AppCompatActivity {
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
