package com.damgeek.stampwallet;

import java.nio.charset.Charset;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, CreateNdefMessageCallback, OnNdefPushCompleteCallback {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    private static final String MIME_TYPE = "Application/com.damgeek.stampwallet";
    NfcAdapter mNfcAdapter;
	private static final int MESSAGE_SENT = 1;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        
        //NFC Stuffs here
	    mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
	    if (mNfcAdapter == null) {
	    	Toast.makeText(this, "NFC is not found in this device.", Toast.LENGTH_LONG).show();
	    }
	    else {
	    	mNfcAdapter.setNdefPushMessageCallback(this, this);
	    	mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
	    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public void send_clicked(View view) {
    	EditText editor = (EditText)findViewById(R.id.message_text);
    	Toast.makeText(this, String.format(getString(R.string.sending_msg), editor.getText()), Toast.LENGTH_LONG).show();
    }
    
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
        	Fragment fragment;
        	switch (position) {
            case 0: 
            	fragment = new MyWalletSectionFragment();
            	break;
            case 1:
            	fragment = new FindShopsSectionFragment();
            	break;
            default:
            	fragment = new Fragment();
        	}
            Bundle args = new Bundle();
            args.putInt(MyWalletSectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
            
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_my_wallet).toUpperCase();
                case 1:
                    return getString(R.string.title_find_shops).toUpperCase();
            }
            return null;
        }
    }

    //NFC Stuffs here
    private final Handler mHandler = new Handler() {
    	@Override
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
    		case MESSAGE_SENT:
    			Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
    			break;    		
    		}    		
    	}
    };
    
    @Override
    public void onNdefPushComplete(NfcEvent arg0) {
    	mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();    	
    }
    
    @Override
    public NdefMessage createNdefMessage(NfcEvent event){
    	String text = "Beam Test";
    	NdefMessage msg = new NdefMessage(new NdefRecord[] {
    			createMimeRecord(MIME_TYPE, text.getBytes())});    	
    	return msg;
    }
    
    /**
     * Creates a custom MIME type encapsulated in an NDEF record
     */
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }
    
    @Override
    public void onNewIntent(Intent intent) {
    	setIntent(intent);    
    }

    @Override
    public void onResume() {
    	super.onResume();
    	
    	if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
    		processIntent(getIntent());
    	
    	}
    }
    
    void processIntent(Intent intent){
    	Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
    	
    	NdefMessage msg = (NdefMessage) rawMsgs[0];
    	
    	String payload = new String(msg.getRecords()[0].getPayload());
    	Toast.makeText(getApplicationContext(), "Message recevied over beam: " + payload, Toast.LENGTH_LONG).show();
    
    }
    
}
