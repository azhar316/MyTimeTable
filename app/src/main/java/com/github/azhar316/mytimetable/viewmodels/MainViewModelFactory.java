package com.github.azhar316.mytimetable.viewmodels;

import com.github.azhar316.mytimetable.database.AppDatabase;
import com.github.azhar316.mytimetable.database.TaskEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mDay;

    public MainViewModelFactory(AppDatabase database, int day) {
        mDb = database;
        mDay = day;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mDb, mDay);
    }
}
