package com.github.azhar316.mytimetable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WeekDaysAdapter extends FragmentStateAdapter {

    public WeekDaysAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new TaskFragment();
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
