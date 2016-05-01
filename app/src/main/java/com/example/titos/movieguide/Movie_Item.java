package com.example.titos.movieguide;

/**
 * Created by TITOS on 4/20/2016.
 */
public class Movie_Item {
    private String vote_average;
    private String release_data;
    private String overview;
    private String original_title;
    private  String id ;
    private String poster_path;


    public Movie_Item()
    {

    }
    public Movie_Item(String rel, String over, String poster, String original, String Id, String vote ){

        release_data = rel;
        overview = over;
        poster_path = poster;
        original_title = original;
        vote_average = vote;
        id = Id;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_data() {
        return release_data;
    }

    public void setRelease_data(String release_data) {
        this.release_data = release_data;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }
}

