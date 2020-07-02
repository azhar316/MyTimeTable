package com.github.azhar316.mytimetable.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(TaskEntry taskEntry);

    @Query("SELECT * FROM task WHERE id=:id")
    LiveData<TaskEntry> loadTaskById(int id);

    @Query("SELECT * FROM task WHERE day=:day ORDER BY start_time")
    LiveData<List<TaskEntry>> loadAllTasksByDay(int day);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(TaskEntry taskEntry);

    @Delete
    void deleteTask(TaskEntry taskEntry);
}
