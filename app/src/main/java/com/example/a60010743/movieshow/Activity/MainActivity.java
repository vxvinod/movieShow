package com.example.a60010743.movieshow.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.a60010743.movieshow.Adapter.ImageAdapter;
import com.example.a60010743.movieshow.DetailActivity;
import com.example.a60010743.movieshow.R;
import com.example.a60010743.movieshow.RoomDatabse.JsonViewModelFactory;
import com.example.a60010743.movieshow.RoomDatabse.MainJsonViewModel;
import com.example.a60010743.movieshow.RoomDatabse.MainViewModel;
import com.example.a60010743.movieshow.model.MovieDetails;
import com.example.a60010743.movieshow.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private ProgressBar mSpinner;
    private final String LOG_TAG = getClass().getSimpleName().toString();
    private final String POPULAR = "popular";
    private final String TOP_RATED = "top_rated";
    private GridView mGridView;
    private static ImageAdapter iAdapter;
    public static final int FETCH_MOVIE_DATA = 22;
    public static URL MOVIE_URL = null;
    private MainJsonViewModel jsonViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get element in to an object
        mSpinner = (ProgressBar) findViewById(R.id.progressBar);
        mGridView = (GridView) findViewById(R.id.movie_grid);
        String searchParam = POPULAR;
        jsonViewModel = ViewModelProviders.of(this,
                new JsonViewModelFactory(this.getApplication(), searchParam, mSpinner))
                .get(MainJsonViewModel.class);
        List<MovieDetails> movieDetails = new ArrayList<MovieDetails>();
        iAdapter = new ImageAdapter(MainActivity.this, movieDetails);
        mGridView.setAdapter(iAdapter);
        fetchDataFor(POPULAR);
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

    // Display Favourite Movies form Database
    private void displayFavouriteMovies() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getDetails().observe(this, new Observer<List<MovieDetails>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetails> movieDetails) {
                iAdapter.setMovieDetails(movieDetails);
                iAdapter.notifyDataSetChanged();
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

    // Fetch data for TOP_RATED and POPULAR movies.
    private void fetchDataFor(String searchParam) {
        jsonViewModel.getData(searchParam).observe(this, new Observer<List<MovieDetails>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetails> movieDetails) {
                iAdapter.setMovieDetails(movieDetails);
                iAdapter.notifyDataSetChanged();
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
}
