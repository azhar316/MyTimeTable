package com.github.azhar316.mytimetable.viewmodels;

import android.app.Application;

import com.github.azhar316.mytimetable.database.AppDatabase;
import com.github.azhar316.mytimetable.database.TaskEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private LiveData<List<TaskEntry>> tasks;

    public MainViewModel(AppDatabase database, int day) {
        tasks = database.taskDao().loadAllTasksByDay(day);
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return tasks;
    }
}
