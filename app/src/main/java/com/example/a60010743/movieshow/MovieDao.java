package com.example.a60010743.movieshow;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.a60010743.movieshow.model.FavouriteMovie;
import com.example.a60010743.movieshow.model.MovieDetails;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insertTask(MovieDetails favMov);

    @Query("SELECT * from MovieDetails")
    LiveData<List<MovieDetails>> fetchAllFavMovies();

    @Delete
    void deleteTask(MovieDetails favMov);

    @Query("SELECT * from MovieDetails WHERE movieId is :id")
    MovieDetails findById(String id);
}
