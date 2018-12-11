package com.example.a60010743.movieshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a60010743.movieshow.model.MovieDetails;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mPosterView;
    private TextView mReleaseDate;
    private TextView mUserRating;
    private TextView mSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle = (TextView) findViewById(R.id.movie_title);
        mPosterView = (ImageView) findViewById(R.id.image_view);
        mReleaseDate = (TextView) findViewById(R.id.release_date_value);
        mUserRating = (TextView) findViewById(R.id.rating_value);
        mSynopsis = (TextView) findViewById(R.id.synopsis);

        Intent intent = getIntent();
        MovieDetails md = intent.getParcelableExtra("movieDetail");

        mTitle.setText(md.getTitle());
        Picasso.with(DetailActivity.this)
                .load("http://image.tmdb.org/t/p/w342/"+md.getPosterTv())
                .into(mPosterView);
        mSynopsis.setText(md.getOverview());
        mUserRating.setText(String.valueOf(md.getUserRating())+"/10");
        mReleaseDate.setText(md.getReleasedDate());



    }
}
