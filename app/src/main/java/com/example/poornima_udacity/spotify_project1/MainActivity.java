package com.example.poornima_udacity.spotify_project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.poornima_udacity.spotify_project1.DetailActivity.DetailFragment;


public class MainActivity extends ActionBarActivity {

    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.now_playing_detail_container) != null) {
                        // The detail container view will be present only in the large-screen layouts
                                // (res/layout-sw600dp). If this view is present, then the activity should be
                                        // in two-pane mode.
                                                mTwoPane = true;
                        // In two-pane mode, show the detail view in this activity by
                                // adding or replacing the detail fragment using a
                                        // fragment transaction.
                                               if (savedInstanceState == null) {
                                getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.now_playing_detail_container, new DetailFragment(), null)
                                                .commit();
                            }
                    } else {
                        mTwoPane = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class SpotifyFragment extends Fragment {

        private MusicAdapter musicAdapter;

        String songName[] = {"Closer", "Where Does The Good Go", "Walking With A Ghost", "I Was A Fool", "Everything is AWESOME!!!"};

        String album[] = {"Heartthrob", "So Jealous", "So Jealous", "Heartthrob", "The Lego® Movie: Original Motion..." };

        String image[] = {"https://i.scdn.co/image/3f3ccc4e2277156b3d1f7335191348c21d32d859","https://i.scdn.co/image/c351a592f40a7ecaff9c629f61b194a38ecf47b7",
                "https://i.scdn.co/image/c351a592f40a7ecaff9c629f61b194a38ecf47b7","https://i.scdn.co/image/3f3ccc4e2277156b3d1f7335191348c21d32d859","https://i.scdn.co/image/1fbdcc7c4464d74bcac3b3b400e4024a056984e9" };

        public SpotifyFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            musicAdapter = new MusicAdapter(getActivity(), songName, image, album);

                    View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    // Get a reference to the ListView, and attach this adapter to it.
                    ListView listView = (ListView) rootView.findViewById(R.id.listview_music);
                    listView.setAdapter(musicAdapter);

                    // We'll call our MainActivity
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               Intent intent = new Intent(getActivity(), DetailActivity.class);
                               startActivity(intent);
                        }
                    } );
            return rootView;
        }
        //TO DO - Spotify Web API
        /** public class FetchMusicTask extends AsyncTask<String, Void, String[]> {

            private final String LOG_TAG = FetchMusicTask.class.getSimpleName();


              //Take the String representing the complete forecast in JSON Format and
              //pull out the data we need to construct the Strings needed for the wireframes.
              //<p/>
              //Fortunately parsing is easy:  constructor takes the JSON string and converts it
              //into an Object hierarchy for us.

            private String getArtistDataFromJson(String musicJsonStr) throws JSONException {

                // These are the names of the JSON objects that need to be extracted.
                final String SPOTIFY_ITEMS = "items";
                final String SPOTIFY_ID = "id";

                JSONObject musicJson = new JSONObject(musicJsonStr);
                JSONArray musicArray = musicJson.getJSONArray(SPOTIFY_ITEMS);

                // OWM returns daily forecasts based upon the local time of the city that is being
                // asked for, which means that we need to know the GMT offset to translate this data
                // properly.

                // Since this data is also sent in-order and the first day is always the
                // current day, we're going to take advantage of that to get a nice
                // normalized UTC date for all of our weather.

                String artistIdObject = null;

                // Get the JSON object representing the day
                JSONObject artistObject = musicArray.getJSONObject(0);

                // description is in a childForecast array called "weather", which is 1 element long.
                artistIdObject = artistObject.getString(SPOTIFY_ID);

                return artistIdObject;

            }


             // Take the String representing the complete forecast in JSON Format and
             // pull out the data we need to construct the Strings needed for the wireframes.
             // <p/>
             // Fortunately parsing is easy:  constructor takes the JSON string and converts it
             // into an Object hierarchy for us.

            private String[] getTopTracksDataFromJson(String musicJsonStr) throws JSONException {

                // These are the names of the JSON objects that need to be extracted.
                final String OWM_LIST = "list";
                final String OWM_WEATHER = "weather";
                final String OWM_TEMPERATURE = "temp";
                final String OWM_MAX = "max";
                final String OWM_MIN = "min";
                final String OWM_DESCRIPTION = "main";

                JSONObject musicJson = new JSONObject(musicJsonStr);
                JSONArray tracksArray = musicJson.getJSONArray(OWM_LIST);

                // OWM returns daily forecasts based upon the local time of the city that is being
                // asked for, which means that we need to know the GMT offset to translate this data
                // properly.

                // Since this data is also sent in-order and the first day is always the
                // current day, we're going to take advantage of that to get a nice
                // normalized UTC date for all of our weather.

                String resultStrs[] = new String[tracksArray.length()];
                for (int i = 0; i < tracksArray.length(); i++) {
                    // For now, using the format "Day, description, hi/low"
                    String day;
                    String description;
                    String highAndLow;

                    // Get the JSON object representing the day
                    JSONObject dayForecast = tracksArray.getJSONObject(i);

                    // description is in a child array called "weather", which is 1 element long.
                    JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                    description = weatherObject.getString(OWM_DESCRIPTION);

                    // Temperatures are in a child object called "temp".  Try not to name variables
                    // "temp" when working with temperature.  It confuses everybody.
                    JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                    double high = temperatureObject.getDouble(OWM_MAX);
                    double low = temperatureObject.getDouble(OWM_MIN);

                    //resultStrs[i] = day + " - " + description + " - " + highAndLow;
                }
                return resultStrs;

            }

            @Override
            protected String[] doInBackground(String... params) {

                // If there's no zip code, there's nothing to look up.  Verify size of params.
                if (params.length == 0) {
                    return null;
                }

                // These two need to be declared outside the try/catch
                // so that they can be closed in the finally block.
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                // Will contain the raw JSON response as a string.
                String musicJsonStr = null;
                String type = "artist";

                String artist_album_id = null;

                try {
                    // Construct the URL for the OpenWeatherMap query
                    // Possible parameters are avaiable at OWM's forecast API page, at
                    // http://openweathermap.org/API#forecast
                    final String MUSIC_BASE_URL =
                            "https://api.spotify.com/v1/search?";
                    final String QUERY_PARAM = "q";
                    final String TYPE_PARAM = "type";


                    Uri builtUri = Uri.parse(MUSIC_BASE_URL).buildUpon()
                            .appendQueryParameter(QUERY_PARAM, params[0])
                            .appendQueryParameter(TYPE_PARAM, type)
                            .build();

                    URL url = new URL(builtUri.toString());

                    Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    musicJsonStr = buffer.toString();

                    Log.v(LOG_TAG, "Song string: " + musicJsonStr);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in attemping
                    // to parse it.
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }

                try {
                    artist_album_id = getArtistDataFromJson(musicJsonStr);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

                try {
                    // Construct the URL for the OpenWeatherMap query
                    // Possible parameters are avaiable at OWM's forecast API page, at
                    // http://openweathermap.org/API#forecast
                    final String SONG_BASE_URL =
                            "https://api.spotify.com/v1/artists/";
                    final String path = artist_album_id + "/top-tracks?";

                    final String COUNTRY_PARAM = "country";

                    Uri builtUri = Uri.parse(SONG_BASE_URL).buildUpon().appendPath(path)
                            .appendQueryParameter(COUNTRY_PARAM, "US")
                            .build();

                    URL url = new URL(builtUri.toString());

                    Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    musicJsonStr = buffer.toString();

                    Log.v(LOG_TAG, "Song string: " + musicJsonStr);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in attemping
                    // to parse it.
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }

                try {
                    return getTopTracksDataFromJson(musicJsonStr);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }


                // This will only happen if there was an error getting or parsing the forecast.
                return null;
            }

            @Override
            protected void onPostExecute(String[] result) {
                if (result != null) {
                    musicAdapter.clear();
                    for (String dayForecastStr : result) {
                        musicAdapter.add(dayForecastStr);
                    }
                    // New data is back from the server.  Hooray!
                }
            }
        } */
    }

}
