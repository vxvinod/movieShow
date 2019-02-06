package com.example.a60010743.movieshow;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.a60010743.movieshow.model.FavouriteMovie;
import com.example.a60010743.movieshow.model.MovieDetails;

@Database(entities = {MovieDetails.class}, version = 1, exportSchema = false)
public abstract class FavMovDatabase extends RoomDatabase {

    private static final String LOG_TAG = FavMovDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favouriteMovies.db";
    private static FavMovDatabase sInstance;

    public static FavMovDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d("LOG_TAG", "Creating DB");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                                            FavMovDatabase.class, FavMovDatabase.DATABASE_NAME)
                                            .build();
            }
        }
        Log.d(LOG_TAG, "Database Instance");
        return sInstance;
    }


    public abstract MovieDao doaAccess();
}
