package com.github.azhar316.mytimetable.database;

import java.sql.Time;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class TaskEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "label")
    private String taskLabel;
    @ColumnInfo(name = "description")
    private String taskDescription;
    private int color;
    private int day;
    @ColumnInfo(name = "start_time")
    private Time startTime;
    @ColumnInfo(name = "end_time")
    private Time endTime;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public TaskEntry(String taskLabel, String taskDescription,
                     int color, int day, Time startTime, Time endTime, Date updatedAt) {
        this.taskLabel = taskLabel;
        this.taskDescription = taskDescription;
        this.color = color;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.updatedAt = updatedAt;
    }

    public TaskEntry(int id, String taskLabel, String taskDescription,
                     int color, int day, Time startTime, Time endTime, Date updatedAt) {
        this.id = id;
        this.taskLabel = taskLabel;
        this.taskDescription = taskDescription;
        this.color = color;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskLabel() {
        return taskLabel;
    }

    public void setTaskLabel(String taskLabel) {
        this.taskLabel = taskLabel;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
