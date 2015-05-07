package com.example.poornima_udacity.spotify_project1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MusicAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] songName;
    private final String[] image;
    private final String[] album;

    public MusicAdapter(Activity context,
                      String[] songName, String[] image, String[] album) {
        super(context, R.layout.list_item_music, songName);
        this.context = context;
        this.songName = songName;
        this.image = image;
        this.album = album;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //LayoutInflater inflater = context.getLayoutInflater();
        View rootView = LayoutInflater.from(context).inflate(R.layout.list_item_music, parent, false);
        //View rowView= inflater.inflate(R.layout.list_item_music, null, true);

        ImageView iconView = (ImageView) rootView.findViewById(R.id.list_item_icon);
        //iconView.setImageResource(R.drawable.ic_launcher);
        Picasso.with(context).load(image[position]).into(iconView);

        TextView songNameView = (TextView) rootView.findViewById(R.id.list_item_song_name);
        songNameView.setText(songName[position]);
        // Read weather forecast from cursor
        //String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        // Find TextView and set weather forecast on it

        TextView albumView = (TextView) rootView.findViewById(R.id.list_item_albumname_textview);
        albumView.setText(album[position]);

        return rootView;
    }
}
