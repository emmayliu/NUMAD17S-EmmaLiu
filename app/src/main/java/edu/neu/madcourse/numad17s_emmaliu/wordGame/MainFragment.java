/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/
package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.util.Log;

import edu.neu.madcourse.numad17s_emmaliu.R;

public class MainFragment extends Fragment {

    private AlertDialog mDialog;
    private View buttonContinue;
    private Button buttonScoreboard;
    private Button buttonLeaderboard;
    String TAG = "TAG for debugging";

    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView =
                inflater.inflate(R.layout.fragment_main, container, false);
        // Handle buttons here...
        View newButton = rootView.findViewById(R.id.new_button);
        buttonContinue = rootView.findViewById(R.id.continue_button);
        View aboutButton = rootView.findViewById(R.id.about_button);
        View acknowledgeButton = rootView.findViewById(R.id.acknowledge1);
        Switch mySwitch = (Switch) rootView.findViewById(R.id.switch1);
        EditText editTextUsername = (EditText) rootView.findViewById(R.id.editTextUsername);
        buttonScoreboard = (Button) rootView.findViewById(R.id.buttonScoreboard);
        buttonLeaderboard = (Button) rootView.findViewById(R.id.buttonLeaderboard);


        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString();
                Log.d(TAG, username);
                GameStatus.setUsername(username);
                shared_preferences =
                        PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
                shared_preferences_editor = shared_preferences.edit();


                if (shared_preferences.getString("username", null) == null) {
                    shared_preferences_editor.putString("username", username);
                } else {
                    String temp = shared_preferences.getString("username", null);
                    temp = temp + "*" + username;
                    shared_preferences_editor.putString("username", temp);
                }
                shared_preferences_editor.apply();

                System.out.println("username in shared_preferences in afterTextChanged " +
                        shared_preferences.getString("username", "DEFAULT"));
            }

        });

        mySwitch.setChecked(false);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GameStatus.stopMusic();
                    GameStatus.isPlaying = false;
                } else {
                    GameStatus.isPlaying = true;
                    GameStatus.playMusic(getActivity(), R.raw.office);
                }
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameStatus.setRestoreStatus(true);
                Intent intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra(GameActivity.KEY_RESTORE, true);
                getActivity().startActivity(intent);
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.about_text);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                mDialog = builder.show();
            }
        });
        acknowledgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage(R.string.acknowledge_text);
                builder1.setCancelable(false);
                builder1.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                mDialog = builder1.show();
            }
        });

        buttonScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScoreboardActivity.class);
                getActivity().startActivity(intent);
            }
        });

        buttonLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LeaderboardActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        // Get rid of the about dialog if it's still up
        if (mDialog != null)
            mDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!GameStatus.getRestroeStatus()) {
            buttonContinue.setVisibility(View.GONE);
        } else {
            buttonContinue.setVisibility(View.VISIBLE);
        }
    }
}

