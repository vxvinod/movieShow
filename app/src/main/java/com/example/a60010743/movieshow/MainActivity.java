package com.example.a60010743.movieshow;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.a60010743.movieshow.model.MovieDetails;
import com.example.a60010743.movieshow.utilities.JsonUtils;
import com.example.a60010743.movieshow.utilities.NetworkUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<MovieDetails> mMovieDetails;
    private String mQueryResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL movieDbUrl = NetworkUtils.buildUrl("popular");
        new MovieDbQueryTask().execute(movieDbUrl);



    }

    public class MovieDbQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
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
          //  Log.d("DISPLAY POPULAR DATA", s);
            try {
                mMovieDetails = JsonUtils.parse_json_get_details(s);
                mGridView = (GridView) findViewById(R.id.movie_grid);
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
