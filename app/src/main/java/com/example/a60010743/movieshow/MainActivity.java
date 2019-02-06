package com.example.a60010743.movieshow;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.a60010743.movieshow.model.MovieDetails;
import com.example.a60010743.movieshow.utilities.JsonUtils;
import com.example.a60010743.movieshow.utilities.NetworkUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{
//        implements LoaderManager.LoaderCallbacks<List<MovieDetails>>{
//
//    private GridView mGridView;
//    private List<MovieDetails> mMovieDetails;
//    private String mQueryResults;
//    private ProgressBar mSpinner;
    private final String LOG_TAG = getClass().getSimpleName().toString();
    private final String POPULAR = "popular";
    private final String TOP_RATED = "top_rated";
    private GridView mGridView;
    private static ImageAdapter iAdapter;
    public static final int FETCH_MOVIE_DATA = 22;
    public static URL MOVIE_URL = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get element in to an object
//        mSpinner = (ProgressBar) findViewById(R.id.progressBar);
//        mGridView = (GridView) findViewById(R.id.movie_grid);

        mGridView = findViewById(R.id.movie_grid);
        iAdapter = new ImageAdapter(this, new ArrayList<MovieDetails>());
        mGridView.setAdapter(iAdapter);
        // By default popular movie details is listed
//        URL movieDbUrl = NetworkUtils.buildUrl(POPULAR);
//        new MovieDbQueryTask(this).execute(movieDbUrl);

        MOVIE_URL = NetworkUtils.buildUrl(POPULAR);
        //new MovieDbQueryTask(this).execute(movieDbUrl);
        String searchParam = POPULAR;
        MainJsonViewModel jsonViewModel = ViewModelProviders.of(this,
                new JsonViewModelFactory(this.getApplication(), searchParam)).get(MainJsonViewModel.class);
        jsonViewModel.getData().observe(this, new Observer<List<MovieDetails>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetails> movieDetails) {
                iAdapter.setMovieDetails(movieDetails);
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Traversing to detailed activity
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("movieDetail", movieDetails.get(position));
                        MainActivity.this.startActivity(intent);
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_filter, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String searchParam = (item.getItemId() == R.id.popular) ? POPULAR : TOP_RATED;
        switch (item.getItemId()) {
            case R.id.popular:
                searchParam = POPULAR;
                fetchDataFor(searchParam);
                break;
            case R.id.top_rated:
                searchParam = TOP_RATED;
                fetchDataFor(searchParam);
                break;

            case R.id.favourite:
                displayFavouriteMovies();
                break;
        }

        return true;
    }

    private void displayFavouriteMovies() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getDetails().observe(this, new Observer<List<MovieDetails>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetails> movieDetails) {
               iAdapter.setMovieDetails(movieDetails);
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Traversing to detailed activity
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("movieDetail", movieDetails.get(position));
                        MainActivity.this.startActivity(intent);
                    }
                });
            }
        });
    }

    private void fetchDataFor(String searchParam) {
        // Display top rated or popular content based on selection
//        URL movieDbUrl = NetworkUtils.buildUrl(searchParam);
//        new MovieDbQueryTask(this).execute(movieDbUrl);
        MainJsonViewModel jsonViewModel = ViewModelProviders.of(this,
                                    new JsonViewModelFactory(this.getApplication(), searchParam)).get(MainJsonViewModel.class);
        jsonViewModel.getData().observe(this, new Observer<List<MovieDetails>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetails> movieDetails) {
                iAdapter.setMovieDetails(movieDetails);
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Traversing to detailed activity
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("movieDetail", movieDetails.get(position));
                        MainActivity.this.startActivity(intent);
                    }
                });
            }
        });
    }

//    @NonNull
//    @Override
//    public Loader<List<MovieDetails>> onCreateLoader(int i, @Nullable Bundle bundle) {
//        return new AsyncTaskLoader<List<MovieDetails>>(this) {
//            @Nullable
//            @Override
//            public List<MovieDetails> loadInBackground() {
//                if(MOVIE_URL == null && MOVIE_URL.equals("")){ return null; };
//                String queryResponse = "";
//                List<MovieDetails> movieDetails = new ArrayList<MovieDetails>();
//                try{
//                    queryResponse = NetworkUtils.fetchData(MOVIE_URL);
//                    if(queryResponse != null){
//                         movieDetails = JsonUtils.parse_json_get_details(queryResponse);
//                    }
//                } catch (Exception e) {
//                    Log.d(LOG_TAG, e.getMessage());
//                }
//                return movieDetails;
//            }
//
//            @Override
//            protected void onStartLoading() {
//                super.onStartLoading();
//            }
//        };
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<List<MovieDetails>> loader, List<MovieDetails> movieDetails) {
//
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<List<MovieDetails>> loader) {
//
//    }

//    public static class MovieDbQueryTask extends AsyncTask<URL, Void, String>{
//
//        private WeakReference<MainActivity> activityReference;
//        private MainActivity activity;
//        private List<MovieDetails> mMovieDetails;
//        private String mQueryResults;
//        private ProgressBar mSpinner;
//        private GridView mGridView;
//        MovieDbQueryTask(MainActivity context){
//            activityReference = new WeakReference<>(context);
//            activity = activityReference.get();
//        //    MainActivity mainActivity = activityReference.get();
//        }
//
//        @Override
//        protected void onPreExecute() {
//            if(activity == null || activity.isFinishing()) return;
//            mSpinner = activity.findViewById(R.id.progressBar);
//
//            // Handle progress spinning bar
//            mSpinner.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(URL... urls) {
//            URL queryUrl = urls[0];
//            try{
//                // Fetch data from movie db api
//                mQueryResults = NetworkUtils.fetchData(queryUrl);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return mQueryResults;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            mSpinner.setVisibility(View.GONE);
//            if(s!= null) {
//                try {
//                    // Parse Json response
//                    mMovieDetails = JsonUtils.parse_json_get_details(s);
//                    //mGridView.setAdapter(new ImageAdapter(activity, mMovieDetails));
//                    iAdapter.setMovieDetails(mMovieDetails);
//                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        // Traversing to detailed activity
//                        Intent intent = new Intent(activity, DetailActivity.class);
//                        intent.putExtra("movieDetail", mMovieDetails.get(position));
//                        activity.startActivity(intent);
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

}
