package com.example.kelseyaten.mystreamer3;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Main3Activity extends AppCompatActivity {

    public String INPUT = "";
    MediaPlayer player = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Bundle extras = getIntent().getExtras();
        String artistName = "";
        String artistId = "";
        if( extras != null){
            artistName = extras.getString("Artist");
            artistId = extras.getString("Id");
            Toast.makeText(Main3Activity.this,"Artist: " + artistName, Toast.LENGTH_SHORT).show();

        }

        final SpotifyApi api = new SpotifyApi();
        final SpotifyService spotify = api.getService();
        spotify.getArtistTopTrack(artistId, "US", new Callback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                final ArrayList<TopTen> topList = new ArrayList<TopTen>();
                int count = 0;
                for (Track track : tracks.tracks) {
                    if (count < 10) {
                        TopTen top = new TopTen();
                        top.setImage(track.album.images.get(0).url.toString().trim());
                        top.setTrack(track.name.toString());
                        top.setName(track.album.name);
                        top.setPlayer(track.preview_url);
                        topList.add(top);
                        count++;
                    }
                }
                ListView listView = (ListView) findViewById(R.id.songs);
                SecondAdapter second = new SecondAdapter(getApplicationContext(), R.layout.songslayout, topList);
                listView.setAdapter(second);
                second.notifyDataSetChanged();

               // Context context = getApplicationContext();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //Intent i = new Intent(getApplicationContext(), Main3Activity.class);
                        Toast.makeText(Main3Activity.this, "Song: "+ topList.get(position).getTrack() , Toast.LENGTH_SHORT).show();
                        String songName  = topList.get(position).getTrack();


                        try{
                            player.reset();
                            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            player.setDataSource(topList.get(position).getPlayer());
                            player.prepare();
                            player.start();
                        } catch (Exception e) {
                        }
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Tracks failure", error.toString());
            }
        });

        Button Backbutton;
        Backbutton=(Button)findViewById(R.id.BackButton);
        Backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                player.reset();
                startActivity(i);
            }
        });
        if (savedInstanceState != null) {
            INPUT = savedInstanceState.getString("input");
            remake(INPUT, spotify);
        }
    }

    public void remake(String s, SpotifyService spotify) {
        ListView listView = (ListView) findViewById(R.id.listView);

        Context context = getApplicationContext();
        final ArrayList<String> songList = new ArrayList<>();
        ArrayList<String> urlStrings = new ArrayList<String>();

        Bundle extras = getIntent().getExtras();
        String artistName = "";
        String artistId = "";
        if( extras != null){
            artistName = extras.getString("Artist");
            artistId = extras.getString("Id");
            Toast.makeText(Main3Activity.this,"Artist: " + artistName, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main3, menu);
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
        player.reset();

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("input", INPUT);
        super.onSaveInstanceState(savedInstanceState);
    }
}
