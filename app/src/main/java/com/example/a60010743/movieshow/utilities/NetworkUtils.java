package com.example.a60010743.movieshow.utilities;

import android.net.Uri;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.a60010743.movieshow.model.MovieDetails;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    // Initialize variables
    final static String MOVIE_DB_URL = "https://api.themoviedb.org";
    final static String PAGE = "3";
    final static String MOVIE = "movie";
    final static String POPULAR = "popular";
    final static String TOP_RATED = "3/movie/top_rated";
    final static String API_KEY_TXT = "api_key";
    final static String API_KEY = "1f6edcf7cdec1a1942d5d87e84a1ab89";

    // Build URL
    public static URL buildUrl(String movieDbSearchQuery){
        Uri buildUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                        .appendEncodedPath(PAGE)
                        .appendEncodedPath(MOVIE)
                        .appendEncodedPath(POPULAR)
                        .appendQueryParameter(API_KEY_TXT, API_KEY)
                        .build();

        URL url = null;
        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.d("URLLLLLL", url.toString());
        return url;
    }

    public static String fetchData(URL query) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) query.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }



    // getResponseWithUrl




}
