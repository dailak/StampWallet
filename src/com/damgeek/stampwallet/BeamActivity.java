package com.damgeek.stampwallet;

import java.nio.charset.Charset;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

import android.nfc.*;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.content.Intent;
import android.os.Handler;

public class BeamActivity extends Activity implements CreateNdefMessageCallback, OnNdefPushCompleteCallback{

    private static final String MIME_TYPE = "Application/com.damgeek.stampwallet";
    NfcAdapter mNfcAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beam);
		
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
		getMenuInflater().inflate(R.menu.activity_beam, menu);
		return true;
	}

	private static final int MESSAGE_SENT = 1;
    
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
