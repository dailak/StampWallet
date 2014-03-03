package com.damgeek.stampwallet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FindShopsSectionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public FindShopsSectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Create a new TextView and set its text to the fragment's section
        // number argument value.
    	View result = inflater.inflate(R.layout.stamp_card, container, false);
    	//Button sendBtn = (Button)result.findViewById(R.id.button_send);
    	
    	EditText editor=(EditText)result.findViewById(R.id.message_text);
        int position=getArguments().getInt(ARG_SECTION_NUMBER, -1);
        editor.setText(String.format(getString(R.string.hint), position));
       
        return result;
    }
}
