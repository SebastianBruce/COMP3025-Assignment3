package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private Button favoritesPageButton;
    private RecyclerView searchRecyclerView;
    private MovieAdapter movieAdapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind views to variables
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        favoritesPageButton = findViewById(R.id.favoritesPageButton);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);

        //Set up RecyclerView
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/") //Base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Set up search button listener
        searchButton.setOnClickListener(v -> {

            //Save entered text
            String query = searchEditText.getText().toString().trim();

            //If text box is not empty, fetch movies
            if (!query.isEmpty()) {
                fetchMovies(query);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            }
        });

        //When clicked open favorites activity
        favoritesPageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    private void fetchMovies(String query) {
        //Clear the previous list of movies
        if (movieAdapter != null) {
            movieAdapter.clearMovies();
        }

        //Search movies based on the user's input
        String searchUrl = "https://www.omdbapi.com/?apikey=6b931edd&s=" + query + "&type=movie";

        OMDBApi omdbApi = retrofit.create(OMDBApi.class);

        //Search movies using the query
        omdbApi.searchMovies(searchUrl).enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    //Save the movies to a list
                    MovieSearchResponse movieSearchResponse = response.body();
                    List<Movie> movieList = movieSearchResponse.getSearch();

                    //If search finds no results
                    if (movieList.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No matching movies found", Toast.LENGTH_SHORT).show();
                    }

                    //Loop through each movie to fetch detailed information using IMDb ID
                    for (Movie movie : movieList) {
                        fetchMovieDetails(movie.getImdbID());
                    }

                //If theres an error
                } else {
                    Log.e("API Error", "Error fetching data: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMovieDetails(String imdbID) {
        //Fetch detailed movie information using the imdbID
        String detailsUrl = "https://www.omdbapi.com/?apikey=6b931edd&i=" + imdbID;

        OMDBApi omdbApi = retrofit.create(OMDBApi.class);

        //Fetch detailed movie information
        omdbApi.fetchMovieDetails(detailsUrl).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie detailedMovie = response.body();

                    //Update the movie list with the detailed information
                    updateRecyclerView(detailedMovie);
                } else {
                    Log.e("API Error", "Error fetching details: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch movie details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView(Movie movie) {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(new ArrayList<>());
            searchRecyclerView.setAdapter(movieAdapter);
        }

        movieAdapter.addMovie(movie);
    }
}
