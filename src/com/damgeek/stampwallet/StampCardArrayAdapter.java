package com.damgeek.stampwallet;

import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StampCardArrayAdapter extends ArrayAdapter<StampCard> {
	  private final Context context;
	  private final List<StampCard> cards;

	  public StampCardArrayAdapter(Context context, List<StampCard> values) {
	    super(context, R.layout.card_row, values);
	    this.context = context;
	    this.cards = values;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.card_row, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.label);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    textView.setText(cards.get(position).getShopName());
	    // Change the icon for Windows and iPhone
	    String logoPath = cards.get(position).getShopLogoPath();
	    InputStream is = getClass().getResourceAsStream("/drawable-hdpi/" + logoPath);
	    imageView.setImageDrawable(Drawable.createFromStream(is, ""));
	    
	    return rowView;
	  }
}
