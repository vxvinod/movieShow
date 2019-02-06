package com.example.a60010743.movieshow.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.a60010743.movieshow.FavMovDatabase;

import java.util.List;

public class MovieRepository {

//    private String DB_NAME = "favourites.db";
//
//    private FavMovDatabase favMovDatabase;
//
//    public MovieRepository(Context context){
//        favMovDatabase = Room.databaseBuilder(context, FavMovDatabase.class, DB_NAME).build();
//    }
//
////    public void performTask(String title, String movieId, String task){
////        FavouriteMovie favouriteMovie = new FavouriteMovie();
////        favouriteMovie.setMovieTitle(title);
////        favouriteMovie.setMovieId(movieId);
////
////        if(task == "insert"){
////            insertTask(favouriteMovie);
////        }else if(task == "delete") {
////            Log.d("Inside Delete DB", "Delete");
////            deleteTask(favouriteMovie);
////        }
////
////    }
//
//
//    public void  insertTask(final FavouriteMovie favouriteMovie){
//        Log.d("Inside Insert DB", "DB2");
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                favMovDatabase.doaAccess().insertTask(favouriteMovie);
//                return null;
//            }
//        }.execute();
//
//    }
//
//    public void deleteTask(final FavouriteMovie favouriteMovie){
//        new AsyncTask<Void, Void, Void>(){
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                favMovDatabase.doaAccess().deleteTask(favouriteMovie);
//                return null;
//            }
//        }.execute();
//    }
//
//    public List<FavouriteMovie> getFavMovies(){
//
//        new AsyncTask<Void, Void, List<FavouriteMovie>>(){
//            @Override
//            protected List<FavouriteMovie> doInBackground(Void... voids) {
//                return favMovDatabase.doaAccess().fetchAllFavMovies();
//            }
//        }.execute();
//    return null;
//    }
//



}
