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

public class MovieEditActivity extends AppCompatActivity {

    private ImageView moviePosterImageView;
    private TextView movieTitleTextView, moviePlotEditText;
    private Button updateButton;
    private Button deleteButton;
    private Button backButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_edit);

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //Bind views to variables
        moviePosterImageView = findViewById(R.id.moviePosterImageView);
        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        moviePlotEditText = findViewById(R.id.moviePlotEditText);
        backButton = findViewById(R.id.backButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        //Get the MovieFavorite object from the Intent
        Intent intent = getIntent();
        MovieFavorite movie = intent.getParcelableExtra("movie");

        if (movie != null) {
            //Set data to the views
            movieTitleTextView.setText(movie.getTitle());
            moviePlotEditText.setText(movie.getPlot());

            //Load the movie poster using Glide
            Glide.with(this).load(movie.getPoster()).into(moviePosterImageView);

            //Back button to finish the activity and go back
            backButton.setOnClickListener(v -> finish());

            //Update button to update the plot in Firestore
            updateButton.setOnClickListener(v -> {
                String updatedPlot = moviePlotEditText.getText().toString().trim();
                if (!updatedPlot.isEmpty()) {
                    updateMoviePlot(movie, updatedPlot);
                } else {
                    Toast.makeText(MovieEditActivity.this, "Plot cannot be empty", Toast.LENGTH_SHORT).show();
                }
            });

            //Delete button to delete the movie from Firestore
            deleteButton.setOnClickListener(v -> deleteMovie(movie));
        } else {
            Toast.makeText(MovieEditActivity.this, "Movie data is missing", Toast.LENGTH_SHORT).show();
        }
    }

    //Update the plot of the movie in Firestore
    private void updateMoviePlot(MovieFavorite movie, String updatedPlot) {
        String userId = mAuth.getCurrentUser().getUid();

        String movieId = movie.getTitle();

        db.collection("users")
                .document(userId)
                .collection("favorites")
                .document(movieId)  //Use movie title as document ID
                .update("plot", updatedPlot)
                .addOnSuccessListener(aVoid -> {
                    //Show success message
                    Toast.makeText(MovieEditActivity.this, "Plot updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    //Show failure message
                    Toast.makeText(MovieEditActivity.this, "Error updating plot", Toast.LENGTH_SHORT).show();
                });
    }

    //Delete the movie from Firestore
    private void deleteMovie(MovieFavorite movie) {
        String userId = mAuth.getCurrentUser().getUid();

        String movieId = movie.getTitle();

        db.collection("users")
                .document(userId)
                .collection("favorites")
                .document(movieId)  //Use movie title as document ID
                .delete()
                .addOnSuccessListener(aVoid -> {
                    //Show success message
                    Toast.makeText(MovieEditActivity.this, "Movie deleted successfully", Toast.LENGTH_SHORT).show();
                    //Finish the activity and return to the previous screen
                    finish();
                })
                .addOnFailureListener(e -> {
                    //Show failure message
                    Toast.makeText(MovieEditActivity.this, "Error deleting movie", Toast.LENGTH_SHORT).show();
                });
    }
}
