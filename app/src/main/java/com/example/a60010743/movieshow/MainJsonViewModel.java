package com.example.a60010743.movieshow;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.a60010743.movieshow.model.MovieDetails;
import com.example.a60010743.movieshow.utilities.JsonUtils;
import com.example.a60010743.movieshow.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainJsonViewModel extends AndroidViewModel {

    private MutableLiveData<List<MovieDetails>> movDetails =
            new MutableLiveData<List<MovieDetails>>();

    public MainJsonViewModel(@NonNull Application application, String searchParam) {
        super(application);
        loadData(searchParam);
    }

    private void loadData(final String searchParam) {
        new AsyncTask<Void, Void, List<MovieDetails>>(){

            @Override
            protected List<MovieDetails> doInBackground(Void... voids) {
                List<MovieDetails> mDetails = new ArrayList<MovieDetails>();
                try{
                    // Fetch data from movie db api
                    URL movieDbUrl = NetworkUtils.buildUrl(searchParam);
                    String movieData = NetworkUtils.fetchData(movieDbUrl);
                    if(movieData != null){ movDetails = (MutableLiveData<List<MovieDetails>>)
                                            JsonUtils.parse_json_get_details(movieData);}
                }catch (Exception e){
                    e.printStackTrace();
                }
                return mDetails;
            }

        }.execute();
    }

    public LiveData<List<MovieDetails>> getData(){ return movDetails;};
}
