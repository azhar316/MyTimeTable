package com.github.azhar316.mytimetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.github.azhar316.mytimetable.database.AppDatabase;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final String[] WEEK_DAYS_NAMES = {"MONDAY", "TUESDAY", "WEDNESDAY",
            "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate method called");

        mDb = AppDatabase.getInstance(getApplicationContext());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_week_days);

        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.pager_week_days);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Lifecycle lifecycle = getLifecycle();
        WeekDaysAdapter weekDaysAdapter = new WeekDaysAdapter(fragmentManager, lifecycle, mDb);

        viewPager.setAdapter(weekDaysAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(WEEK_DAYS_NAMES[position]);
            }
        });
        tabLayoutMediator.attach();
    }
}