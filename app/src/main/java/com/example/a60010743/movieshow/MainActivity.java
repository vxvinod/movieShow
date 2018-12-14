package com.example.a60010743.movieshow;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//
//    private GridView mGridView;
//    private List<MovieDetails> mMovieDetails;
//    private String mQueryResults;
//    private ProgressBar mSpinner;
    private final String POPULAR = "popular";
    private final String TOP_RATED = "top_rated";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get element in to an object
//        mSpinner = (ProgressBar) findViewById(R.id.progressBar);
//        mGridView = (GridView) findViewById(R.id.movie_grid);

        // By default popular movie details is listed
        URL movieDbUrl = NetworkUtils.buildUrl(POPULAR);
        new MovieDbQueryTask(this).execute(movieDbUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_filter, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String searchParam = (item.getItemId() == R.id.popular) ? POPULAR : TOP_RATED;

        // Display top rated or popular content based on selection
        URL movieDbUrl = NetworkUtils.buildUrl(searchParam);
        new MovieDbQueryTask(this).execute(movieDbUrl);

        return true;
    }

    public static class MovieDbQueryTask extends AsyncTask<URL, Void, String>{

        private WeakReference<MainActivity> activityReference;
        private MainActivity activity;
        private List<MovieDetails> mMovieDetails;
        private String mQueryResults;
        private ProgressBar mSpinner;
        private GridView mGridView;
        MovieDbQueryTask(MainActivity context){
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        //    MainActivity mainActivity = activityReference.get();
        }

        @Override
        protected void onPreExecute() {
            if(activity == null || activity.isFinishing()) return;
            mSpinner = activity.findViewById(R.id.progressBar);
            mGridView = activity.findViewById(R.id.movie_grid);
            // Handle progress spinning bar
            mSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL queryUrl = urls[0];
            try{
                // Fetch data from movie db api
                mQueryResults = NetworkUtils.fetchData(queryUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
            return mQueryResults;
        }

        @Override
        protected void onPostExecute(String s) {
            mSpinner.setVisibility(View.GONE);
            if(s!= null) {
                try {
                    // Parse Json response
                    mMovieDetails = JsonUtils.parse_json_get_details(s);
                    mGridView.setAdapter(new ImageAdapter(activity, mMovieDetails));
                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Traversing to detailed activity
                        Intent intent = new Intent(activity, DetailActivity.class);
                        intent.putExtra("movieDetail", mMovieDetails.get(position));
                        activity.startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
