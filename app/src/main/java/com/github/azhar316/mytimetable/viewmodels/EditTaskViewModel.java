package com.github.azhar316.mytimetable.viewmodels;

import com.github.azhar316.mytimetable.database.AppDatabase;
import com.github.azhar316.mytimetable.database.TaskEntry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class EditTaskViewModel extends ViewModel {

    private LiveData<TaskEntry> task;

    public EditTaskViewModel(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
