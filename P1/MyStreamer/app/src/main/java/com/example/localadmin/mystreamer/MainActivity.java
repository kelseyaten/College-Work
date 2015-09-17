package com.example.localadmin.mystreamer;

import android.content.Context;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this)
                .load("http://i.imgur.com/s6Nkq0c.png")
                .into(imageView);
        //final so that you can access the variable inside the method
        final SpotifyApi api = new SpotifyApi();
        final SpotifyService spotify = api.getService();

        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setBackgroundColor(Color.LTGRAY);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                spotify.searchArtists(s, new Callback<ArtistsPager>() {
                    @Override
                    public void success(ArtistsPager artistsPager, Response response) {
                        int count = 0;
                        ArrayList<Artist> artistArray = new ArrayList<>();

                            //count to ten so you only see 10 artist names
                            for( Artist artist: artistsPager.artists.items ) {
                                if(count < 10) {
                                    artistArray.add(artist);
                                    Log.d("ArtistName", artist.name);
                                    count++;
                                }
                            }


                        //----Make the listView----
                        ListView listView = (ListView) findViewById(R.id.listView);
                        Context context = getApplicationContext();
                        ArrayList<String> artistList = new ArrayList<>();

                        for (Artist artist: artistArray) {
                            artistList.add(artist.name);
                        }

                        
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, artistList);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Artist Failure", error.getUrl() );
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

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
}
