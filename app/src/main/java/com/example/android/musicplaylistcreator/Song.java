package com.example.android.musicplaylistcreator;

/**
 * Created by Jin on 2/14/17.
 */

public class Song {

    public static int count = 0;
    private int id;
    private String title;
    private String artist;
    private Boolean checked = false;
    private String videoUrl;
    private String songUrl;
    private String artistUrl;
    private Integer imageId;

    Song(String title, String artist, String videoUrl,
         String songUrl, String artistUrl, int imageId){
        id = count;
        count++;
        this.title = title;
        this.artist = artist;
        this.videoUrl = videoUrl;
        this.songUrl = songUrl;
        this.artistUrl = artistUrl;
        this.imageId = imageId;
    }

    public int id(){ return id; }
    public String title(){
        return title;
    }
    public String artist(){
        return artist;
    }
    public Boolean checked(){ return checked; }
    public String videoUrl(){ return videoUrl; }
    public String songUrl(){ return songUrl; }
    public String artistUrl(){ return artistUrl; }
    public Integer imageId(){ return imageId; }

    public void setChecked(Boolean checked){ this.checked = checked; }

    @Override
    public String toString(){
        return title + "\nBy: " + artist;
    }
}
