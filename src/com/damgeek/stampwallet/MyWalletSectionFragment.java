package com.damgeek.stampwallet;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class MyWalletSectionFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public MyWalletSectionFragment() {
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      ArrayList<StampCard> cards = new ArrayList<StampCard>();
      cards.add(new StampCard("Park n Shop","vendor1.jpeg", 3));
      cards.add(new StampCard("FroYo Yogurt", "vendor2.gif", 2));
    		  
      ArrayAdapter<StampCard> adapter = new ArrayAdapter<StampCard>(getActivity(),
          android.R.layout.simple_list_item_1, cards);
      setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Create a new TextView and set its text to the fragment's section
        // number argument value.
    	View result = inflater.inflate(R.layout.my_wallet, container, false);
    	//Button sendBtn = (Button)result.findViewById(R.id.button_send);
        return result;
    }
}
