package com.example.a60010743.movieshow.RoomDatabse;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

public class JsonViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String searchParam;
    private ProgressBar spinner;

    public JsonViewModelFactory(Application mApplication, String searchParam, ProgressBar spinner) {
        this.mApplication = mApplication;
        this.searchParam = searchParam;
        this.spinner = spinner;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainJsonViewModel(mApplication, searchParam, spinner);
    }
}
