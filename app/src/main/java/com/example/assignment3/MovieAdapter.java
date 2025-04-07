package com.example.assignment3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void addMovie(Movie movie) {
        movieList.add(movie); //Add new movie to the list
        notifyItemInserted(movieList.size() - 1); //Notify adapter about new item
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout for each movie item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        //Set title & year
        holder.titleTextView.setText(movie.getTitle());
        holder.yearTextView.setText(movie.getYear());

        //Load movie poster using Glide
        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .into(holder.posterImageView);

        //Display rating and metascore
        holder.ratedTextView.setText("Rated: " + (movie.getRated() != null ? movie.getRated() : "N/A"));
        holder.ratingTextView.setText("Score: " + (movie.getMetascore() != null ? movie.getMetascore() : "0%"));

        holder.itemView.setOnClickListener(v -> {
            //Create an Intent to navigate to MovieDetailsActivity
            Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);

            //Pass the movie object to MovieDetailsActivity
            intent.putExtra("movie", movie);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        //Views for movie details
        TextView titleTextView;
        TextView yearTextView;
        TextView ratedTextView;
        TextView ratingTextView;
        ImageView posterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            //Initialize the views
            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            ratedTextView = itemView.findViewById(R.id.ratedTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }

    public void clearMovies() {
        movieList.clear();
        notifyDataSetChanged();
    }

}
