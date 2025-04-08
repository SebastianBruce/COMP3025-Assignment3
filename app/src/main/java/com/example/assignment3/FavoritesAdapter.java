package com.example.assignment3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MovieViewHolder> {

    private List<MovieFavorite> favoritesList;

    //Initialize the list of favorite movies
    public FavoritesAdapter(List<MovieFavorite> favoritesList) {
        this.favoritesList = favoritesList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout for each movie item in the list
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieFavorite movie = favoritesList.get(position);  //Get the movie at the current position

        //Set movie info
        holder.titleTextView.setText(movie.getTitle());
        holder.yearTextView.setText("Year: " + movie.getYear());
        holder.ratedTextView.setText("Rated: " + movie.getRated());
        holder.ratingTextView.setText("Rating: " + movie.getRating());

        //Load the movie poster
        Glide.with(holder.posterImageView.getContext())
                .load(movie.getPoster())
                .into(holder.posterImageView);

        //When an item is clicked open MovieEditActivity
        holder.itemView.setOnClickListener(v -> {
            //Create an intent to open MovieEditActivity and pass the movie object
            Intent intent = new Intent(v.getContext(), MovieEditActivity.class);
            intent.putExtra("movie", movie);  //Pass the movie object to the next activity
            v.getContext().startActivity(intent);  //Start the MovieEditActivity
        });
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();  //Return the total number of favorite movies
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, yearTextView, ratedTextView, ratingTextView;
        ImageView posterImageView;

        //Initialize the views
        public MovieViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            ratedTextView = itemView.findViewById(R.id.ratedTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}