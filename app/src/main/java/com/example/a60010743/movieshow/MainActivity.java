package com.example.a60010743.movieshow;

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

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<MovieDetails> mMovieDetails;
    private String mQueryResults;
    private ProgressBar mSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinner = (ProgressBar) findViewById(R.id.progressBar);
        mGridView = (GridView) findViewById(R.id.movie_grid);
        URL movieDbUrl = NetworkUtils.buildUrl("popular");
        new MovieDbQueryTask().execute(movieDbUrl);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_filter, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String searchParam = (item.getItemId() == R.id.popular) ? "popular" : "top_rated";
        Log.d("ITEMIDIDIDID", String.valueOf(item.getItemId()));
        URL movieDbUrl = NetworkUtils.buildUrl(searchParam);
        new MovieDbQueryTask().execute(movieDbUrl);
       // return super.onOptionsItemSelected(item);
        return true;
    }

    public class MovieDbQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            mSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
          //  mGridView.setVisibility(View.GONE);

            URL queryUrl = urls[0];
//            String movieQueryResults = null;
            try{
                mQueryResults = NetworkUtils.fetchData(queryUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
           // Log.d("QUERY_RESULTS", movieQueryResults);
            return mQueryResults;
        }

        @Override
        protected void onPostExecute(String s) {
            mSpinner.setVisibility(View.GONE);
           // mGridView.setVisibility(View.VISIBLE);
          //  Log.d("DISPLAY POPULAR DATA", s);
            try {
                mMovieDetails = JsonUtils.parse_json_get_details(s);


                mGridView.setAdapter(new ImageAdapter(MainActivity.this, mMovieDetails));

                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("movieDetail", mMovieDetails.get(position));
                        Log.d("CLCISD","asdasd");
                        startActivity(intent);
                    }
                });
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
