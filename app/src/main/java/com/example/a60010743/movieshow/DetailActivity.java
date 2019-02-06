package com.example.a60010743.movieshow;

import android.arch.lifecycle.LiveData;
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

import com.example.a60010743.movieshow.model.FavouriteMovie;
import com.example.a60010743.movieshow.model.MovieDetails;
import com.example.a60010743.movieshow.model.MovieRepository;
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

//    private TextView mTitle;
//    private ImageView mPosterView;
//    private TextView mReleaseDate;
//    private TextView mUserRating;
//    private TextView mSynopsis;

    private  MovieDetails md = null;
    private static Context context;
    private static List<String> youTubeKey;
    @BindView(R.id.movie_title) TextView mTitle;
    @BindView(R.id.image_view) ImageView mPosterView;
    @BindView(R.id.release_date_value) TextView mReleaseDate;
    @BindView(R.id.rating_value) TextView mUserRating;
    @BindView(R.id.synopsis) TextView mSynopsis;
    @BindView(R.id.favourite) Button mFavBtn;
//    @BindView(R.id.review_author) TextView mAuthor;
//    @BindView(R.id.review_content) TextView mContent;
    final static String MOVIE_DB_URL = "https://api.themoviedb.org";
    final static String API_KEY = BuildConfig.API_KEY;

   // @BindView(R.id.iv_trailer1) Button mTrailerIcon1;

    private static RecyclerView trailerView;
    private RecyclerView reviewView;
    String[] Items = {"item 0","item 1","item 2", "item 3", "item 4", "item 5", "item 6", "item 7"};
    List<ReviewContent> reviewDetails;
    ReviewAdapter reviewAdapter;
    private FavMovDatabase favDb;
    private boolean favBtnPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DetailActivity.context = getApplicationContext();
        //movieRepository = new MovieRepository(context);
        favDb = FavMovDatabase.getInstance(context);





        reviewDetails = new ArrayList<ReviewContent>();
//        ReviewContent rv1 = new ReviewContent("vinod", "tttttt");
//        reviewDetails.add(0,rv1);
//        ReviewContent rv2 = new ReviewContent("vinod1", "tttttt");
//        reviewDetails.add(0,rv2);
//
//        ReviewContent rv3 = new ReviewContent("vinod1", "tttttt");
//        reviewDetails.add(0,rv3);
//        ReviewContent rv4 = new ReviewContent("vinod1", "tttttt");
//        reviewDetails.add(0,rv4);

        reviewView = (RecyclerView) findViewById(R.id.reviewView);
        reviewView.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new ReviewAdapter(this, reviewDetails);
        reviewView.setAdapter(reviewAdapter);

        trailerView = (RecyclerView) findViewById(R.id.recyclerView);
        // Fetch the view item in to an object
//        mTitle = (TextView) findViewById(R.id.movie_title);
//        mPosterView = (ImageView) findViewById(R.id.image_view);
//        mReleaseDate = (TextView) findViewById(R.id.release_date_value);
//        mUserRating = (TextView) findViewById(R.id.rating_value);
//        mSynopsis = (TextView) findViewById(R.id.synopsis);
        ButterKnife.bind(this);
//        mTrailerIcon1.setOnClickListener(this);
//        mTrailerIcon2.setOnClickListener(this);
//        mTrailerIcon3.setOnClickListener(this);
        // Get the data from main activity
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
            //movieId = md.getMovieId();
            mFavBtn.setText("Mark as Fav");
            mFavBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favBtnPressed == false) {
                      //  movieRepository.performTask(md.getTitle(), md.getMovieId(), "insert");
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                               // FavouriteMovie fm = new FavouriteMovie(md.getTitle(), md.getMovieId());
                                favDb.doaAccess().insertTask(md);
                            }
                        });
                        mFavBtn.setText("Marked Fav");
                        mFavBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        favBtnPressed = true;
                    } else {
                       // movieRepository.performTask(md.getTitle(), md.getMovieId(), "delete");
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                               // FavouriteMovie fm = new FavouriteMovie(md.getTitle(), md.getMovieId());
                                favDb.doaAccess().deleteTask(md);
                            }
                        });
                        mFavBtn.setText("Mark as Fav");
                        mFavBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    }
                    Log.d("Inside Insert DB", "DB1");

                }
            });


            showMovies();
            URL movieVideoUrl = NetworkUtils.buildTrailerUrl(md.getMovieId());
            new fetchMovieDetails().execute(movieVideoUrl);

//            trailerView = (RecyclerView) findViewById(R.id.recyclerView);
//            trailerView.setLayoutManager(new LinearLayoutManager(this));
//            trailerView.setAdapter(new TrailerAdapter(this, Items));
//            List<String> list = new ArrayList<String>();
//            list.add("A");
//            list.add("B");
//            list.add("C");
           // create_trailer_button(list);
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
                    Log.d("REPONSE BODY", String.valueOf(response.body()));
                    Log.d("RESPONSE CODE", String.valueOf(response.code()));
                    ReviewDetails reviews = response.body();
                    Log.d("RESPONSE RESULTS", String.valueOf(reviews.results));
                    //reviewView.setAdapter(new ReviewAdapter(Call<ReviewDetails> call,this, reviews.results));
                    reviewAdapter.setReviewsList(reviews.results);
                    for(ReviewContent review: reviews.results){

//
//


                        String content = "";
                        content += "Author:" + review.getAuthor() + "\n";
                        content += "Content:" + review.getContent() + "\n";
                        Log.d("CONTENT", content);
                        //mContent.setText(content);


                    }
                }

                @Override
                public void onFailure(Call<ReviewDetails> call, Throwable t) {
                    Log.d("RESPONSE FAILED", t.toString());
                }
            });
        }

        //movieRepository.getFavMovies().observe();
    }


    private void showMovies() {
//        List<FavouriteMovie> movies = movieRepository.getFavMovies();
//        for(FavouriteMovie mov:movies){
//            Log.d("DATA---ID", mov.getMovieId());
//            Log.d("DATA===TITLE|", mov.getMovieTitle());
//        }
    }

    public void start_video_activity(String key){
        String videoPath = "https://www.youtube.com/watch?v="+key;
        Log.d("TRAILER KEY", videoPath);
        Uri uri = Uri.parse(videoPath);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }



    public static Context get_app_context(){
        return DetailActivity.context;
    }

    private void create_trailer_button(List<String> youTubeKey) {
        Button trailerBtn;
        Log.d("TRAILER Button", "inside trailer btn method");
        //Log.d("TRAILER Button", "inside trailer btn method-"+tNum);
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.detailLayout);
        ConstraintSet set = new ConstraintSet();
        set.clone(cl);
        int prevBtnId = 1;
        for(int tNum=0; tNum<=youTubeKey.size(); tNum++){

            if(tNum == 0) {
                trailerBtn = new Button(get_app_context());
                trailerBtn.setId(100);
                trailerBtn.setText("Trailer-" + tNum);
                trailerBtn.setBackgroundColor(getResources().getColor(R.color.btnColor));
                trailerBtn.setTag(tNum);
                cl.addView(trailerBtn);
                set.connect(trailerBtn.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
                set.connect(trailerBtn.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
                set.connect(trailerBtn.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
                set.constrainHeight(trailerBtn.getId(), 200);
                prevBtnId = trailerBtn.getId();
                set.applyTo(cl);
            } else {
                //Button 2:
                Button newButton = new Button(get_app_context());
                newButton.setText("Trailer-"+tNum);
                newButton.setId(100+tNum);
                cl.addView(newButton);
                set.connect(newButton.getId(), ConstraintSet.BOTTOM, prevBtnId, ConstraintSet.TOP, 32);
                set.connect(newButton.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 32);
                set.connect(newButton.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 32);
                set.constrainHeight(newButton.getId(), 200);
                prevBtnId = newButton.getId();
                set.applyTo(cl);
            }

        }
    }

    public static class fetchMovieDetails extends AsyncTask<URL, Void, String>{

        private String response;

        @Override
        protected String doInBackground(URL... urls) {
            //Build Uri
           // URL movieVideoUrl = NetworkUtils.buildTrailerUrl(movieId);
            URL movieVideoUrl = urls[0];
            //Fetch data
            try {
                Log.d("MOVIE VIDEO URL", movieVideoUrl.toString());
                response = NetworkUtils.fetchData(movieVideoUrl);

            //    if(youTubeKey != null){ create_trailer_button(youTubeKey); };
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
                    Log.d("RESPONSE IS ", response);
                    youTubeKey = JsonUtils.get_vaue_for_key_from_json(response, "key");
                    if(youTubeKey != null) {
                        trailerView.setLayoutManager(new LinearLayoutManager(get_app_context()));
                        trailerView.setAdapter(new TrailerAdapter(get_app_context(), youTubeKey));
                    }
                } else {
                    Log.d("RESPONSE IS NULL", response);
                    //   Log.d("URLLLL", movieVideoUrl.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
