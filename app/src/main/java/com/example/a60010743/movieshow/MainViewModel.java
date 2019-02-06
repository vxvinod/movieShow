package com.example.a60010743.movieshow;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.a60010743.movieshow.model.MovieDetails;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<MovieDetails>> details;
    public MainViewModel(@NonNull Application application) {
        super(application);
        FavMovDatabase fDb = FavMovDatabase.getInstance(this.getApplication());
        details = fDb.doaAccess().fetchAllFavMovies();
    }

    public LiveData<List<MovieDetails>> getDetails() {
        return details;
    }
}
