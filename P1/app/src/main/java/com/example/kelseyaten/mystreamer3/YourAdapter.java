package com.example.kelseyaten.mystreamer3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class YourAdapter extends ArrayAdapter<String>{

    private final ArrayList<String> urls;
    private final ArrayList<String> names;

    public YourAdapter(Context context, ArrayList<String> names, ArrayList<String> urls) {
        super(context, 0, names);

        this.names = names;
        this.urls=urls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = convertView;

        if (v == null) {
            v = inflater.inflate(R.layout.rowlayout, parent, false);
        }
        // Now we can fill the layout with the right values
        TextView textView= (TextView) v.findViewById(R.id.label);
        ImageView imageView = (ImageView) v.findViewById(R.id.img_thumbnail);

        String ImageUrl = urls.get( position );
        String ArtistSong = names.get( position );


        if(textView != null) {
            textView.setText( ArtistSong );

        }
        if( imageView != null) {

            Picasso.with( getContext() )
                    .load(ImageUrl)
                    .into(imageView);
        }
        return v;

    }
}