/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
***/
package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.madcourse.numad17s_emmaliu.R;

public class ControlFragment extends Fragment {

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            final Bundle savedInstanceState) {
      View rootView =
            inflater.inflate(R.layout.fragment_control, container, false);
      View main = rootView.findViewById(R.id.button_main);
      View restart = rootView.findViewById(R.id.button_restart);
      View submit = rootView.findViewById(R.id.button_check);

      main.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            getActivity().finish();
         }
      });
      restart.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            ((GameActivity) getActivity()).restartGame();
         }
      });

      submit.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View view) {
             System.out.println("clcik me ......");
             if (savedInstanceState != null) {
                 String s = savedInstanceState.toString();
                 System.out.println(s + "ccccccc");
             } else {
                 System.out.println(" no no no ");
             }


         }
      });
      return rootView;
   }

}
