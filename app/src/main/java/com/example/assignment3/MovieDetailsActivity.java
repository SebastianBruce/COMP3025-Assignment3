package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView moviePosterImageView;
    private TextView movieTitleTextView, movieYearTextView, movieRatedTextView, movieMetascoreTextView,
            movieReleasedTextView, movieRuntimeTextView, movieGenreTextView, movieDirectorTextView,
            movieWriterTextView, movieActorsTextView, moviePlotTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Bind views to variables
        moviePosterImageView = findViewById(R.id.moviePosterImageView);
        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        moviePlotTextView = findViewById(R.id.moviePlotTextView);
        backButton = findViewById(R.id.backButton);

        //Get the Movie object from the Intent
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movie");

        if (movie != null) {
            //Set data to the views
            movieTitleTextView.setText(movie.getTitle());
            moviePlotTextView.setText("Plot: " + movie.getPlot());

            //Load the movie poster
            Glide.with(this).load(movie.getPoster()).into(moviePosterImageView);
        }

        //Back button to finish the activity and go back
        backButton.setOnClickListener(v -> finish());
    }
}
