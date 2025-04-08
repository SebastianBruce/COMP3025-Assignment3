package com.example.assignment3;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private List<MovieFavorite> favoritesList;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //Initialize RecyclerView and set its layout manager
        recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoritesList = new ArrayList<>();
        adapter = new FavoritesAdapter(favoritesList);
        recyclerView.setAdapter(adapter);

        //Initialize Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        loadFavorites();  // Load the user's favorite movies from Firestore

        //Close activity when clicked
        findViewById(R.id.searchPageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // End the activity
            }
        });
    }

    private void loadFavorites() {
        // Check if the user is authenticated
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();  //Get current user ID

            //Fetch favorites from Firestore
            db.collection("users")
                    .document(userId)
                    .collection("favorites")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        favoritesList.clear();  //Clear existing list before loading new data
                        if (queryDocumentSnapshots.isEmpty()) {
                            //Show message if no favorites are found
                            Toast.makeText(FavoritesActivity.this, "No favorites found", Toast.LENGTH_SHORT).show();
                        } else {
                            //Add each favorite movie to the list
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                MovieFavorite movie = snapshot.toObject(MovieFavorite.class);
                                if (movie != null) {
                                    favoritesList.add(movie);  // Add movie to list
                                }
                            }
                            //Notify the adapter to update the UI with new data
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(e -> {
                        //Show error message if fetching fails
                        Toast.makeText(FavoritesActivity.this, "Failed to load favorites", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();  // Print the error stack trace for debugging
                    });
        } else {
            //Show error message if user is not authenticated
            Toast.makeText(FavoritesActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
