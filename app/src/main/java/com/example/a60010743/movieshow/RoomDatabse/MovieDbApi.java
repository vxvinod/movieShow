package com.example.a60010743.movieshow.RoomDatabse;

import com.example.a60010743.movieshow.model.ReviewDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbApi {

    @GET("3/movie/{movieId}/reviews")
    Call<ReviewDetails> getReview(@Path("movieId") String movieId, @Query("api_key") String api_key);
}
