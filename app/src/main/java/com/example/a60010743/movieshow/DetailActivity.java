package com.example.a60010743.movieshow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a60010743.movieshow.Adapter.ReviewAdapter;
import com.example.a60010743.movieshow.Adapter.TrailerAdapter;
import com.example.a60010743.movieshow.RoomDatabse.AppExecutors;
import com.example.a60010743.movieshow.RoomDatabse.MovieDbApi;
import com.example.a60010743.movieshow.model.MovieDetails;
import com.example.a60010743.movieshow.model.ReviewContent;
import com.example.a60010743.movieshow.model.ReviewDetails;
import com.example.a60010743.movieshow.utilities.JsonUtils;
import com.example.a60010743.movieshow.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity  {

    private  MovieDetails md = null;
    private static Context context;
    private static List<String> youTubeKey;
    @BindView(R.id.movie_title) TextView mTitle;
    @BindView(R.id.image_view) ImageView mPosterView;
    @BindView(R.id.release_date_value) TextView mReleaseDate;
    @BindView(R.id.rating_value) TextView mUserRating;
    @BindView(R.id.synopsis) TextView mSynopsis;
    @BindView(R.id.favourite) Button mFavBtn;

    final static String MOVIE_DB_URL = "https://api.themoviedb.org";
    final static String API_KEY = BuildConfig.API_KEY;

    private static RecyclerView trailerView;
    private RecyclerView reviewView;
    List<ReviewContent> reviewDetails;
    ReviewAdapter reviewAdapter;
    private FavMovDatabase favDb;
    private boolean favBtnPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DetailActivity.context = getApplicationContext();

        favDb = FavMovDatabase.getInstance(context);
        reviewDetails = new ArrayList<ReviewContent>();
        reviewView = (RecyclerView) findViewById(R.id.reviewView);
        reviewView.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new ReviewAdapter(this, reviewDetails);
        reviewView.setAdapter(reviewAdapter);

        trailerView = (RecyclerView) findViewById(R.id.recyclerView);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final MovieDetails md = intent.getParcelableExtra("movieDetail");
        if(md != null) {
            mTitle.setText(md.getTitle());
            Picasso.with(DetailActivity.this)
                    .load("http://image.tmdb.org/t/p/w185/" + md.getPosterTv())
                    .into(mPosterView);
            mSynopsis.setText(md.getOverview());
            mUserRating.setText(String.valueOf(md.getUserRating()) + getString(R.string.out_of_ten));
            mReleaseDate.setText(md.getReleasedDate());
            checkMoviePresentAndSetFavBtn(md.getMovieId());
            mFavBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favBtnPressed == false) {
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() { favDb.doaAccess().insertTask(md); }
                        });
                        mFavBtn.setText("Marked Fav");
                        mFavBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        favBtnPressed = true;
                    } else {
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() { favDb.doaAccess().deleteTask(md); }
                        });
                        mFavBtn.setText("Mark as Fav");
                        mFavBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    }
                }
            });

            URL movieVideoUrl = NetworkUtils.buildTrailerUrl(md.getMovieId());
            new fetchMovieDetails().execute(movieVideoUrl);

            fetchMovieReview(md);
        }

    }

    // Fetch Movie Review
    private void fetchMovieReview(MovieDetails md) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIE_DB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieDbApi movieDbApi = retrofit.create(MovieDbApi.class);
        Call<ReviewDetails> call = movieDbApi.getReview(md.getMovieId(), API_KEY);
        call.enqueue(new Callback<ReviewDetails>() {
            @Override
            public void onResponse(Call<ReviewDetails> call, Response<ReviewDetails> response) {
                if(!response.isSuccessful()){
                    Log.d("RESPONSE NULL", String.valueOf(response.code()));
                    return;
                }
                ReviewDetails reviews = response.body();
                reviewAdapter.setReviewsList(reviews.results);
            }
            @Override
            public void onFailure(Call<ReviewDetails> call, Throwable t) {
                Log.d("RESPONSE FAILED", t.toString());
            }
        });
    }

    // Method to set fav button Marked or Not Marked.
    public void checkMoviePresentAndSetFavBtn(final String movieId){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                MovieDetails md = favDb.doaAccess().findById(movieId);
                if(md != null){
                    mFavBtn.setText("Marked Fav");
                    mFavBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    favBtnPressed = true;
                } else {
                    mFavBtn.setText("Mark as Fav");
                    mFavBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                };
            }
        });
    }

    public static Context get_app_context(){
        return DetailActivity.context;
    }

    // AsyncTask to fetch Video and Parse URL.
    public static class fetchMovieDetails extends AsyncTask<URL, Void, String>{
        private String response;

        @Override
        protected String doInBackground(URL... urls) {
            URL movieVideoUrl = urls[0];

            //Fetch data
            try {
                Log.d("MOVIE VIDEO URL", movieVideoUrl.toString());
                response = NetworkUtils.fetchData(movieVideoUrl);

            } catch(Exception e) {
                e.printStackTrace();
            }

            //parse data
            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if (response != null) {
                    youTubeKey = JsonUtils.get_vaue_for_key_from_json(response, "key");
                    if(youTubeKey != null) {
                        trailerView.setLayoutManager(new LinearLayoutManager(get_app_context()));
                        trailerView.setAdapter(new TrailerAdapter(get_app_context(), youTubeKey));
                    }
                } else {
                    Log.d("RESPONSE IS NULL", response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
