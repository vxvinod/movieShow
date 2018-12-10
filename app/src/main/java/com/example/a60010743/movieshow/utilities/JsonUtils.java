package com.example.a60010743.movieshow.utilities;

import android.util.Log;

import com.example.a60010743.movieshow.model.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<MovieDetails> parse_json_get_details(String response)
                            throws JSONException{

        final String RESULT = "RESULT";
        final String TITLE = "title";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";
        final String VOTE_AVERAGE = "vote_average";
        final String OVERVIEW = "overview";

        List<MovieDetails> movieDetCollection = new ArrayList<MovieDetails>();
        String title;
        String image;
        String synopsis;
        float rating;
        String release_date;

        JSONObject movieData = new JSONObject(response);
        JSONArray results = movieData.getJSONArray("results");
        for(int i=0; i < results.length(); i++){
            JSONObject data = results.getJSONObject(i);
            title = data.optString(TITLE);
            image = data.optString(POSTER_PATH);
            synopsis = data.optString(OVERVIEW);
            rating = Float.valueOf(data.optString(VOTE_AVERAGE));
            release_date = data.optString(RELEASE_DATE);
            Log.d("PUTSS"+i, title);
            movieDetCollection.add(new MovieDetails(title, image, synopsis, rating, release_date));
        }

        for(int j=0; j<movieDetCollection.size(); j++){
            Log.d("movieCollection"+j+"title", movieDetCollection.get(j).getTitle());
            Log.d("movieCollection"+j+"image", movieDetCollection.get(j).getPosterTv());
            Log.d("movieCollection"+j+"rating", String.valueOf(movieDetCollection.get(j).getUserRating()) );
        }
        Log.d("movieCollection", movieDetCollection.toString());
        //movieDetCollection.add(new MovieDetails())
        return movieDetCollection;
    }
}
