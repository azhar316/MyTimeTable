package com.github.azhar316.mytimetable.database;

import java.sql.Time;
import java.util.Date;

import androidx.room.TypeConverter;

public class DataTypeConverters {

    @TypeConverter
    public static Time toTime(Long time) {
        return time == null ? null : new Time(time);
    }

    @TypeConverter
    public static Long fromTime(Time time) {
        return time == null ? null : time.getTime();
    }

    @TypeConverter
    public static Date toDate(Long date) {
        return date == null ? null : new Date(date);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }
}
