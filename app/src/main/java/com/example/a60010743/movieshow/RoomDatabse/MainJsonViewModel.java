package com.example.a60010743.movieshow.RoomDatabse;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.a60010743.movieshow.model.MovieDetails;
import com.example.a60010743.movieshow.utilities.JsonUtils;
import com.example.a60010743.movieshow.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainJsonViewModel extends AndroidViewModel {

    private List<MovieDetails> movDetails;
    private ProgressBar mSpinner;
    public MainJsonViewModel(@NonNull Application application, String searchParam, ProgressBar spinner) {
        super(application);
        Log.d("MainJsonViewModel", "indie view model");
        this.mSpinner = spinner;
        loadData(searchParam);
    }


    private void loadData(final String searchParam) {

        new AsyncTask<Void, Void, List<MovieDetails>>(){
            String movieData;

            @Override
            protected void onPreExecute() {
                mSpinner.setVisibility(View.VISIBLE);
            }

            @Override
            protected List<MovieDetails> doInBackground(Void... voids) {
                List<MovieDetails> mDetails = new ArrayList<MovieDetails>();
                try{
                    // Fetch data from movie db api
                    URL movieDbUrl = NetworkUtils.buildUrl(searchParam);
                    Log.d("Test BK TASK", movieDbUrl.toString());
                    movieData = NetworkUtils.fetchData(movieDbUrl);
                    Log.d("Test BK Data", movieData);
                    if(movieData != null){
                        Log.d("GOING TO PARSE", movieData);
                       // movDetails = (MutableLiveData<List<MovieDetails>>) JsonUtils.parse_json_get_details(movieData);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                return mDetails;
            }

            @Override
            protected void onPostExecute(List<MovieDetails> movieDetails) {
                Log.d("INSIDE", "POST EXECUTE");
                mSpinner.setVisibility(View.INVISIBLE);
                try {
                    movDetails = JsonUtils.parse_json_get_details(movieData);

                } catch (Exception e) {

                }
            }
        }.execute();
    }

    public List<MovieDetails> getData(String searchParam){
       // if(movDetails == null){  loadData(searchParam);}
        loadData(searchParam);
        return movDetails;
    }
}

