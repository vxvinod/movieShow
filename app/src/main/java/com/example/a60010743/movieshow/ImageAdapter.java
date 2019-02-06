package com.example.a60010743.movieshow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.a60010743.movieshow.model.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    List<MovieDetails> movieDetails;
    public ImageAdapter(Context c, List<MovieDetails> movieDetails){
        mContext = c;
        inflater = LayoutInflater.from(c);
        this.movieDetails = movieDetails;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return movieDetails.size();
    }

    public void setMovieDetails(List<MovieDetails> md){
        movieDetails = md;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_gridview, null);
        ImageView im = (ImageView) convertView.findViewById(R.id.movie_icon);
        String image = movieDetails.get(position).getPosterTv();
        Log.d("Image Details--", image);
        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/w500/"+image)
                .into(im);
        return convertView;
    }
}
