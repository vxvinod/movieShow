package com.example.a60010743.movieshow.utilities;

import android.util.Log;

import com.example.a60010743.movieshow.model.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {
    private static final String RESULT = "results";
    private JsonUtils(){}
    public static List<MovieDetails> parse_json_get_details(String response)
                            throws JSONException{
        // Initialize variables

        final String TITLE = "title";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";
        final String VOTE_AVERAGE = "vote_average";
        final String OVERVIEW = "overview";
        final String MOVIE_ID = "id";

        List<MovieDetails> movieDetCollection = new ArrayList<MovieDetails>();
        String title;
        String image;
        String synopsis;
        float rating;
        String release_date;
        String movie_id;

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
                movie_id = data.optString(MOVIE_ID);
                Log.d("PUTSS MOVIE ID" + i, movie_id);
                movieDetCollection.add(new MovieDetails(title, image, synopsis, rating, release_date, movie_id));
            }
        }
        return movieDetCollection;
    }

    public static List<String> get_vaue_for_key_from_json(String response, String key)
                    throws JSONException{
        String value = null;
        String type = null;
        List<String> trailers = new ArrayList<String>();
        JSONObject movieTrailerDetails = new JSONObject(response);
        if(movieTrailerDetails !=null) {
            JSONArray results = movieTrailerDetails.getJSONArray(RESULT);
            for(int i=0; i< results.length(); i++){
                JSONObject data = results.getJSONObject(i);
                type = data.optString("type");
                if(type != null) {
                    value = data.optString(key);
                    if (value != null) { trailers.add(value); };
                }

            }

        }
        for( String key1 : trailers) {
            Log.d("FINAL VALUEY", key1);
        }
        return trailers;
    }
}
