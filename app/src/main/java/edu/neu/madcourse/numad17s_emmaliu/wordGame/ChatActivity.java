package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import edu.neu.madcourse.numad17s_emmaliu.R;
import edu.neu.madcourse.numad17s_emmaliu.realtimedatabase.models.User;

public class ChatActivity extends AppCompatActivity  {
    String targetUsername;
    String mUsername = GameStatus.getUsername();
    String message;
    String TAG = "Debuging: ";
    Button buttonSend;
    EditText editTextInput;
    String targetToken;
    private static final String SERVER_KEY =
            "key=AAAAEm4qR9g:APA91bEUpUifDgXaMd1I11FbzzuJkqpWSZ2xd1eemQa5R-JZsEsGCDAL5COgKM0Nk806WjJRa4bTwVwZSyDdy24h2s8Ba6vFqzQlapd7TBGdxkXCtV5QhY1d08X1-8h7WY1edfmzOtvb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayList<String> usernames = new ArrayList<>();
        for (User user : GameStatus.getDisplayedUser()) {
            usernames.add(user.username);
        }


        //here I assume user will input different user name
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,usernames);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                targetUsername = usernames.get(position);
                for (User u : GameStatus.getDisplayedUser()) {
                    if (u.username.equals(targetUsername)) {
                        Log.d(TAG, " get Token");
                        targetToken = u.token;
                    }
                }
            }

                //Get selected value of key


            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, " nothing select");
            }
        });

        buttonSend = (Button) findViewById(R.id.buttonSend);
        editTextInput = (EditText) findViewById(R.id.editTextInput);

        editTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                message = s.toString();
                Log.d(TAG, message);
            }

        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(targetToken);
            }
        });

    }

    private void sendMessage(final String targetToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessageToDevice(targetToken);
            }
        }).start();
    }

    private void sendMessageToDevice(String targetToken) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {
            jNotification.put("title", mUsername + " send you a message");
            jNotification.put("body", message);
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            /*
            // We can add more details into the notification if we want.
            // We happen to be ignoring them for this demo.
            jNotification.put("click_action", "OPEN_ACTIVITY_1");
            */

            /***
             * The Notification object is now populated.
             * Next, build the Payload that we send to the server.
             */

            // If sending to a single client
            jPayload.put("to", targetToken); // CLIENT_REGISTRATION_TOKEN);

            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */

            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);


            /***
             * The Payload object is now populated.
             * Send it to Firebase to send the message to the appropriate recipient.
             */
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());
            outputStream.close();

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: " + resp);
                    Toast.makeText(ChatActivity.this,resp,Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

}
