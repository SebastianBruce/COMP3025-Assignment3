package com.example.assignment3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface OMDBApi {

    //OMDB API endpoint for searching movies
    @GET
    Call<MovieSearchResponse> searchMovies(@Url String url);

    //OMDB API endpoint for fetching movie details by IMDb ID
    @GET
    Call<Movie> fetchMovieDetails(@Url String url);
}
