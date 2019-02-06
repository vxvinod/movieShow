package com.example.a60010743.movieshow;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.a60010743.movieshow.model.MovieDetails;
import com.example.a60010743.movieshow.utilities.JsonUtils;
import com.example.a60010743.movieshow.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainJsonViewModel extends AndroidViewModel {

    private List<MovieDetails> movDetails = new ArrayList<>();

    public MainJsonViewModel(@NonNull Application application, String searchParam) {
        super(application);
        Log.d("MainJsonViewModel", "indie view model");
       // loadData(searchParam);
    }


    private void loadData(final String searchParam) {
        new AsyncTask<Void, Void, List<MovieDetails>>(){
            String movieData;
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
                try {
                    movDetails = JsonUtils.parse_json_get_details(movieData);
                } catch (Exception e) {

                }
            }
        }.execute();
    }

    public List<MovieDetails> getData(String searchParam){
        //if(movDetails == null){  loadData(searchParam);
        loadData(searchParam);
        return movDetails;
    };
}




//
//package com.example.a60010743.movieshow;
//
//        import android.app.Application;
//        import android.arch.lifecycle.AndroidViewModel;
//        import android.arch.lifecycle.LiveData;
//        import android.arch.lifecycle.MutableLiveData;
//        import android.os.AsyncTask;
//        import android.support.annotation.NonNull;
//        import android.util.Log;
//
//        import com.example.a60010743.movieshow.model.MovieDetails;
//        import com.example.a60010743.movieshow.utilities.JsonUtils;
//        import com.example.a60010743.movieshow.utilities.NetworkUtils;
//
//        import java.net.URL;
//        import java.util.ArrayList;
//        import java.util.List;

//public class MainJsonViewModel extends AndroidViewModel {
//
//    private MutableLiveData<List<MovieDetails>> movDetails;
//
//    public MainJsonViewModel(@NonNull Application application, String searchParam) {
//        super(application);
//        Log.d("MainJsonViewModel", "indie view model");
//        loadData(searchParam);
//    }
//
//    private void loadData(final String searchParam) {
//        new AsyncTask<Void, Void, List<MovieDetails>>(){
//            String movieData;
//            @Override
//            protected List<MovieDetails> doInBackground(Void... voids) {
//                List<MovieDetails> mDetails = new ArrayList<MovieDetails>();
//                try{
//                    // Fetch data from movie db api
//                    URL movieDbUrl = NetworkUtils.buildUrl(searchParam);
//                    Log.d("Test BK TASK", movieDbUrl.toString());
//                    movieData = NetworkUtils.fetchData(movieDbUrl);
//                    Log.d("Test BK Data", movieData);
//                    if(movieData != null){
//                        Log.d("GOING TO PARSE", movieData);
//                        // movDetails = (MutableLiveData<List<MovieDetails>>) JsonUtils.parse_json_get_details(movieData);
//
//                    }
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                return mDetails;
//            }
//
//            @Override
//            protected void onPostExecute(List<MovieDetails> movieDetails) {
//                try {
//                    movDetails.setValue(JsonUtils.parse_json_get_details(movieData));
//                } catch (Exception e) {
//
//                }
//            }
//        }.execute();
//    }
//
//    public LiveData<List<MovieDetails>> getData(){
//
//        if(movDetails == null){
//            movDetails = new MutableLiveData<List<MovieDetails>>();
//
//        }
//        return movDetails;
//    };
//}
//
