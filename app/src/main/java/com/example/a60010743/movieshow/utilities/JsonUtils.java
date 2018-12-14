package com.example.a60010743.movieshow.utilities;

import android.util.Log;

import com.example.a60010743.movieshow.model.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {
    private JsonUtils(){}
    public static List<MovieDetails> parse_json_get_details(String response)
                            throws JSONException{
        // Initialize variables
        final String RESULT = "results";
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

        // Parse JSON Object
        JSONObject movieData = new JSONObject(response);
        if(movieData != null) {
            JSONArray results = movieData.getJSONArray(RESULT);
            for (int i = 0; i < results.length(); i++) {
                JSONObject data = results.getJSONObject(i);
                title = data.optString(TITLE);
                image = data.optString(POSTER_PATH);
                synopsis = data.optString(OVERVIEW);
                rating = Float.valueOf(data.optString(VOTE_AVERAGE));
                release_date = data.optString(RELEASE_DATE);
                Log.d("PUTSS" + i, title);
                movieDetCollection.add(new MovieDetails(title, image, synopsis, rating, release_date));
            }
        }
        return movieDetCollection;
    }
}
