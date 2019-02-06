package com.example.a60010743.movieshow;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class JsonViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String searchParam;

    public JsonViewModelFactory(Application mApplication, String searchParam) {
        this.mApplication = mApplication;
        this.searchParam = searchParam;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainJsonViewModel(mApplication, searchParam);
    }
}
