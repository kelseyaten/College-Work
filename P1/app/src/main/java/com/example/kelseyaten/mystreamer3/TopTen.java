package com.example.kelseyaten.mystreamer3;

/**
 * Created by kelseyaten on 10/6/15.
 */
public class TopTen {

    public void setName(String name) {
        this.name = name;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    private String player;

    private String name;
    private String track;
    private String image;


    public String getTrack( ) {
        return track;
    }
    public String getName(){
        return name;
    }
    public String getImage(){
        return image;
    }

}
