package com.example.poornima_udacity.spotify_project1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.now_playing_detail_container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {
        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            setUpSeekBar(rootView);
            setUpPlaybackButtons(rootView);
            ImageView albumArt = (ImageView) rootView.findViewById(R.id.imageview_album_art);

            //Picasso.with(getActivity().getApplicationContext()).load("https://i.scdn.co/image/a78a0c21745c15f6114602b7c9008d7abb90a8bf").into(albumArt);
            return rootView;
        }

        private void setUpSeekBar(View view) {
            // MOCK value: Song duration for "Everything is AWESOME!!!" in milliseconds
            final int SONG_DURATION = 163423;
            SeekBar musicSeekBar = (SeekBar) view.findViewById(R.id.seekbar_music_progress);
            musicSeekBar.setMax(SONG_DURATION);
            final TextView currentPlaybackTime = (TextView) view.findViewById(R.id.seekbar_min_duration);
            musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    // modify current duration timestamp based on seek position
                    currentPlaybackTime.setText(getTimeString(i));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            // Set up seek bar for the current song
            TextView maxTimeLabel = (TextView) view.findViewById(R.id.seekbar_max_duration);
            maxTimeLabel.setText(getTimeString(SONG_DURATION));
        }

        private void setUpPlaybackButtons(View view) {
            // Toggle play/pause button state
            final ImageButton playPauseButton = (ImageButton) view.findViewById(R.id.button_play_pause);
            /*playPauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (playPauseButton.getText().equals(getString(R.string.media_control_play))) {
                        playPauseButton.setText(getString(R.string.media_control_pause));
                    } else {
                        playPauseButton.setText(getString(R.string.media_control_play));
                    }
                }
            });
        }*/
        }

        /**
         * Given a duration in milliseconds, returns a string in the format %m:%ss
         * @param milliseconds the time to get the current time string
         * @return the time string based on milliseconds.
         */
        private static String getTimeString(int milliseconds) {
            final int MILLISECONDS_PER_SECOND = 1000;
            final int SECONDS_PER_MINUTE = 60;

            int totalSeconds = milliseconds / MILLISECONDS_PER_SECOND;

            int timeSeconds = totalSeconds % SECONDS_PER_MINUTE;
            int timeMinutes = totalSeconds / SECONDS_PER_MINUTE;
            return String.format("%d:%02d", timeMinutes, timeSeconds);
        }
    }
}
