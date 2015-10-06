package com.example.kelseyaten.mystreamer3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Main2Activity extends AppCompatActivity {

    public String INPUT = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);


        //final so that you can access the variable inside the method
        final SpotifyApi api = new SpotifyApi();
        final SpotifyService spotify = api.getService();
        
        if( savedInstanceState != null  ){
            INPUT = savedInstanceState.getString("input");
            remake( INPUT, spotify );
        }

        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setBackgroundColor(Color.LTGRAY);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {

                INPUT = s;
                remake(s, spotify);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    public void remake(String s, SpotifyService spotify) {
        spotify.searchArtists(s, new Callback<ArtistsPager>() {
            @Override
            public void success(final ArtistsPager artistsPager, Response response) {
                int count = 0;
                final ArrayList<Artist> artistArray = new ArrayList<>();
                //count to ten so you only see 10 artist names
                for (Artist artist : artistsPager.artists.items) {
                    if (count < 10) {
                        artistArray.add(artist);
                        Log.d("ArtistName", artist.name);
                        count++;
                    }
                }
                if (count == 0 || artistArray.isEmpty()) {
                    Toast.makeText(Main2Activity.this, "Song or Artist Not Found", Toast.LENGTH_SHORT).show();
                }
                //---------------Make the listView-------------
                ListView listView = (ListView) findViewById(R.id.listView);

                Context context = getApplicationContext();
                final ArrayList<String> artistList = new ArrayList<>();
                ArrayList<String> urlStrings = new ArrayList<String>();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent i = new Intent(getApplicationContext(), Main3Activity.class);
                        String artistName  = artistArray.get(position).name;
                        String artistId  =  artistArray.get(position).id;
                        i.putExtra("Artist", artistName );
                        i.putExtra("Id", artistId);


                        startActivity(i);

                    }
                });
                //----------------Go through artist array---------
                for (Artist artist : artistArray) {
                    artistList.add(artist.name);

                    if (!artist.images.isEmpty()) {
                        urlStrings.add(artist.images.get(0).url.toString());
                    } else
                        urlStrings.add("http://www.morecambehigh.com/LIVE/wp-content/uploads/2014/12/music.jpg");
                }
                YourAdapter adapter = new YourAdapter(context, artistList, urlStrings);
                listView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failure", error.getUrl());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

    //to save state of search after rotate etc.

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("input", INPUT);
        super.onSaveInstanceState(savedInstanceState);
    }
}
