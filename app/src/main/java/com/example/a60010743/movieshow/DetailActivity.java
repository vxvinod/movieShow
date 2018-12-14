package com.example.a60010743.movieshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a60010743.movieshow.model.MovieDetails;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

//    private TextView mTitle;
//    private ImageView mPosterView;
//    private TextView mReleaseDate;
//    private TextView mUserRating;
//    private TextView mSynopsis;
    @BindView(R.id.movie_title) TextView mTitle;
    @BindView(R.id.image_view) ImageView mPosterView;
    @BindView(R.id.release_date_value) TextView mReleaseDate;
    @BindView(R.id.rating_value) TextView mUserRating;
    @BindView(R.id.synopsis) TextView mSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Fetch the view item in to an object
//        mTitle = (TextView) findViewById(R.id.movie_title);
//        mPosterView = (ImageView) findViewById(R.id.image_view);
//        mReleaseDate = (TextView) findViewById(R.id.release_date_value);
//        mUserRating = (TextView) findViewById(R.id.rating_value);
//        mSynopsis = (TextView) findViewById(R.id.synopsis);
        ButterKnife.bind(this);

        // Get the data from main activity
        Intent intent = getIntent();
        MovieDetails md = intent.getParcelableExtra("movieDetail");
        if(md != null) {
            mTitle.setText(md.getTitle());
            Picasso.with(DetailActivity.this)
                    .load("http://image.tmdb.org/t/p/w185/" + md.getPosterTv())
                    .into(mPosterView);
            mSynopsis.setText(md.getOverview());
            mUserRating.setText(String.valueOf(md.getUserRating()) + getString(R.string.out_of_ten));
            mReleaseDate.setText(md.getReleasedDate());
        }

    }
}
