package com.example.assignment3;

import java.io.Serializable;

public class Movie implements Serializable {

    //Variables matching the json info
    private String Title;
    private String Year;
    private String imdbID;
    private String Poster;
    private String Rated;
    private String Metascore;
    private String Plot;

    //Getters
    public String getTitle() {
        //Truncate title if longer than 20 characters
        if (Title.length() > 20) {
            Title = Title.substring(0, 17) + "...";
        }
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getPoster() {
        return Poster;
    }

    public String getRated() {
        return Rated;
    }

    public String getMetascore() {
        return Metascore + "%";
    }

    public String getPlot() {
        return Plot;
    }
}
