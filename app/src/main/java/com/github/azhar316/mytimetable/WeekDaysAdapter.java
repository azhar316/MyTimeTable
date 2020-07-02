package com.github.azhar316.mytimetable;

import com.github.azhar316.mytimetable.database.AppDatabase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WeekDaysAdapter extends FragmentStateAdapter {

    private AppDatabase mDb;

    public WeekDaysAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, AppDatabase db) {
        super(fragmentManager, lifecycle);
        mDb = db;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new TaskFragment(mDb, position);
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
