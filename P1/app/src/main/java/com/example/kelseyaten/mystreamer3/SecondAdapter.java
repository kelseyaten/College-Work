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

import kaaes.spotify.webapi.android.models.Artist;

public class SecondAdapter extends ArrayAdapter<TopTen>{
    ArrayList<TopTen> top = new ArrayList<TopTen>();

    public SecondAdapter(Context context, int id, ArrayList<TopTen> top) {
        super(context, id, top);
        this.top = top;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = convertView;

        if (v == null) {
            v = inflater.inflate(R.layout.songslayout, parent, false);
        }

        // Now we can fill the layout with the right values
        TextView songName= (TextView) v.findViewById(R.id.label);
        TextView albumName= (TextView) v.findViewById(R.id.album);
        ImageView albumCover = (ImageView) v.findViewById(R.id.img_thumbnail);

        TopTen yourItem = top.get(position);

        if(yourItem.getName() != null) {
            albumName.setText(yourItem.getName());
        }
        if(yourItem.getTrack() != null) {
            songName.setText(yourItem.getTrack());
        }
        if( yourItem.getImage() != null) {
            Picasso.with( getContext() )
                    .load(yourItem.getImage())
                    .into(albumCover);
        }
        return v;
    }
}