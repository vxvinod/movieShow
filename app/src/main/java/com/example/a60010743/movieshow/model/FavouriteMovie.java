package com.example.a60010743.movieshow.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

@Entity
public class FavouriteMovie implements Serializable {

//    @PrimaryKey(autoGenerate = true)
//    private int id;

    public FavouriteMovie(@NonNull String movieId, String movieTitle) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
    }

    @PrimaryKey @NonNull
    private String movieId;

    private String movieTitle;



//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

}
