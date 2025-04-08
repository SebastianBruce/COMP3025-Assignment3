package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView moviePosterImageView;
    private TextView movieTitleTextView, moviePlotTextView;
    private Button favoriteButton;
    private Button backButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //Bind views to variables
        moviePosterImageView = findViewById(R.id.moviePosterImageView);
        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        moviePlotTextView = findViewById(R.id.moviePlotTextView);
        favoriteButton = findViewById(R.id.addFavorite);
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

        //Favorite button to add the movie to the user's favorites in Firestore
        favoriteButton.setOnClickListener(v -> addMovieToFavorites(movie));

        //Back button to finish the activity and go back
        backButton.setOnClickListener(v -> finish());
    }

    //Add movie to the current user's favorites in Firestore
    private void addMovieToFavorites(Movie movie) {
        //Get the current user
        String userId = mAuth.getCurrentUser().getUid();

        //Create a movie map to store in Firestore
        MovieFavorite favoriteMovie = new MovieFavorite(
                movie.getTitle(),
                movie.getPlot(),
                movie.getPoster(),
                movie.getYear(),       // Pass the year
                movie.getRated(),      // Pass the rated info
                movie.getMetascore()      // Pass the rating info
        );

        //Add the movie to Firestore under the user's document
        db.collection("users")
                .document(userId)
                .collection("favorites")
                .document(movie.getTitle()) //Use movie title as document ID
                .set(favoriteMovie)
                .addOnSuccessListener(aVoid -> {
                    //Show success message
                    Toast.makeText(MovieDetailsActivity.this, "Movie added to favorites", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    //Show failure message
                    Toast.makeText(MovieDetailsActivity.this, "Error adding to favorites", Toast.LENGTH_SHORT).show();
                });
    }

}
