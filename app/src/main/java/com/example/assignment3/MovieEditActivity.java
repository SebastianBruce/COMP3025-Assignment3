package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieEditActivity extends AppCompatActivity {

    private ImageView moviePosterImageView;
    private TextView movieTitleTextView, moviePlotEditText;
    private Button favoriteButton;
    private Button deleteButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_edit);

        //Bind views to variables
        moviePosterImageView = findViewById(R.id.moviePosterImageView);
        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        moviePlotEditText = findViewById(R.id.moviePlotEditText);
        backButton = findViewById(R.id.backButton);

        //Get the Movie object from the Intent
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movie");

        if (movie != null) {
            //Set data to the views
            movieTitleTextView.setText(movie.getTitle());
            moviePlotEditText.setText("Plot: " + movie.getPlot());

            //Load the movie poster
            Glide.with(this).load(movie.getPoster()).into(moviePosterImageView);
        }

        //Back button to finish the activity and go back
        backButton.setOnClickListener(v -> finish());
    }
}
