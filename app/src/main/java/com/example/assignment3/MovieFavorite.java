package com.example.assignment3;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieFavorite implements Parcelable {

    //Variables matching the json info
    private String title;
    private String plot;
    private String poster;
    private String year;
    private String rated;
    private String rating;

    public MovieFavorite() {}

    //Constructor
    public MovieFavorite(String title, String plot, String poster, String year, String rated, String rating) {
        this.title = title;
        this.plot = plot;
        this.poster = poster;
        this.year = year;
        this.rated = rated;
        this.rating = rating;
    }

    protected MovieFavorite(Parcel in) {
        title = in.readString();
        plot = in.readString();
        poster = in.readString();
        year = in.readString();
        rated = in.readString();
        rating = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(plot);
        dest.writeString(poster);
        dest.writeString(year);
        dest.writeString(rated);
        dest.writeString(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieFavorite> CREATOR = new Creator<MovieFavorite>() {
        @Override
        public MovieFavorite createFromParcel(Parcel in) {
            return new MovieFavorite(in);
        }

        @Override
        public MovieFavorite[] newArray(int size) {
            return new MovieFavorite[size];
        }
    };

    //Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
